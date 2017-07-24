package in.clouthink.daas.sbb.shared.util;

import in.clouthink.daas.sbb.shared.util.impl.BaiduShortUrlGenerator;
import in.clouthink.daas.sbb.shared.util.impl.DefaultShortUrlGenerator;
import in.clouthink.daas.sbb.shared.util.impl.ShortUrlGenerator;
import in.clouthink.daas.sbb.shared.util.impl.SinaShortUrlGenerator;

/**
 * @author dz
 */
public class ShortenUrlUtils {

	private static ShortUrlGenerator BBT_SHORT_URL_GENERATOR = new DefaultShortUrlGenerator();

	private static ShortUrlGenerator SINA_SHORT_URL_GENERATOR = new SinaShortUrlGenerator();

	private static ShortUrlGenerator BAIDU_SHORT_URL_GENERATOR = new BaiduShortUrlGenerator();

	public static String bbt(String url) {
		return BBT_SHORT_URL_GENERATOR.shorten(url);
	}

	public static String baidu(String url) {
		return BAIDU_SHORT_URL_GENERATOR.shorten(url);
	}

	public static String sina(String url) {
		return SINA_SHORT_URL_GENERATOR.shorten(url);
	}

}
