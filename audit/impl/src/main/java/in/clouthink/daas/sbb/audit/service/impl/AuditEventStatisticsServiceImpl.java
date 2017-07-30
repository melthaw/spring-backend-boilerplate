package in.clouthink.daas.sbb.audit.service.impl;

import in.clouthink.daas.sbb.account.domain.model.User;
import in.clouthink.daas.sbb.audit.domain.model.AggregationType;
import in.clouthink.daas.sbb.audit.domain.model.AuditEvent;
import in.clouthink.daas.sbb.audit.domain.model.AuditEventAggregation;
import in.clouthink.daas.sbb.audit.exception.AuditEventException;
import in.clouthink.daas.sbb.audit.repository.AuditEventAggregationRepository;
import in.clouthink.daas.sbb.audit.repository.AuditEventRepository;
import in.clouthink.daas.sbb.audit.service.AuditEventStatisticsService;
import in.clouthink.daas.sbb.shared.DomainConstants;
import in.clouthink.daas.sbb.shared.util.DateTimeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class AuditEventStatisticsServiceImpl implements AuditEventStatisticsService {

	private static final Log logger = LogFactory.getLog(AuditEventStatisticsServiceImpl.class);

	@Autowired
	private AuditEventRepository auditEventRepository;

	@Autowired
	private AuditEventAggregationRepository auditEventAggregationRepository;

	@Override
	public void statisticByDay(String realm, Date day) {
		if (StringUtils.isEmpty(realm)) {
			return;
		}
		if (day == null) {
			return;
		}
		String aggregationKeyByDay = new SimpleDateFormat("yyyy-MM-dd").format(day);
		logger.debug(String.format("statisticByDay : %s", aggregationKeyByDay));
		Date startOfDay = DateTimeUtils.startOfDay(day);
		Date endOfDay = DateTimeUtils.endOfDay(day);
		List<AuditEvent> auditEventList = auditEventRepository.findListByRequestedAtBetween(startOfDay, endOfDay);
		AuditEventAggregation auditEventAggregationByDay = auditEventAggregationRepository.findByRealmAndAggregationTypeAndAggregationKey(
				realm,
				AggregationType.DAY,
				aggregationKeyByDay);
		if (auditEventAggregationByDay == null) {
			auditEventAggregationByDay = new AuditEventAggregation();
			auditEventAggregationByDay.setAggregationType(AggregationType.DAY);
			auditEventAggregationByDay.setAggregationKey(aggregationKeyByDay);
		}

		long totalCountByDay = 0;
		long errorCountByDay = 0;
		long totalTimeCostByDay = 0;
		for (AuditEvent auditEvent : auditEventList) {
			totalCountByDay += 1;
			if (auditEvent.isError()) {
				errorCountByDay += 1;
			}
			totalTimeCostByDay += auditEvent.getTimeCost();
		}

		auditEventAggregationByDay.setTotalCount(totalCountByDay);
		auditEventAggregationByDay.setErrorCount(errorCountByDay);
		auditEventAggregationByDay.setTotalTimeCost(totalTimeCostByDay);
		auditEventAggregationRepository.save(auditEventAggregationByDay);

	}

	@Override
	public void statisticByMonth(String realm, Date dayOfMonth) {
		if (StringUtils.isEmpty(realm)) {
			return;
		}

		if (dayOfMonth == null) {
			return;
		}

		String aggregationKeyByMonth = new SimpleDateFormat("yyyy-MM").format(dayOfMonth);
		logger.debug(String.format("statisticByMonth : %s", aggregationKeyByMonth));
		AuditEventAggregation auditEventAggregationByMonth = auditEventAggregationRepository.findByRealmAndAggregationTypeAndAggregationKey(
				realm,
				AggregationType.MONTH,
				aggregationKeyByMonth);
		if (auditEventAggregationByMonth == null) {
			auditEventAggregationByMonth = new AuditEventAggregation();
			auditEventAggregationByMonth.setAggregationType(AggregationType.MONTH);
			auditEventAggregationByMonth.setAggregationKey(aggregationKeyByMonth);
		}

		List<AuditEventAggregation> auditEventAggregationList = auditEventAggregationRepository.findByRealmAndAggregationTypeAndAggregationKeyLike(
				realm,
				AggregationType.DAY,
				aggregationKeyByMonth);

		long totalCount = 0;
		long errorCount = 0;
		long totalTimeCost = 0;
		for (AuditEventAggregation auditEventAggregation : auditEventAggregationList) {
			totalCount += auditEventAggregation.getTotalCount();
			errorCount += auditEventAggregation.getErrorCount();
			totalTimeCost += auditEventAggregation.getTotalTimeCost();
		}

		auditEventAggregationByMonth.setTotalCount(totalCount);
		auditEventAggregationByMonth.setErrorCount(errorCount);
		auditEventAggregationByMonth.setTotalTimeCost(totalTimeCost);
		auditEventAggregationRepository.save(auditEventAggregationByMonth);
	}


	@Override
	public void scanAllAuditEventsAndDoStatistics(String realm, User byWho) {
		if (StringUtils.isEmpty(realm)) {
			return;
		}
		if (!"administrator".equals(byWho.getUsername())) {
			throw new AuditEventException("Only the administrator is allowed to access.");
		}

		long whenToStop = System.currentTimeMillis() - 100 * DomainConstants.HOW_LONG_OF_ONE_DAY;
		Date day = new Date();
		for (; day.getTime() > whenToStop; ) {
			statisticByDay(realm, day);
			statisticByMonth(realm, day);
			day = new Date(day.getTime() - DomainConstants.HOW_LONG_OF_ONE_DAY);
		}
	}

}
