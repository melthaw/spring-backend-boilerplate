package in.clouthink.daas.sbb.audit.service;

import in.clouthink.daas.sbb.account.domain.model.SysUser;
import org.springframework.scheduling.annotation.Async;

import java.util.Date;

/**
 */
public interface AdvancedAuditEventService {

	/**
	 * 以天为单位统计数据
	 *
	 * @param realm
	 * @param day
	 */
	void statisticByDay(String realm, Date day);

	/**
	 * 以月为单位统计数据
	 *
	 * @param realm
	 * @param day
	 */
	void statisticByMonth(String realm, Date day);

	/**
	 * 统计所有的审计数据
	 *
	 * @param realm
	 * @param byWho
	 */
	@Async
	void scanAllAuditEventsAndDoStatistics(String realm, SysUser byWho);
}
