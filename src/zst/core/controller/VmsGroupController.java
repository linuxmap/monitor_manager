package zst.core.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import zst.core.entity.VmsGroup;
import zst.core.service.IVmsGoupService;
import zst.extend.common.Constant;
import zst.extend.enums.LogEnum;
import zst.extend.exception.PlatformException;
import zst.extend.page.PageBean;
import zst.extend.util.CommonUtil;
import zst.extend.util.LogUtil;
import zst.extend.util.PageUtil;

@Controller
@RequestMapping("/group")
public class VmsGroupController {

	private static final Log logger = LogFactory.getLog(VmsGroupController.class);
	
	@Resource
	private IVmsGoupService vmsGoupService;
	
	/**
	 * 查询告警分组列表
	 * @param model
	 * @param alarmLevel
	 * @param rows
	 * @param page
	 * @param orgName
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/queryGroups")
	public String queryGroups(Model model, VmsGroup vmsGroup, String rows,String page,String funtId,HttpServletRequest request) throws Exception {
		try {
			vmsGroup.setPage(rows, page);
			List<VmsGroup> groupList = null;
			long totalRows = vmsGoupService.selectCountByObj(vmsGroup);
			if(totalRows > 0) {
				groupList = vmsGoupService.selectListByObj(vmsGroup);
			}
			
			PageBean myPage = new PageBean();
			myPage.setPageSize(rows);
			myPage.setPageNum(page);
			myPage.setTotalRows(totalRows);
			model.addAttribute("list",groupList);
			model.addAttribute("myPage", myPage);
			model.addAttribute("funtId", funtId);
			//处理所用有的组织按钮权限
			Map<Integer, List<String>> map = (Map<Integer, List<String>>) request.getSession().getAttribute(Constant.USER_BUTTON);
			if (map != null) {
				List<String> list = map.get(funtId+"");
				JSONArray fromObject = JSONArray.fromObject(list);
				model.addAttribute(Constant.BUTTON_LIST, fromObject.toString());
			}
			return "configuration/group/groupList";
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new PlatformException(e.getMessage());
		}
	}
	
	/**
	 * 跳转到新增页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addGroupBefore")
	public String addGroupBefore() throws Exception {
		return "configuration/group/operateGroup";
	}
	
	/**
	 * 名称重复问题验证
	 * @param alarmLevel
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/validateGroupName")
	public void validateGroupName(VmsGroup vmsGroup, HttpServletResponse response) throws Exception {
		long total = vmsGoupService.selectCountByObj(vmsGroup);
		if(total>0) {
			PageUtil.toBeJsonByMap("false","","",response);
			return;
		}
		PageUtil.toBeJsonByMap("true","","",response);
	}
	
	/**
	 * 增加告警分组
	 * @param alarmLevel
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping("/addGroup")
	public void addGroup(VmsGroup vmsGroup, HttpServletResponse response, HttpServletRequest request) throws Exception {
		try {
			vmsGoupService.insertSelective(vmsGroup);
			//写入日志
			LogUtil.writeLog(request, LogEnum.LogLevel.ADD, LogEnum.OperatorModule.ALARM_MODULE, LogEnum.OperatorType.INSERTOPERATION, "添加告警分组："+vmsGroup.getName());
			PageUtil.toBeJsonByMap("true",vmsGroup.getGroupId()+"","",response);
		} catch(Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			PageUtil.toBeJsonByMap("false","新增失败，请确认！","",response);
		}
	}
	
	/**
	 * 跳转到编辑页面
	 * @param model
	 * @param levelId
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/modifyGroupBefore")
	public String modifyGroupBefore(Model model,Integer groupId,HttpServletResponse response)throws Exception{
		VmsGroup vmsGroup = vmsGoupService.selectByPrimaryKey(groupId);
		model.addAttribute("groupId", groupId);
		model.addAttribute("vmsGroup", vmsGroup);
		return "configuration/group/operateGroup";
	}
	
	/**
	 * 修改告警等级
	 * @param alarmLevel
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping("/modifyGroup")
	public void modifyGroup(VmsGroup vmsGroup, HttpServletResponse response, HttpServletRequest request) throws Exception {
		try {
			//拼接变动
			StringBuilder action = new StringBuilder();
			VmsGroup oldGroup = vmsGoupService.selectByPrimaryKey(vmsGroup.getGroupId());
			if (oldGroup!=null) { 
				action.append("修改告警分组："+oldGroup.getName());
				if (CommonUtil.notEqualString(oldGroup.getName(), vmsGroup.getName())) {
					action.append("名称由"+oldGroup.getName()+"改为："+vmsGroup.getName()+"~");
				}
			}
			vmsGoupService.updateByPrimaryKeySelective(vmsGroup);
			//写入日志
			LogUtil.writeLog(request, LogEnum.LogLevel.MODIFY, LogEnum.OperatorModule.ALARM_MODULE, LogEnum.OperatorType.UPDATEOPERATION, action.toString());
			PageUtil.toBeJsonByMap("true","","",response);
		} catch(Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			PageUtil.toBeJsonByMap("false","编辑失败，请确认！","",response);
		}
	}
	
	/**
	 * 删除告警等级
	 * @param levelId
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping("/deleteGroup")
	public void deleteGroup(Integer groupId, HttpServletResponse response, HttpServletRequest request) throws Exception {
		try {
			VmsGroup group = vmsGoupService.selectByPrimaryKey(groupId);
			String name = group!=null ? group.getName() : null;
			vmsGoupService.deleteByPrimaryKey(groupId);
			//写入日志
			LogUtil.writeLog(request, LogEnum.LogLevel.DELETE, LogEnum.OperatorModule.ALARM_MODULE, LogEnum.OperatorType.PHYSICALDELOPERATION, "删除告警分组："+name);
			PageUtil.toBeJsonByMap("true","删除成功！","",response);
		} catch(Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			PageUtil.toBeJsonByMap("false","删除失败，请确认！","",response);
		}
	}
}
