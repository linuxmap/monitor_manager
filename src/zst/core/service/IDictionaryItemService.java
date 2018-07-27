package zst.core.service;

import java.util.List;

import zst.core.entity.DictionaryItem;

/**
 * 字典条目相关服务接口
 * @author songxiang
 */
public interface IDictionaryItemService {
	//条件查询字典条目集合
	List<DictionaryItem> selectListByObj(DictionaryItem dictionaryItem)throws Exception;
	//条件查询字典条目条数
	long selectCountByObj(DictionaryItem dictionaryItem)throws Exception;
	//新增字典条目
	int insertDictionaryItem(DictionaryItem dictionaryItem)throws Exception;
	//修改字典条目信息
	int updateDictionaryItem(DictionaryItem dictionaryItem)throws Exception;
	//条件查询字典条目对象
	DictionaryItem selectByObj(DictionaryItem dictionaryItem)throws Exception;
	/**查询所有 **/
	List<DictionaryItem> selectAllByObj(DictionaryItem dictionaryItem)throws Exception;
	/**通过设备类型条目id查询对应的设备厂家 **/
	List<DictionaryItem> selectListByEquType(String equType)throws Exception;
	/**模糊查询总和 **/
	long selectFuzzyCountByObj(DictionaryItem dictionaryItem)throws Exception;
	/**模糊分页查询 **/
	List<DictionaryItem> selectFuzzyListByObj(DictionaryItem dictionaryItem)throws Exception;
}
