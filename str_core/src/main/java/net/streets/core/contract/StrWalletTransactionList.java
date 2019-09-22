package net.streets.core.contract;

import net.streets.core.contract.base.DataContract;
import net.streets.persistence.enumeration.StrResponseCode;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;

/***************************************************************************
 *                                                                         *
 * Created:     16 / 05 / 2019                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/

@XmlRootElement
public class StrWalletTransactionList extends DataContract<StrWalletTransactionList> implements Serializable {

    private ArrayList<StrWalletTransaction> walletTransactionData;
    private BigDecimal currentBalance;

    public StrWalletTransactionList() {}

    public StrWalletTransactionList(StrResponseCode symResponseCode) {
        this.strResponse = new StrResponse(symResponseCode);
    }


    public StrWalletTransactionList(StrResponseCode symResponseCode, ArrayList<StrWalletTransaction> walletTransactionData, BigDecimal currentBalance) {
        this.strResponse = new StrResponse(symResponseCode);
        this.walletTransactionData = walletTransactionData;
        this.currentBalance = currentBalance;
    }

    public ArrayList<StrWalletTransaction> getWalletTransactionData() {
        return walletTransactionData;
    }

    public void setWalletTransactionData(ArrayList<StrWalletTransaction> walletTransactionData) {
        this.walletTransactionData = walletTransactionData;
    }

	public BigDecimal getCurrentBalance() {
		return currentBalance;
	}

	public void setCurrentBalance(BigDecimal currentBalance) {
		this.currentBalance = currentBalance;
	}
}
