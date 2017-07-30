package in.clouthink.daas.sbb.audit.service.impl;

import in.clouthink.daas.sbb.account.domain.model.User;
import in.clouthink.daas.sbb.audit.domain.model.AggregationType;
import in.clouthink.daas.sbb.audit.domain.model.AuthEvent;
import in.clouthink.daas.sbb.audit.domain.model.AuthEventAggregation;
import in.clouthink.daas.sbb.audit.exception.AuditEventException;
import in.clouthink.daas.sbb.audit.repository.AuthEventAggregationRepository;
import in.clouthink.daas.sbb.audit.repository.AuthEventRepository;
import in.clouthink.daas.sbb.audit.service.AuthEventStatisticsService;
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
public class AuthEventStatisticsServiceImpl implements AuthEventStatisticsService {

	private static final Log logger = LogFactory.getLog(AuthEventStatisticsServiceImpl.class);

	@Autowired
	private AuthEventRepository authEventRepository;

	@Autowired
	private AuthEventAggregationRepository authEventAggregationRepository;

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
		List<AuthEvent> authEventList = authEventRepository.findListByLoginAtBetween(startOfDay, endOfDay);
		AuthEventAggregation authEventAggregationByDay = authEventAggregationRepository.findByRealmAndAggregationTypeAndAggregationKey(
				realm,
				AggregationType.DAY,
				aggregationKeyByDay);
		if (authEventAggregationByDay == null) {
			authEventAggregationByDay = new AuthEventAggregation();
			authEventAggregationByDay.setAggregationType(AggregationType.DAY);
			authEventAggregationByDay.setAggregationKey(aggregationKeyByDay);
		}

		long totalCountByDay = 0;
		long errorCountByDay = 0;
		for (AuthEvent authEvent : authEventList) {
			totalCountByDay += 1;
			if (!authEvent.isSucceed()) {
				errorCountByDay += 1;
			}
		}

		authEventAggregationByDay.setTotalCount(totalCountByDay);
		authEventAggregationByDay.setErrorCount(errorCountByDay);
		authEventAggregationRepository.save(authEventAggregationByDay);

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
		AuthEventAggregation authEventAggregationByMonth = authEventAggregationRepository.findByRealmAndAggregationTypeAndAggregationKey(
				realm,
				AggregationType.MONTH,
				aggregationKeyByMonth);
		if (authEventAggregationByMonth == null) {
			authEventAggregationByMonth = new AuthEventAggregation();
			authEventAggregationByMonth.setAggregationType(AggregationType.MONTH);
			authEventAggregationByMonth.setAggregationKey(aggregationKeyByMonth);
		}

		List<AuthEventAggregation> authEventAggregationList = authEventAggregationRepository.findByRealmAndAggregationTypeAndAggregationKeyLike(
				realm,
				AggregationType.DAY,
				aggregationKeyByMonth);

		long totalCount = 0;
		long errorCount = 0;
		for (AuthEventAggregation authEventAggregation : authEventAggregationList) {
			totalCount += authEventAggregation.getTotalCount();
			errorCount += authEventAggregation.getErrorCount();
		}

		authEventAggregationByMonth.setTotalCount(totalCount);
		authEventAggregationByMonth.setErrorCount(errorCount);
		authEventAggregationRepository.save(authEventAggregationByMonth);
	}


	@Override
	public void scanAllAuthEventsAndDoStatistics(String realm, User byWho) {
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
