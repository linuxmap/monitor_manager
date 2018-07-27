package zst.extend.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import zst.core.entity.DictionaryInfo;
import zst.core.entity.DictionaryItem;
import zst.core.service.IDictionaryInfoService;
import zst.core.service.IDictionaryItemService;
import zst.core.service.impl.DictionaryItemService;

/**
 * 字典缓存工具类
 * @author: liuyy
 * @date: 2017-6-15 下午5:18:33
 */
public final class DicCacheUtil {

	private DicCacheUtil(){}
	
	/**字典缓存 **/
	private static Map<String,List<DictionaryItem>> dicMap;
	
	/**
	 * 获取指定字典类型的条目集合
	 * @param 字典类型id
	 * @return List<DictionaryItem>
	 * @throws Exception
	 */
	public static List<DictionaryItem> getItmesBydicInfoId(String dicInfoId,HttpServletRequest request) throws Exception{
		if(StringUtils.isBlank(dicInfoId)) {
			return null;
		}
		getDicMap(request);
		synchronized(DicCacheUtil.class) {
			List<DictionaryItem> itemList = dicMap.get(dicInfoId);
			return itemList;
		}
	}

	/**
	 * 获取所有字典类型及字典条目
	 * @return Map<String,List<DictionaryItem>>
	 * @throws Exception
	 */
	public static Map<String,List<DictionaryItem>> getDicMap(HttpServletRequest request) throws Exception {
		if(null == dicMap) {
			synchronized (DicCacheUtil.class) {
				if(null == dicMap) { 
					dicMap = new HashMap<String, List<DictionaryItem>>();
					WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
					IDictionaryItemService dictionaryItemService = (IDictionaryItemService) ctx.getBean("dictionaryItemService");
					IDictionaryInfoService dictionaryInfoService = (IDictionaryInfoService) ctx.getBean("dictionaryInfoService");
					
					DictionaryInfo dictionaryInfo = new DictionaryInfo();
					List<DictionaryInfo> dictionaryInfoList = dictionaryInfoService.selectAllByObj(dictionaryInfo);
					Iterator<DictionaryInfo> iteratorInfo = dictionaryInfoList.iterator();
					while(iteratorInfo.hasNext()) {
						DictionaryInfo info = iteratorInfo.next();
						
						DictionaryItem item = new DictionaryItem();
						item.setDictionaryId(info.getDictionaryId());
						item.setItemStatus("1");
						List<DictionaryItem> itemList = dictionaryItemService.selectAllByObj(item);
						dicMap.put(info.getDictionaryId(), itemList);
					}
				}
			}
		}
		return dicMap;
	}
	
	/**
	 * 更新字典缓存
	 * @param 字典类型id
	 * @throws Exception
	 */
	public static void updateDicMap(String dicInfoId,HttpServletRequest request) throws Exception {
		getDicMap(request);
		synchronized(DicCacheUtil.class) {
			if(dicMap.containsKey(dicInfoId)) {
				dicMap.remove(dicInfoId);//移除该key对应的字典条目
				WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
				DictionaryItemService dictionaryItemService = (DictionaryItemService) ctx.getBean("dictionaryItemService");
				
				DictionaryItem item = new DictionaryItem();
				item.setDictionaryId(dicInfoId);
				item.setItemStatus("1");
				List<DictionaryItem> itemList = dictionaryItemService.selectAllByObj(item);
				dicMap.put(dicInfoId, itemList);//重新添加该字典条目
			}
		}
	}
}
