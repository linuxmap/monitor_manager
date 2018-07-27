package zst.core.service;

import java.util.List;

import zst.core.entity.DictionaryInfo;
/**
 * 字典类型
 * @author Apple
 */
public interface IDictionaryInfoService {
	/**条件查询字典类型集合**/
	List<DictionaryInfo> selectListByObj(DictionaryInfo dictionary) throws Exception;
	/**条件查询字典类型总数**/
	long selectCountByObj(DictionaryInfo dictionary)throws Exception;
	/**查询所有**/
	List<DictionaryInfo> selectAllByObj(DictionaryInfo record) throws Exception;
}
