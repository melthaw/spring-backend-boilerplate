package in.clouthink.daas.sbb.shared.util.impl;

import java.security.MessageDigest;
import java.util.Random;

/**
 * @author dz
 */
public class DefaultShortUrlGenerator implements ShortUrlGenerator {

	String[] chars = new String[]{"a",
								  "b",
								  "c",
								  "d",
								  "e",
								  "f",
								  "g",
								  "h",
								  "i",
								  "j",
								  "k",
								  "l",
								  "m",
								  "n",
								  "o",
								  "p",
								  "q",
								  "r",
								  "s",
								  "t",
								  "u",
								  "v",
								  "w",
								  "x",
								  "y",
								  "z",
								  "0",
								  "1",
								  "2",
								  "3",
								  "4",
								  "5",
								  "6",
								  "7",
								  "8",
								  "9",
								  "A",
								  "B",
								  "C",
								  "D",
								  "E",
								  "F",
								  "G",
								  "H",
								  "I",
								  "J",
								  "K",
								  "L",
								  "M",
								  "N",
								  "O",
								  "P",
								  "Q",
								  "R",
								  "S",
								  "T",
								  "U",
								  "V",
								  "W",
								  "X",
								  "Y",
								  "Z"

	};

	// 可以自定义生成 MD5 加密字符传前的混合 KEY
	private String salt = "@bbt2016";

	public DefaultShortUrlGenerator() {
	}

	public DefaultShortUrlGenerator(String salt) {
		this.salt = salt;
	}

	@Override
	public String shorten(String url) {
		// 对传入网址:加盐+加密（MD5）
		String hex = md5ByHex(url + salt);

		//将产生4组6位字符串
		String[] resUrl = new String[4];
		for (int i = 0; i < 4; i++) {
			// 把加密字符按照 8 位一组 16 进制与 0x3FFFFFFF 进行位与运算
			String sTempSubString = hex.substring(i * 8, i * 8 + 8);

			// 这里需要使用 long 型来转换，因为 Inteper .parseInt() 只能处理 31 位 , 首位为符号位 , 如果不用long ，则会越界
			long lHexLong = 0x3FFFFFFF & Long.parseLong(sTempSubString, 16);
			String outChars = "";
			for (int j = 0; j < 6; j++) {
				// 把得到的值与 0x0000003D 进行位与运算，取得字符数组 chars 索引
				long index = 0x0000003D & lHexLong;
				// 把取得的字符相加
				outChars += chars[(int) index];
				// 每次循环按位右移 5 位
				lHexLong = lHexLong >> 5;
			}
			// 把字符串存入对应索引的输出数组
			resUrl[i] = outChars;
		}

		//产成4以内随机数
		return resUrl[new Random().nextInt(4)];
	}


	private String md5ByHex(String src) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] b = src.getBytes();
			md.reset();
			md.update(b);
			byte[] hash = md.digest();
			String hs = "";
			String stmp = "";
			for (int i = 0; i < hash.length; i++) {
				stmp = Integer.toHexString(hash[i] & 0xFF);
				if (stmp.length() == 1) {
					hs = hs + "0" + stmp;
				}
				else {
					hs = hs + stmp;
				}
			}
			return hs.toUpperCase();
		}
		catch (Exception e) {
			return "";
		}
	}

	//	public static void main(String[] args) {
	//		System.out.println(new DefaultShortUrlGenerator().shorten(
	//				"http://117.177.38.55:9999/portal#!//static/app/mobile/paper/detail.html?messageId=1"));
	//		System.out.println(new DefaultShortUrlGenerator().shorten(
	//				"http://117.177.38.55:9999/portal#!//static/app/mobile/paper/detail.html?messageId=2"));
	//		System.out.println(new DefaultShortUrlGenerator().shorten(
	//				"http://117.177.38.55:9999/portal#!//static/app/mobile/paper/detail.html?messageId=3"));
	//		System.out.println(new DefaultShortUrlGenerator().shorten(
	//				"http://117.177.38.55:9999/portal#!//static/app/mobile/paper/detail.html?messageId=4"));
	//
	//		System.out.println(new DefaultShortUrlGenerator().shorten(
	//				"http://117.177.38.55:9999/portal#!//static/app/mobile/paper/detail.html?messageId=11111111111111111"));
	//		System.out.println(new DefaultShortUrlGenerator().shorten(
	//				"http://117.177.38.55:9999/portal#!//static/app/mobile/paper/detail.html?messageId=11111111111111112"));
	//		System.out.println(new DefaultShortUrlGenerator().shorten(
	//				"http://117.177.38.55:9999/portal#!//static/app/mobile/paper/detail.html?messageId=11111111111111113"));
	//		System.out.println(new DefaultShortUrlGenerator().shorten(
	//				"http://117.177.38.55:9999/portal#!//static/app/mobile/paper/detail.html?messageId=11111111111111114"));
	//
	//	}

}
