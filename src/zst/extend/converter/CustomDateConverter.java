package zst.extend.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.convert.converter.Converter;

/**
 * 自定义日期类型绑定
 * @author: liuyy
 * @date: 2017-6-22 下午1:52:42
 */
public class CustomDateConverter implements Converter<String, Date> {

	private static final Log logger = LogFactory.getLog(CustomDateConverter.class);
	
	@Override
	public Date convert(String source) {
		if(StringUtils.isBlank(source)) {
			return null;
		}
		try{
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return dateFormat.parse(source);
		} catch(ParseException e) {
			logger.equals(e.getMessage());
		}
		//如果参数绑定失败返回null
		return null;
	}

}
