package net.streets.utilities;

import java.util.logging.Logger;

import static java.lang.Long.parseLong;
import static java.lang.String.format;
import static net.streets.common.enumeration.StrConfig.CONFIG_MUTEX_LOCK_WAIT_INTERVAL;
import static net.streets.common.enumeration.StrConfig.CONFIG_MUTEX_LOCK_WAIT_TIME;
import static net.streets.persistence.helper.DaoManager.getStrConfigDao;

/**
 * ***************************************************************************
 * *
 * Created:     25 / 09 / 2015                                             *
 * Platform:    Red Hat Linux 9                                            *
 * Author:      Tich de Blak (Tsungai Kaviya)                              *
 * ****************************************************************************
 */


public class MutexLock {

    private static final Logger logger = Logger.getLogger(MutexLock.class.getSimpleName());
    private static final Long DEFAULT_MUTEX_LOCK_WAIT_TIME = parseLong(getStrConfigDao().getConfig(CONFIG_MUTEX_LOCK_WAIT_TIME));
    private static final Long DEFAULT_MUTEX_LOCK_INTERVAL = parseLong(getStrConfigDao().getConfig(CONFIG_MUTEX_LOCK_WAIT_INTERVAL));
    private boolean locked = false;
    private final Long waitInterval;
    private Long waitTimeout;

    public MutexLock() {
        this.waitTimeout = DEFAULT_MUTEX_LOCK_WAIT_TIME;
        this.waitInterval = DEFAULT_MUTEX_LOCK_INTERVAL;
    }

    public MutexLock(Long waitTimeout, Long waitInterval) {
        this.waitTimeout = waitTimeout;
        this.waitInterval = waitInterval;
    }

    public synchronized boolean waitForLock() {

        //wait for previous lock to be released
        while (this.locked && waitTimeout > 0) {
            try {
                Thread.sleep(10L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            waitTimeout -= waitInterval;
        }

        return true;
    }

    public synchronized boolean acquireLock() {

        //wait for previous lock to be released
        while (this.locked && waitTimeout > 0) {
            logger.warning(format("Waiting for lock. Retrying in %d milliseconds", waitInterval));
            try {
                Thread.sleep(waitInterval);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            waitTimeout -= waitInterval;
        }

        //acquire lock and return
        this.locked = true;

        return true;
    }

    public synchronized void unlock() {
        this.locked = false;
    }

    protected boolean isLocked() {
        return this.locked;
    }
}
