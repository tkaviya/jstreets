package net.streets.core;

import net.streets.core.service.engine.BankAccountEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.logging.Logger;

/***************************************************************************
 *                                                                         *
 * Created:     21 / 09 / 2019                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/

@Component
public class StreetsCore implements PropertyChangeListener {

	private static final Logger logger = Logger.getLogger(StreetsCore.class.getSimpleName());
	private final PropertyChangeSupport moduleEvent = new PropertyChangeSupport(this);

	@Autowired
	public StreetsCore(BankAccountEngine bankAccountEngine){

		logger.info("Initializing Streets Modules");
		bankAccountEngine.addPropertyChangeListener(this).start();
		logger.info("Initialization complete...");
		moduleEvent.firePropertyChange(new PropertyChangeEvent(this, "streets", null,
			"I'm The Streets, look both ways before you cross me!")
		);
	}

	public StreetsCore addPropertyChangeListener(PropertyChangeListener pcl) {
		logger.info("Adding property change listener " + pcl.getClass().getSimpleName());
		moduleEvent.addPropertyChangeListener(pcl);
		return this;
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		moduleEvent.firePropertyChange(event);
	}


}
