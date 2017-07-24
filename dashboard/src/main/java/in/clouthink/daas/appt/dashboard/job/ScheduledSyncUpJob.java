package in.clouthink.daas.sbb.dashboard.job;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

//@Component
public class ScheduledSyncUpJob {

	private static final Log logger = LogFactory.getLog(ScheduledSyncUpJob.class);

//	@Scheduled(cron = "0 5 0 * * ?")
	public void auditEventStatistics() {
		logger.debug("auditEventStatistics start...");
		try {

		}
		catch (Throwable e) {
			logger.error("auditEventStatistics failed", e);
		}
		logger.debug("auditEventStatistics end...");
	}

}
