package in.clouthink.daas.sbb.shared.domain.request;


import java.util.Date;

/**
 * date ranged query request
 */
public interface DateRangedQueryRequest extends PageQueryRequest {

	/**
	 *
	 * @return 起始时间（包括在内）
	 */
	Date getBeginDate();

	/**
	 *
	 * @return 结束时间（包括在内）
	 */
	Date getEndDate();

}
