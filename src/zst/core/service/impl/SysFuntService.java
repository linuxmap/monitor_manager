package zst.core.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import zst.core.dao.SysFuntMapper;
import zst.core.entity.SysFunt;
import zst.core.service.ISysFuntService;
@Service
public class SysFuntService implements ISysFuntService{

	@Resource
	private SysFuntMapper sysFuntMapper;
	@Override
	public int deleteByPrimaryKey(Integer id) throws Exception {
		return sysFuntMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(SysFunt record) throws Exception {
		return sysFuntMapper.insert(record);
	}

	@Override
	public int insertSelective(SysFunt record) throws Exception {
		return sysFuntMapper.insertSelective(record);
	}

	@Override
	public SysFunt selectByPrimaryKey(Integer id) throws Exception {
		
		return sysFuntMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<SysFunt> selectAll(SysFunt record) throws Exception {
		
		return sysFuntMapper.selectAll(record);
	}

	@Override
	public int updateByPrimaryKeySelective(SysFunt record) throws Exception {
		
		return sysFuntMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(SysFunt record) throws Exception {
		return sysFuntMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<SysFunt> selectSubFunt(Integer funtId) throws Exception {
		List<SysFunt> funtList = sysFuntMapper.selectSubFunt(funtId);
		if(funtList!=null&&funtList.size()>0){
			for(SysFunt funt:funtList){
				funt.setSubMenuList(selectSubFunt(funt.getId()));
			}
		}
		return funtList;
	}
	
	@Override
	public List<SysFunt> selectClientFunt() throws Exception {
		List<SysFunt> funtList = sysFuntMapper.selectClientFunt();
		if(funtList!=null && funtList.size()>0){
			for(SysFunt funt:funtList){
				funt.setSubMenuList(selectClientSubFunt(funt.getId()));
			}
		}
		return funtList;
	}

	@Override
	public int updateByFuntId(SysFunt record) throws Exception {
		return sysFuntMapper.updateByFuntId(record);
	}

	@Override
	public int deleteByFuntId(Integer id) throws Exception {
		
		return sysFuntMapper.deleteByFuntId(id);
	}

	@Override
	public List<SysFunt>  selectByFuntId(Integer id) throws Exception {
		
		return sysFuntMapper.selectByFuntId(id);
	}

	@Override
	public List<SysFunt> selectFunByUserId(Integer userId) throws Exception {
		return sysFuntMapper.selectFunByUserId(userId);
	}

	@Override
	public List<SysFunt> selectClientSubFunt(Integer id) throws Exception {
		return sysFuntMapper.selectClientSubFunt(id);
	}

	@Override
	public List<SysFunt> selectFunByRoleId(Integer roleId) throws Exception {
		return sysFuntMapper.selectFunByRoleId(roleId);
	}


}
