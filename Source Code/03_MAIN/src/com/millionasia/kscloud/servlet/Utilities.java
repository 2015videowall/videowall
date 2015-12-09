package com.millionasia.kscloud.servlet;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.codec.binary.Base64;

public class Utilities {

	public Utilities() {
	}

	public static String TripleDESMethod = "DESede";
	public static byte[] TripleDESencryptMode(byte[] src, String enckey) {
		try {
			SecretKey deskey = new SecretKeySpec(
					build3DesKey(enckey), TripleDESMethod); // 生成密钥
			Cipher c1 = Cipher.getInstance(TripleDESMethod); // 实例化负责加密/解密的Cipher工具类
			c1.init(Cipher.ENCRYPT_MODE, deskey); // 初始化为加密模式
			return c1.doFinal(src);
		} catch (java.security.NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (javax.crypto.NoSuchPaddingException e2) {
			e2.printStackTrace();
		} catch (java.lang.Exception e3) {
			e3.printStackTrace();
		}
		return null;
	}

	public static byte[] TripleDESdecryptMode(byte[] src, String enckey) {
		try {
			SecretKey deskey = new SecretKeySpec(
					build3DesKey(enckey), TripleDESMethod);
			Cipher c1 = Cipher.getInstance(TripleDESMethod);
			c1.init(Cipher.DECRYPT_MODE, deskey); // 初始化为解密模式
			return c1.doFinal(src);
		} catch (java.security.NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (javax.crypto.NoSuchPaddingException e2) {
			e2.printStackTrace();
		} catch (java.lang.Exception e3) {
			e3.printStackTrace();
		}
		return null;
	}

	public static byte[] build3DesKey(String keyStr)
			throws UnsupportedEncodingException {
		byte[] key = new byte[24]; // 声明一个24位的字节数组，默认里面都是0
		byte[] temp = keyStr.getBytes("UTF-8"); // 将字符串转成字节数组

		/*
		 * 执行数组拷贝 System.arraycopy(源数组，从源数组哪里开始拷贝，目标数组，拷贝多少位)
		 */
		if (key.length > temp.length) {
			// 如果temp不够24位，则拷贝temp数组整个长度的内容到key数组中
			System.arraycopy(temp, 0, key, 0, temp.length);
		} else {
			// 如果temp大于24位，则拷贝temp数组24个长度的内容到key数组中
			System.arraycopy(temp, 0, key, 0, key.length);
		}
		return key;
	}

	public static String ConvertSqlTimestamp(java.sql.Timestamp timestamp) {
		String rtnValue = "";
		if (timestamp != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			rtnValue = sdf.format(timestamp);
		}
		return rtnValue;
	}

	public static java.sql.Timestamp CreateTimestamp() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.sss");
		java.util.Date utilDate = new java.util.Date();
		utilDate = sdf.parse(sdf.format(utilDate));
		java.sql.Timestamp sqlDateTime = new java.sql.Timestamp(
				utilDate.getTime());
		return sqlDateTime;
	}

	public static String getCurrentTime() {
		return new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar
				.getInstance().getTime());
	}

	public static String getTime(long value) {
		return new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date(value));
	}

	public static String shorten_filename(String name) {

		String matchString = "[\\u4E00-\\u9FA5]+";
		int lengthOfChinese = 8;
		int lengthOfEnglish = 19;
		boolean haveChinese = false;

		for (int i = 0; i <= name.length() - 1; i++) {
			String test = name.substring(i, i + 1);
			if (test.matches(matchString)) {
				haveChinese = true;
				break;
			}
		}

		if (haveChinese) {
			// Chinese
			if (name.length() > lengthOfChinese)
				name = name.substring(0, lengthOfChinese) + "...";
		} else {
			if (name.length() > lengthOfEnglish)
				name = name.substring(0, lengthOfEnglish) + "...";
		}

		return name;
	}

	public static String jsp_alert(String msg) {
		StringBuffer sb = new StringBuffer();
		sb.append("<html>");
		sb.append("<script type=\"text/javascript\">");
		sb.append("alert(\"" + msg + "\");");
		sb.append("history.go(-1);");
		sb.append("</script>");
		sb.append("</html>");

		String rtnValue = sb.toString();
		return rtnValue;
	}

	public static String jsp_alert(String msg, String url) {
		StringBuffer sb = new StringBuffer();
		sb.append("<html>");
		sb.append("<script type=\"text/javascript\">");
		sb.append("alert(\"" + msg + "\");");
		sb.append("location.href=\"" + url + "\"");
		sb.append("</script>");
		sb.append("</html>");

		String rtnValue = sb.toString();
		return rtnValue;
	}

	public static String jsp_redirect(String url) {
		StringBuffer sb = new StringBuffer();
		sb.append("<html>");
		sb.append("<script type=\"text/javascript\">");
		sb.append("location.href=\"" + url + "\"");
		sb.append("</script>");
		sb.append("</html>");

		String rtnValue = sb.toString();
		return rtnValue;
	}

	public static String jsp_onload_alert(String msg) {
		StringBuffer sb = new StringBuffer();
		sb.append("<script type=\"text/javascript\">");
		sb.append("alert(\"" + msg + "\");");
		sb.append("</script>");

		String rtnValue = sb.toString();
		return rtnValue;
	}

	public static String iso8859_to_utf8(String iso_8859_1_string)
			throws UnsupportedEncodingException {

		String rtn = new String(iso_8859_1_string.getBytes("8859_1"), "UTF-8");
		return rtn;
	}

	public static String getBase64String(String value) {

		byte[] bytesEncoded = Base64.encodeBase64(value.getBytes());
		return new String(bytesEncoded);

	}
	
	public static String getBase64String(byte[] value) {

		byte[] bytesEncoded = Base64.encodeBase64(value);
		return new String(bytesEncoded);

	}
	public static boolean isHttpExists(String URLName){
	    try {
	      HttpURLConnection.setFollowRedirects(false);
	      // note : you may also need
	      //        HttpURLConnection.setInstanceFollowRedirects(false)
	      HttpURLConnection con =
	         (HttpURLConnection) new URL(URLName).openConnection();
	      con.setRequestMethod("HEAD");
	      return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
	    }
	    catch (Exception e) {
	       e.printStackTrace();
	       return false;
	    }
	  }
	public static boolean isHttpsExists(String URLName){
	    try {
	      HttpsURLConnection.setFollowRedirects(false);
	      // note : you may also need
	      //        HttpURLConnection.setInstanceFollowRedirects(false)
	      HttpsURLConnection con =
	         (HttpsURLConnection) new URL(URLName).openConnection();
	      con.setRequestMethod("HEAD");
	      return (con.getResponseCode() == HttpsURLConnection.HTTP_OK);
	    }
	    catch (Exception e) {
	       e.printStackTrace();
	       return false;
	    }
	  }
}
