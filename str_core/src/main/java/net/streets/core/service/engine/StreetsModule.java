package net.streets.core.service.engine;

import net.streets.common.structure.Pair;
import net.streets.core.contract.StrMessage;
import net.streets.persistence.entity.complex_type.str_user;
import net.streets.persistence.entity.enumeration.str_module_config;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.logging.Logger;

import static java.lang.Integer.*;
import static java.lang.String.format;
import static java.util.concurrent.Executors.newScheduledThreadPool;
import static java.util.concurrent.TimeUnit.MINUTES;
import static net.streets.common.utilities.CommonUtilities.isNullOrEmpty;
import static net.streets.common.utilities.CommonUtilities.throwableAsString;
import static net.streets.core.contract.StrMessage.MESSAGE_TYPE.MSG_PRIVATE;
import static net.streets.core.contract.StrMessage.MESSAGE_TYPE.MSG_STREETS;
import static net.streets.persistence.enumeration.StrConfig.CONFIG_THREAD_MAX_POOL_SIZE;
import static net.streets.persistence.helper.DaoManager.getEntityManagerRepo;
import static net.streets.persistence.helper.DaoManager.getStrConfigDao;
import static net.streets.utilities.NetworkUtilities.sendEmailAlert;

/***************************************************************************
 *                                                                         *
 * Created:     24 / 09 / 2019                                             *
 * Author:      Tsungai Kaviya                                             *
 * System:      IntelliJ 2019 / Windows 10                                 *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/

public abstract class StreetsModule implements Runnable {

	private static final Logger logger = Logger.getLogger(StreetsModule.class.getSimpleName());
	private static final ScheduledExecutorService moduleScheduler =
		newScheduledThreadPool(parseInt(getStrConfigDao().getConfig(CONFIG_THREAD_MAX_POOL_SIZE)));
	private final PropertyChangeSupport moduleEvent = new PropertyChangeSupport(this);
	private static final ArrayList<StreetsModule> runningModules = new ArrayList<>();
	private static final ArrayList<StreetsModule> scheduledModules = new ArrayList<>();
	private final ArrayList<ScheduledFuture<?>> moduleScheduledRunners = new ArrayList<>();
	private final String moduleName;

	protected StreetsModule(String moduleName) {
		this.moduleName = moduleName;
		runningModules.add(this);
	}

	public void start() {

		try {
			var moduleConfigResp = getEntityManagerRepo().findUniqueWhere(str_module_config.class, new Pair<>("module_name", moduleName));

			// Some modules are not run as scheduled events. If it has no scheduled run times, it will be called manually
			if (isNullOrEmpty(moduleConfigResp.getResponseObject().getModule_run_times())) { return; }

			String[] moduleTimes = moduleConfigResp.getResponseObject().getModule_run_times().split(",");
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			DateFormat fullDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date currentDate = new Date();

			for (String moduleTime : moduleTimes) {
				logger.info(format("%s will run every day at %s", moduleName, moduleTime));
				Date moduleDateTime = fullDateFormat.parse(dateFormat.format(currentDate) + " " + moduleTime + ":00");
				Calendar moduleCalTime = Calendar.getInstance();

				if (moduleDateTime.before(currentDate)) {
					moduleCalTime.setTime(moduleDateTime);
					moduleCalTime.add(Calendar.DATE, 1);
					moduleDateTime = moduleCalTime.getTime();
					logger.info(format("Module time %s already passed, next starting time is %s", moduleTime, fullDateFormat.format(moduleDateTime)));
				}

				long initialDelay = ((moduleDateTime.getTime() - currentDate.getTime()) / 60000) + 1;

				moduleScheduledRunners.add(moduleScheduler.scheduleWithFixedDelay(this, initialDelay, 1440, MINUTES));
				logger.info(format("Scheduled module %s @ %s with initial delay of %s minutes.", moduleName, moduleTime, initialDelay));

				if (scheduledModules.contains(this)) {
					scheduledModules.add(this);
				}
			}

		} catch (Exception ex) {
			logger.severe(format("Failed to start module %s! %s", moduleName, ex.getMessage()));
			sendEmailAlert(format("Failed to start module %s!", moduleName), throwableAsString(ex));
		}
	}

	public void stop() {
		logger.info("Stopping module " + moduleName);
		for (var moduleRunner : moduleScheduledRunners) {
			moduleRunner.cancel(true);
			moduleScheduledRunners.remove(moduleRunner);
		}
	}

	@Override
	public final void run() {
		logger.info("Running module " + moduleName);
		try {
			execute();
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info(format("Module %s crashed with error %s. Attempting to restart...", moduleName, ex.getMessage()));
			stop();
			start();
		}
	}

	abstract void execute() throws Exception;

	public StreetsModule addPropertyChangeListener(PropertyChangeListener pcl) { moduleEvent.addPropertyChangeListener(pcl); return this; }

	protected void broadcastStreets(String text) {
		var message = new StrMessage(MSG_STREETS, null, this.moduleName + "# " + text);
		moduleEvent.firePropertyChange(new PropertyChangeEvent(this, null, null, message));
	}

	protected void broadcastPrivate(String text, str_user user) {
		var message = new StrMessage(MSG_PRIVATE, user.getId(), this.moduleName + "# " + text);
		moduleEvent.firePropertyChange(new PropertyChangeEvent(this, null, null, message));
	}
}
