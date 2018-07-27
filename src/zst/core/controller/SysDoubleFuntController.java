package zst.core.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import zst.core.entity.SysFunt;
import zst.core.entity.SysUser;
import zst.core.service.ISysFuntService;
import zst.extend.enums.LogEnum;
import zst.extend.exception.PlatformException;
import zst.extend.util.CommonUtil;
import zst.extend.util.LogUtil;
import zst.extend.util.PageUtil;
import zst.extend.util.StringUtil;

@Controller
@RequestMapping("/doubleFunt")
public class SysDoubleFuntController {
	
	private static final Log logger = LogFactory.getLog(SysDoubleFuntController.class);
	
	@Resource
	private ISysFuntService sysFuntService;
	
	@RequestMapping("/openFuntManage")
	public String openFuntManage() throws Exception {
		return "sys/funt/funcDoubleEndTreeEdit";
	}
	
	/**
	 * 添加节点
	 * @param sysFunt
	 * @throws Exception
	 */
	@RequestMapping("/addFunt")
	public void addFunt(HttpServletRequest request, SysFunt sysFunt)throws Exception{
		try {
			sysFuntService.insertSelective(sysFunt);
			LogUtil.writeLog(request, LogEnum.LogLevel.ADD, LogEnum.OperatorModule.MENU_MODULE, LogEnum.OperatorType.INSERTOPERATION, "添加菜单:"+sysFunt.getFuntName());
		} catch (Exception e) {
			e.printStackTrace();
			throw new PlatformException(e.getMessage());
		}
	}
	/**
	 * 增加一个菜单树的功能节点
	 * @param funtParId
	 * @param funtName
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/addFuntNode")
	@ResponseBody
	public void addFuntNode(Integer funtParId,String funtName,HttpServletRequest request, HttpServletResponse response)throws Exception{
		try {
			SysFunt sysFunt = new SysFunt();
			SysUser user = CommonUtil.getSessionUser(request);
			sysFunt.setCreateUserId(user.getUserId());
			sysFunt.setFuntName(funtName);
			sysFunt.setFuntParId(funtParId);
			//根据父节点level设置新增节点的level
			SysFunt sysParFunt = new SysFunt();
			sysParFunt = sysFuntService.selectByFuntId(funtParId).get(0);
			int level = sysParFunt.getFuntLevel()+1;//在父节点的level上+1
			sysFunt.setFuntLevel(level);
			
			sysFuntService.insertSelective(sysFunt);
			//写入日志
			LogUtil.writeLog(request, LogEnum.LogLevel.ADD, LogEnum.OperatorModule.MENU_MODULE, LogEnum.OperatorType.INSERTOPERATION, "添加菜单："+sysFunt.getFuntName());
			PageUtil.toBeJsonByMap("true", sysFunt.getId()+"", "", response);
		} catch (Exception e) {
			e.printStackTrace();
			throw new PlatformException(e.getMessage());
		}
		
	}
	
	/**
	 * 重命名菜单功能
	 * @param funtId
	 * @param funtName
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/updateFunt")
	public void updateFunt(Integer id,String funtName,HttpServletResponse response, HttpServletRequest request)throws Exception{
		try {
			SysFunt sysFunt = new SysFunt();
			sysFunt.setId(id);
			sysFunt.setFuntName(funtName);
			sysFuntService.updateByFuntId(sysFunt);
			//写入日志
			LogUtil.writeLog(request, LogEnum.LogLevel.MODIFY, LogEnum.OperatorModule.MENU_MODULE, LogEnum.OperatorType.UPDATEOPERATION, "更改菜单名称为："+sysFunt.getFuntName());
			PageUtil.toBeJsonByMap("true","","",response);
		} catch (Exception e) {
			PageUtil.toBeJsonByMap("false","","",response);
			e.printStackTrace();
			throw new PlatformException(e.getMessage());
		}
	}
	/**
	 * 拖拽功能菜单修改父节点
	 * @param funtId
	 * @param funtParId
	 * @param response
	 * @throws Exception
	 */
	
	@RequestMapping("/changePar")
	public void changePar(Integer id, Integer funtParId,HttpServletResponse response)throws Exception{
		try {
			SysFunt sysFunt = new SysFunt();
			sysFunt.setId(id);
			sysFunt.setFuntParId(funtParId);
			sysFuntService.updateByFuntId(sysFunt);
			
			PageUtil.toBeJsonByMap("true","","",response);
		} catch (Exception e) {
			PageUtil.toBeJsonByMap("false","","",response);
			e.printStackTrace();
			throw new PlatformException(e.getMessage());
			
		}
	}

