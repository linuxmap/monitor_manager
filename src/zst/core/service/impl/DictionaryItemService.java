package zst.core.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import zst.core.dao.DictionaryItemMapper;
import zst.core.entity.DictionaryItem;
import zst.core.service.IDictionaryItemService;

/**
 * 字典条目相关业务
 * @author Apple
 */
@Service
public class DictionaryItemService implements IDictionaryItemService{
	private @Resource DictionaryItemMapper dictionaryItemMapper;

	/**
	 * 条件查询字典条目对象
	 * @author songxiang
	 */
	@Override
	public DictionaryItem selectByObj(DictionaryItem dictionaryItem)throws Exception {
		return dictionaryItemMapper.selectByObj(dictionaryItem);
	}
	
	/**
	 * 根据条件查询字典条目
	 * @author songxiang
	 */
	@Override
	public List<DictionaryItem> selectListByObj(DictionaryItem dictionaryItem)throws Exception {
		return dictionaryItemMapper.selectListByObj(dictionaryItem);
	}

	/**
	 * 根据条件查询字典条目条数
	 * @author songxiang
	 */
	@Override
	public long selectCountByObj(DictionaryItem dictionaryItem)throws Exception {
		return dictionaryItemMapper.selectCountByObj(dictionaryItem);
	}
	
	/**
	 * 新增字典条目
	 * @author songxiang
	 */
	@Override
	public int insertDictionaryItem(DictionaryItem dictionaryItem)throws Exception {
		return dictionaryItemMapper.insertBySelective(dictionaryItem);
	}

	/**
	 * 修改字典条目
	 * @author songxiang
	 */
	@Override
	public int updateDictionaryItem(DictionaryItem dictionaryItem)throws Exception {
		return dictionaryItemMapper.updateByPrimaryKeySelective(dictionaryItem);
	}

	@Override
	public List<DictionaryItem> selectAllByObj(DictionaryItem dictionaryItem)
			throws Exception {
		return dictionaryItemMapper.selectAllByObj(dictionaryItem);
	}

	@Override
	public List<DictionaryItem> selectListByEquType(String equType)
			throws Exception {
		return dictionaryItemMapper.selectListByEquType(equType);
	}

	@Override
	public long selectFuzzyCountByObj(DictionaryItem dictionaryItem)
			throws Exception {
		return dictionaryItemMapper.selectFuzzyCountByObj(dictionaryItem);
	}

	@Override
	public List<DictionaryItem> selectFuzzyListByObj(
			DictionaryItem dictionaryItem) throws Exception {
		return dictionaryItemMapper.selectFuzzyListByObj(dictionaryItem);
	}

	
}
