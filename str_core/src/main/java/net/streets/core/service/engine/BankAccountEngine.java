package net.streets.core.service.engine;

import net.streets.persistence.entity.complex_type.str_user_attributes;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.logging.Logger;

import static java.lang.String.format;
import static net.streets.persistence.helper.DaoManager.getEntityManagerRepo;
import static net.streets.utilities.StreetsUtils.formatMoney;

/***************************************************************************
 *                                                                         *
 * Created:     24 / 09 / 2019                                             *
 * Author:      Tsungai Kaviya                                             *
 * System:      IntelliJ 2019 / Windows 10                                 *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/

@Service
public class BankAccountEngine extends StreetsModule {

	private static final Logger logger = Logger.getLogger(BankAccountEngine.class.getSimpleName());
	private LinkedList<str_user_attributes> richestPlayers = new LinkedList<>();

	public BankAccountEngine() { super("BankAccount"); }

	@Override
	void execute() throws Exception {
		processInterest();
		processLoans();
	}

	private void processInterest() throws Exception {
		var userAttributes = getEntityManagerRepo().findAll(str_user_attributes.class);

		BigDecimal totalInterest = new BigDecimal(0);
		richestPlayers.clear();
		for (var userAttr : userAttributes) {
			var initialAmount = userAttr.getAccount_balance();
			var interest = userAttr.getAccount_balance().multiply(new BigDecimal(0.05));
			userAttr.setAccount_balance(userAttr.getAccount_balance().add(interest));
			totalInterest = totalInterest.add(interest);

			if (richestPlayers.size() < 5) {
				richestPlayers.add(userAttr);
				richestPlayers.sort(Comparator.comparing(str_user_attributes::getAccount_balance));
			} else if (richestPlayers.get(4).getAccount_balance().compareTo(userAttr.getAccount_balance()) < 0) {
				//remove brokest user from the list, replace user with current user, then reorder the list again
				richestPlayers.remove(richestPlayers.get(4));
				richestPlayers.add(userAttr);
				richestPlayers.sort(Comparator.comparing(str_user_attributes::getAccount_balance));
			}
			userAttr.save();
			broadcastPrivate(format("Interest of %s credited to account. Previous balance: %s | New Balance: %s",
				formatMoney(interest), formatMoney(initialAmount),
				formatMoney(userAttr.getAccount_balance())), userAttr.getUser()
			);
		}
		StringBuilder top5users = new StringBuilder("Top 5 -> ");
		for (var player : richestPlayers) {
			top5users.append(player.getUser().getUsername())
					 .append(": ")
					 .append(formatMoney(player.getAccount_balance()))
					 .append(",");
		}
		var top5str = top5users.toString().substring(0, top5users.length() - 2);
		broadcastStreets("Daily interest has been added to all accounts!");
		broadcastStreets("Total interest today: " + formatMoney(totalInterest));
		broadcastStreets(top5str);
		logger.info("Daily interest added to all accounts! Total interest : " + formatMoney(totalInterest));
		logger.info(top5str);
	}

	private void processLoans() throws Exception {

	}
}