	/**
	 * 修改功能菜单的url以及排序和样式
	 * @param id
	 * @param funtUrl
	 * @param className
	 * @param funtSort
	 * @param funtGroup
	 * @param code
	 * @param funtNo   权限按钮样式
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/changeFuntUrl")
	public void changeFuntUrl(Integer id,String funtUrl,String className,int funtSort,Integer funtGroup,String code ,String funtNo ,HttpServletResponse response)throws Exception{
		try {
			SysFunt sysFunt = new SysFunt();
			sysFunt.setId(id);
			sysFunt.setFuntUrl(funtUrl);
			sysFunt.setFuntSort(funtSort);
			sysFunt.setClassName(className);
			sysFunt.setFuntGroup(funtGroup);
			sysFunt.setFuntNo(funtNo);
			sysFuntService.updateByFuntId(sysFunt);
			PageUtil.toBeJsonByMap("true","","",response);
		} catch (Exception e) {
			e.printStackTrace();
			PageUtil.toBeJsonByMap("false","","",response);
			throw new PlatformException(e.getMessage());
			
		}
	}
	/**
	 * 根据功能id删除该功能
	 * @param funtId
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/deleteByFuntId")
	public void deleteByFuntId(Integer id,HttpServletResponse response, HttpServletRequest request)throws Exception{
		try {
			SysFunt funt = sysFuntService.selectByPrimaryKey(id);
			String name = funt!=null ? funt.getFuntName() : null;
			//逻辑删除数据库该功能
			sysFuntService.deleteByFuntId(id);
			//写入日志
			LogUtil.writeLog(request, LogEnum.LogLevel.DELETE, LogEnum.OperatorModule.MENU_MODULE, LogEnum.OperatorType.LOGICALDELOPERATION, "删除菜单："+name);
			PageUtil.toBeJsonByMap("true","","",response);
		} catch (Exception e) {
			e.printStackTrace();
			PageUtil.toBeJsonByMap("false","","",response);
			throw new PlatformException(e.getMessage());
			
		}
	}
	/**
	 * 根据功能id查找该功能详情
	 * @param funtId
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/selectByFuntId")
	@ResponseBody
	public void selectByFuntId(Integer id,HttpServletResponse response)throws Exception {
		try {
			String funtUrl ="";
			String className = "";
			Integer funtSort = 0;
			String data = "";
			Integer funtGroup = 0;
			String code = "";
			String funtNo = "";
			List<SysFunt> list = sysFuntService.selectByFuntId(id);
			if (null!=list && list.size()>0) {
				funtUrl =  list.get(0).getFuntUrl();
				className = list.get(0).getClassName();
				funtSort = list.get(0).getFuntSort();
				funtGroup = list.get(0).getFuntGroup();
				funtNo = list.get(0).getFuntNo();
				if (!StringUtil.validateStr(funtUrl)) {
					funtUrl = "";
				}
				if (!StringUtil.validateStr(className)) {
					className = "";
				}
				if (funtSort == null) {
					funtSort = 0;
				}
				if (funtGroup == null) {
					funtGroup = 0;
				}
				if (!StringUtil.validateStr(code)) {
					code = "";
				}
				if (!StringUtil.validateStr(funtNo)) {
					funtNo = "";
				}
				data = funtUrl+","+className+","+funtSort + "," + funtGroup + ","+code + "," + funtNo;
				PageUtil.toBeJsonByMap("true","",data,response);
			}
		} catch (Exception e) {
			logger.equals(e.getMessage());
			e.printStackTrace();
			PageUtil.toBeJsonByMap("false","","",response);
			throw new PlatformException(e.getMessage());
		}
	}
	
	@RequestMapping("/selectById")
	public void selectById(int id)throws Exception{
		try {
			sysFuntService.selectByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new PlatformException(e.getMessage());
		}
		
	}
	/**
	 * 查询所有功能菜单用于拼接成树
	 * @param sysFunt
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/selectAllfunt")
	public void selectAll(SysFunt sysFunt,HttpServletResponse response)throws Exception{
		try {
			sysFunt.setFuntStatus(1);
			List<SysFunt> sysfuntList = sysFuntService.selectAll(sysFunt);
			StringBuffer sb = new StringBuffer();
			
			if(sysfuntList!=null&&sysfuntList.size()>0){
				for(SysFunt sysfunt:sysfuntList){
					if(null==sysfunt.getFuntUrl()){
						sysfunt.setFuntUrl("");
					}
					//{id:'"+funcVO.getFunc_id()+"',pId:'"+funcVO.getParent_id()+"',leaf:'"+funcVO.getLeaf()+"',name:\""+funcVO.getFunc_name()+"\""
					sb.append("{id:'"+sysfunt.getId()+"',pId:'"+sysfunt.getFuntParId()+"',name:'"+sysfunt.getFuntName()+"',url:'"+sysfunt.getFuntUrl()+"'},");
				}
				
			}
			//出去最后一个逗号
			String str = sb.substring(0, sb.length()-1);
			StringBuffer strbuff = new StringBuffer();
			strbuff.append("[");
			strbuff.append(str);
			strbuff.append("]");
			PageUtil.toBeJsonByMap("true", "", strbuff.toString(), response);
		} catch (Exception e) {
			e.printStackTrace();
			throw new PlatformException(e.getMessage());
		}
	}
	
}
