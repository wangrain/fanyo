package com.fanyo.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MD5Util {
	private static Logger log = LoggerFactory.getLogger(MD5Util.class);
	public static final String[] hexStrings;

	static {
		hexStrings = new String[256];
		for (int i = 0; i < 256; i++) {
			StringBuilder d = new StringBuilder(2);
			char ch = Character.forDigit(((byte) i >> 4) & 0x0F, 16);
			d.append(Character.toUpperCase(ch));
			ch = Character.forDigit((byte) i & 0x0F, 16);
			d.append(Character.toUpperCase(ch));
			hexStrings[i] = d.toString();
		}

	}

	/**
	 * 对数据进行md5加密 江苏银联
	 * 
	 * @param data
	 * @return
	 */
	public static String signData(String data) {
		String result = null;
		if (StringUtils.isNotBlank(data)) {
			final String ALGO_MD5 = "MD5";
			try {
				MessageDigest md = MessageDigest.getInstance(ALGO_MD5);
				md.update(data.getBytes());
				result = hexString(md.digest());
			} catch (NoSuchAlgorithmException e) {
				log.error("md5加密失败", e);
			}
		}
		return result;
	}

	public static byte[] signDataBySHA1(String data) {
		byte[] result = null;
		if (StringUtils.isNotBlank(data)) {
			final String ALGO_MD5 = "SHA-1";
			try {
				MessageDigest md = MessageDigest.getInstance(ALGO_MD5);
				md.update(data.getBytes());
				result = md.digest();
			} catch (NoSuchAlgorithmException e) {
				log.error("SHA-1加密失败", e);
			}
		}
		return result;
	}

	/**
	 * 将字节数组转为HEX字符串(16进制串)江苏银联
	 * 
	 * @param b
	 * @return
	 */
	public static String hexString(byte[] b) {
		StringBuilder d = new StringBuilder(b.length * 2);
		for (byte aB : b) {
			d.append(hexStrings[(int) aB & 0xFF]);
		}
		return d.toString();
	}

	/**
	 * 将字节数组转为HEX字符串(16进制串)
	 * 
	 * @param bts
	 *            要转换的字节数组
	 * @return 转换后的HEX串
	 */
	public static String bytes2Hex(byte[] bts) {
		String des = "";
		String tmp = null;
		for (int i = 0; i < bts.length; i++) {
			tmp = (Integer.toHexString(bts[i] & 0xFF));
			if (tmp.length() == 1) {
				des += "0";
			}
			des += tmp;
		}
		return des;
	}
	
	/**
	 * 计算消息摘要
	 * 计算结果长度：SHA-1 40byte, MD5 32byte, SHA-256 64byte
	 * @param strSrc 计算摘要的源字符串
	 * @param encName 摘要算法: "SHA-1"  "MD5"  "SHA-256", 默认为"SHA-1"
	 * @return 正确返回摘要值,错误返回null
	 */
	public static String getMessageDigest(String strSrc, String encName) {
		MessageDigest md = null;
		String strDes = null;
		final String ALGO_DEFAULT = "SHA-1";
		//final String ALGO_MD5 = "MD5";
		//final String ALGO_SHA256 = "SHA-256";

		byte[] bt = strSrc.getBytes();
		try {
			if (StringUtils.isEmpty(encName)) {
				encName = ALGO_DEFAULT;
			}
			md = MessageDigest.getInstance(encName);
			md.update(bt);
			strDes = bytes2Hex(md.digest()); //to HexString
		} catch (NoSuchAlgorithmException e) {
			log.error("不支持的摘要算法。"+e.getMessage());
			return null;
		}
		return strDes;
	}
}
