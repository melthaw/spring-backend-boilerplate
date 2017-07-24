package in.clouthink.daas.sbb.shared.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class HmacUtils {

	private static final Log logger = LogFactory.getLog(HmacUtils.class);


	private static final String HEADER_AUTH_PREFIX = "Basic ";

	public static final String HEADER_DATE = "Date";

	public static final String HEADER_USERNAME = "Username";

	public static final String HEADER_AUTH = "Authorization";

	public static DateFormat getDateFormat() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
		return dateFormat;
	}

	// ================== encoding ========================

	public static String generateKey(String source) {
		if (StringUtils.isEmpty(source)) {
			throw new IllegalArgumentException("Key source must be supplied.");
		}
		return generateKey(source.getBytes());
	}

	public static String generateKey(byte[] source) {
		byte[] keyBytes = new Hex().encode(source);
		return new String(keyBytes);
	}

	public static String generateMobileKey(String sourceKey, String source) throws Exception {
		byte[] hmacHash = encode(sourceKey, source);
		// if (logger.isDebugEnabled()) {
		// logger.debug("MobileKey before Hex =" + new String(hmacHash));
		// }

		String hexKey = generateKey(hmacHash);
		if (logger.isDebugEnabled()) {
			logger.debug("Final MobileKey =" + hexKey);
		}

		return hexKey;
	}

	public static byte[] encode(String secret, String data) throws Exception {
		if (StringUtils.isEmpty(secret) || StringUtils.isEmpty(data)) {
			throw new IllegalArgumentException("Secret and Data must be supplied.");
		}

		Mac mac = Mac.getInstance("HmacSHA256");
		// get the bytes of the hmac key and data string
		// byte[] secretByte = secret.getBytes();
		byte[] secretByte = new Hex().decode(secret.getBytes());
		// byte[] secretByte = hexStringToByteArray(secret);
		byte[] dataBytes = data.getBytes("UTF-8");
		SecretKey secretKey = new SecretKeySpec(secretByte, "HmacSHA256");

		mac.init(secretKey);
		byte[] doFinal = mac.doFinal(dataBytes);
		return doFinal;
		// byte[] hexB = new Hex().encode(doFinal);
		// return hexB;
	}

	public static byte[] encode(byte[] secret, String data) throws Exception {
		if (secret == null || StringUtils.isEmpty(data)) {
			throw new IllegalArgumentException("Secret and Data must be supplied.");
		}

		Mac mac = Mac.getInstance("HmacSHA256");
		byte[] dataBytes = data.getBytes("UTF-8");
		SecretKey secretKey = new SecretKeySpec(secret, "HmacSHA256");

		mac.init(secretKey);
		byte[] doFinal = mac.doFinal(dataBytes);
		return doFinal;
	}


	public static String getFinalHmac(String username, String secret, String data) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("HMAC Id =" + username);
			logger.debug("HMAC Secret =" + secret);
			logger.debug("Data Before HMAC Encoded =" + data);
		}

		byte[] orginHmac = encode(secret, data);

		String firstBase64 = new String(Base64.encodeBase64(orginHmac));

		if (logger.isDebugEnabled()) {
			logger.debug("HMAC Data with Base64 Encoder =" + firstBase64);
		}

		String usernameN64 = username + ":" + firstBase64;

		if (logger.isDebugEnabled()) {
			logger.debug("Username and HMAC Data =" + usernameN64);
		}

		String secodeBase64 = new String(Base64.encodeBase64(usernameN64.getBytes("UTF-8")));

		if (logger.isDebugEnabled()) {
			logger.debug("Final Data =" + secodeBase64);
		}

		String authHeader = HEADER_AUTH_PREFIX + secodeBase64;

		if (logger.isDebugEnabled()) {
			logger.debug("Authorization Header =" + authHeader);
		}

		return authHeader;
	}

	public static String getFinalHmac(HmacDetail detail) throws Exception {
		if (detail == null) {
			throw new IllegalArgumentException("HmacDetail must be supplied.");
		}
		return getFinalHmac(detail.getUsername(), detail.getSecret(), detail.toHmacData());
	}

	// ======================= decoding ===================
	public static String decodeSecretId(String authHeader) {
		if (authHeader == null || authHeader.length() < HEADER_AUTH_PREFIX.length()) {
			throw new IllegalArgumentException("Unrecognized Authorization header.");
		}

		String base64Final = authHeader.substring(HEADER_AUTH_PREFIX.length());
		String usernameN64 = null;
		try {
			usernameN64 = new String(Base64.decodeBase64(base64Final.getBytes("UTF-8")));
		}
		catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException("Unrecognized Authorization header.");
		}

		String[] array = StringUtils.split(usernameN64, ":");
		if (array == null || array.length != 2) {
			throw new IllegalArgumentException("Unrecognized Authorization header.");
		}

		return array[0];
	}

	// ======================= utils =======================

	//    public static void setHmacData(HmacDetail detail, HttpRequest request) {
	//        if (request == null) {
	//            throw new IllegalArgumentException("HttpRequest must be supplied.");
	//        }
	//
	//        String authHeader = null;
	//        try {
	//            authHeader = getFinalHmac(detail);
	//        }
	//        catch (Exception e) {
	//            throw new IllegalArgumentException("Encoding HMAC error.", e);
	//        }
	//        request.getHeaders().addHeader(HEADER_DATE,
	//                                       toGMTDateString(detail.getDate()));
	//        request.getHeaders().addHeader(HEADER_USERNAME, detail.getUsername());
	//        request.getHeaders().addHeader(HEADER_AUTH, authHeader);
	//        if (detail.getContent() != null) {
	//            request.setBody(detail.getContent());
	//        }
	//    }

	public static String toGMTDateString(Date date) {
		return getDateFormat().format(date);
	}

	public static Date parseGMTDate(String str) {
		try {
			return getDateFormat().parse(str);
		}
		catch (ParseException e) {
			logger.error("parseGMTDate error.", e);
			return null;
		}
	}
	//
	//    public static void main(String[] args) throws Exception {
	//        // String username = "54CB61FA-04FC-490F-997D-D57EF275FF7F";
	//        // String secret =
	//        // "a219519471f875f823ab0085ae0f590f1b78a25269acd24a07a0f7ec87ba111d";
	//
	//        String username = "YmI4MGFmN2ItYTVkOC00Nzc1LWIzYjctMjVkYzA2MDUxNDVl";
	//        String secret = "62353334666137642d366331352d346332642d396332632d623764356233313766356532";
	//
	//        // String data =
	//        // "GET\nMon, 10 Mar 2014 01:47:10 GMT\n54CB61FA-04FC-490F-997D-D57EF275FF7F\n/SAAuthWSDev/api/authping";
	//        String data = "POST\n" + "Tue, 11 Mar 2014 07:49:52 GMT\n"
	//                      + "54CB61FA-04FC-490F-997D-D57EF275FF7F\n"
	//                      + "/SAAuthWSDev/api/auth\n"
	//                      + "{\"message\":\"pm\",\"os\":\"Android 4.3\",\"token\":\"pt02\",\"requestId\":\"f547087c807c55bc8191e97486bedba4\",\"userId\":\"40b820f8-76af-4a44-9a56-a2a11ce38623\",\"userName\":\"test\",\"integrationId\":\"id\",\"companyId\":\"5910974510923776\",\"companyName\":\"Default\",\"type\":\"push\",\"integrationName\":\"name\",\"async\":1,\"ip\":\"/127.0.0.1\"}";
	//        // String data = "abc";
	//
	//        String authHeader = getFinalHmac(username, secret, data);
	//        String decodeUsername = decodeSecretId(authHeader);
	//        System.out.println(decodeUsername);
	//    }


	public static class HmacDetail {

		private String username;

		private String secret;

		private Date date;

		private String path;

		private String content;

		private String method;

		public HmacDetail(String username, String secret, String method, String path, String content) {
			super();
			this.username = username;
			this.secret = secret;
			this.method = method;
			this.path = path;
			this.content = content;
		}

		public HmacDetail(String username, String secret, String method, String path) {
			this(username, secret, method, path, null);
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getSecret() {
			return secret;
		}

		public void setSecret(String secret) {
			this.secret = secret;
		}

		public Date getDate() {
			if (date == null) {
				date = new Date();
			}
			return date;
		}

		public void setDate(Date date) {
			this.date = date;
		}

		public String getPath() {
			return path;
		}

		public void setPath(String path) {
			this.path = path;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public String getMethod() {
			return method;
		}

		public void setMethod(String method) {
			this.method = method;
		}

		public String toHmacData() {
			if (StringUtils.isEmpty(username)) {
				throw new IllegalArgumentException("Username must be supplied.");
			}
			if (StringUtils.isEmpty(secret)) {
				throw new IllegalArgumentException("Secret must be supplied.");
			}
			if (StringUtils.isEmpty(path)) {
				throw new IllegalArgumentException("Path must be supplied.");
			}
			if (StringUtils.isEmpty(method)) {
				throw new IllegalArgumentException("Method must be supplied.");
			}

			StringBuilder sb = new StringBuilder();
			sb.append(method);
			sb.append("\n").append(HmacUtils.toGMTDateString(getDate()));
			sb.append("\n").append(username);
			sb.append("\n").append(path);
			if (content != null) {
				sb.append("\n").append(content);
			}

			return sb.toString();
		}

        /*private static String toGMTDateString(Date date) {
			return getDateFormat().format(date);
        }*/

	}
}
