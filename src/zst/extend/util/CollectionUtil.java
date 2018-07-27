package zst.extend.util;

import java.util.Collection;

import org.springframework.util.CollectionUtils;


/**
 * 集合的工具类
 * @author Ablert
 * @date 2018-2-2 上午9:36:14
 */
public final class CollectionUtil {

	/**
	 * 判断collection是否为空
	 * @param collection
	 * @return
	 */
	public static boolean isEmpty(Collection<?> collection) {
		return CollectionUtils.isEmpty(collection);
	}
	
	/**
	 * 判断collection是否非空
	 * @param collection
	 * @return
	 */
	public static boolean isNotEmpty(Collection<?> collection) {
		return !isEmpty(collection);
	}
}
