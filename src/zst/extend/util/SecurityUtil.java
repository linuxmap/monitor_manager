package zst.extend.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.util.StringUtils;

/**
 * 安全工具类
 * @author: liuyy
 * @date: 2017-6-8 下午4:41:06
 */
public final class SecurityUtil {

	/**
	 * MD5加密
	 * @param s
	 * @return String
	 * @throws Exception
	 */
	public static String encryptPassword(String str) {
        String md5Str = "";
        if (StringUtils.isEmpty(str)) 
        	return md5Str;
        try {
            MessageDigest messagedigest = MessageDigest.getInstance("MD5");
            messagedigest.update(str.getBytes());
            byte abyte0[] = messagedigest.digest();
            md5Str = myByte2hex(abyte0);
        } catch (NoSuchAlgorithmException e) {
        	
        }
        return md5Str;
    }
	
	public static void main(String[] args) {
		System.out.println(encryptPassword("zhanglin,123456"));
	}
	
    private static String myByte2hex(byte abyte0[]) {
        String s = "";
        for (int i = 0; i < abyte0.length; i++) {
            String s2 = Integer.toHexString(abyte0[i] & 0xff);
            if (s2.length() == 1)
                s = s + "0" + s2;
            else
                s = s + s2;          
        }

        return s;
    }
}
