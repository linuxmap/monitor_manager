package zst.extend.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * 汉字和拼音的工具类
 * 
 * @author Ablert
 * 
 */
public final class PinyinUtil {

	/**
	 * 提取汉字的首字母，如果里面含有费中文字符则忽略之；如果全为非中文则返回""
	 * @param zn_str
	 * @param caseType
	 * @return
	 */
	public static String getPinYinHeadChar(String zn_str, int caseType) {
		if (zn_str != null && !zn_str.trim().equalsIgnoreCase("")) {
			char[] strChar = zn_str.toCharArray();
			// 汉语拼音格式输出类
			HanyuPinyinOutputFormat hanYuPinOutputFormat = new HanyuPinyinOutputFormat();
			// 输出设置，大小写，音标方式等
			if (1 == caseType) {
				hanYuPinOutputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
			} else {
				hanYuPinOutputFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
			}
			hanYuPinOutputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
			hanYuPinOutputFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
			StringBuffer pyStringBuffer = new StringBuffer();
			for (int i = 0; i < strChar.length; i++) {
				char c = strChar[i];
				char pyc = strChar[i];
				if (String.valueOf(c).matches("[\\u4E00-\\u9FA5]+")) {// 是中文或者a-z或者A-Z转换拼音
					try {
						String[] pyStirngArray = PinyinHelper
								.toHanyuPinyinStringArray(strChar[i],
										hanYuPinOutputFormat);
						if (null != pyStirngArray && pyStirngArray[0] != null) {
							pyc = pyStirngArray[0].charAt(0);
							pyStringBuffer.append(pyc);
						}
					} catch (BadHanyuPinyinOutputFormatCombination e) {
						e.printStackTrace();
					}
				}
			}
			return pyStringBuffer.toString();
		}
		return null;
	}

	public static void main(String[] args) {
		String cnStr = "TEST的";
		System.out.println(getPinYinHeadChar(cnStr, 1)); // 输出lff
		System.out.println(getPinYinHeadChar(cnStr, 2)); // 输出LFF
	}
}
