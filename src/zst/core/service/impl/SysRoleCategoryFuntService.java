package zst.core.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import zst.core.dao.SysRoleCategoryFuntMapper;
import zst.core.entity.SysRoleCategoryFunt;
import zst.core.service.ISysRoleCategoryFuntService;

@Service
public class SysRoleCategoryFuntService implements ISysRoleCategoryFuntService{
	
	@Resource
	private SysRoleCategoryFuntMapper sysRoleCategoryFuntMapper;
	
	@Override
	public int deleteByPrimaryKey(Integer id) throws Exception {
		return sysRoleCategoryFuntMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(SysRoleCategoryFunt record) throws Exception {
		return sysRoleCategoryFuntMapper.insert(record);
	}

	@Override
	public int insertSelective(SysRoleCategoryFunt record) throws Exception {
		return sysRoleCategoryFuntMapper.insertSelective(record);
	}

	@Override
	public SysRoleCategoryFunt selectByPrimaryKey(Integer id) throws Exception {
		return sysRoleCategoryFuntMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(SysRoleCategoryFunt record) throws Exception {
		return sysRoleCategoryFuntMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(SysRoleCategoryFunt record) throws Exception {
		return sysRoleCategoryFuntMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<SysRoleCategoryFunt> selectByCategoryid(Integer roleId) throws Exception {
		return sysRoleCategoryFuntMapper.selectByCategoryid(roleId);
	}

	@Override
	public int addList(List<SysRoleCategoryFunt> list) throws Exception {
		return sysRoleCategoryFuntMapper.addList(list);
	}

	@Override
	public int deleteByCategoryid(Integer roleId) throws Exception {
		return sysRoleCategoryFuntMapper.deleteByCategoryid(roleId);
	}

	@Override
	public int deleteByCategoryFunt(SysRoleCategoryFunt record) throws Exception {
		return sysRoleCategoryFuntMapper.deleteByCategoryFunt(record);
	}

	@Override
	public List<SysRoleCategoryFunt> selectListByObj(SysRoleCategoryFunt record) throws Exception {
		return sysRoleCategoryFuntMapper.selectListByObj(record);
	}

}
