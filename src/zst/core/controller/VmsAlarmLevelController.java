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

import zst.core.entity.VmsAlarmLevel;
import zst.core.service.IVmsAlarmLevelService;
import zst.extend.common.Constant;
import zst.extend.enums.LogEnum;
import zst.extend.exception.PlatformException;
import zst.extend.page.PageBean;
import zst.extend.util.CommonUtil;
import zst.extend.util.LogUtil;
import zst.extend.util.PageUtil;

@Controller
@RequestMapping("/alarm")
public class VmsAlarmLevelController {

	private static final Log logger = LogFactory.getLog(VmsAlarmLevelController.class);
	
	@Resource
	private IVmsAlarmLevelService alarmLevelService;
	
	/**
	 * 查询告警级别列表
	 * @param model
	 * @param alarmLevel
	 * @param rows
	 * @param page
	 * @param orgName
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/queryAlarms")
	public String queryAlarms(Model model, VmsAlarmLevel alarmLevel, String rows,String page,String funtId,HttpServletRequest request) throws Exception {
		try {
			alarmLevel.setPage(rows, page);
			List<VmsAlarmLevel> alarmLevelList = null;
			long totalRows = alarmLevelService.selectCountByObj(alarmLevel);
			if(totalRows > 0) {
				alarmLevelList = alarmLevelService.selectListByObj(alarmLevel);
			}
			
			PageBean myPage = new PageBean();
			myPage.setPageSize(rows);
			myPage.setPageNum(page);
			myPage.setTotalRows(totalRows);
			model.addAttribute("list",alarmLevelList);
			model.addAttribute("myPage", myPage);
			model.addAttribute("funtId", funtId);
			//处理所用有的组织按钮权限
			Map<Integer, List<String>> map = (Map<Integer, List<String>>) request.getSession().getAttribute(Constant.USER_BUTTON);
			if (map != null) {
				List<String> list = map.get(funtId+"");
				JSONArray fromObject = JSONArray.fromObject(list);
				model.addAttribute(Constant.BUTTON_LIST, fromObject.toString());
			}
			return "configuration/alarmLevel/alarmLevelList";
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
	@RequestMapping("/addAlarmLevelBefore")
	public String addAlarmLevelBefore(Model model) throws Exception {
		//编码默认加1
		List<VmsAlarmLevel> allList = alarmLevelService.selectListByObj(null);
		if (allList!=null && allList.size()>0) {
			int max = 0;
			for(VmsAlarmLevel aLevel : allList) {
				if (aLevel.getValue()!=null && aLevel.getValue().intValue() > max) {
					max = aLevel.getValue().intValue();
				}
			}
			max++;
			VmsAlarmLevel alarmLevel = new VmsAlarmLevel();
			alarmLevel.setValue(max);
			model.addAttribute("alarmLevel", alarmLevel);
		}
		
		return "configuration/alarmLevel/addAlarmLevel";
	}
	
	/**
	 * 级别名称和编码重复问题验证 增加模块
	 * @param alarmLevel
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/validateLevelName")
	public void validateLevelName(VmsAlarmLevel alarmLevel, HttpServletResponse response) throws Exception {
		long total = alarmLevelService.selectCountByObj(alarmLevel);
		if (total>0) {
			PageUtil.toBeJsonByMap("false","","",response);
			return;
		}
		PageUtil.toBeJsonByMap("true","","",response);
	}
	
	/**
	 * 级别名称和编码，修改时用于重复验证的功能
	 * @param alarmLevel
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/validateNameForModify")
	public void validateNameForModify(VmsAlarmLevel alarmLevel, HttpServletResponse response) throws Exception {
		Integer levelId = alarmLevel.getLevelId();
		alarmLevel.setLevelId(null);//置空条件查询
		List<VmsAlarmLevel> currentList = alarmLevelService.selectListByObj(alarmLevel);
		if (currentList!=null && currentList.size()>0) {
			if ((currentList.size()) == 1 && (currentList.get(0).getLevelId().intValue() == levelId.intValue())) {
				//一条记录且是自己的
				PageUtil.toBeJsonByMap("true","","",response);
			} else {
				PageUtil.toBeJsonByMap("false","","",response);
			}
		} else {
			//没有查出来
			PageUtil.toBeJsonByMap("true","","",response);
		}
	}
	
	/**
	 * 增加告警级别
	 * @param alarmLevel
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping("/addAlarmLevel")
	public void addAlarmLevel(VmsAlarmLevel alarmLevel, HttpServletResponse response, HttpServletRequest request) throws Exception {
		try {
			alarmLevelService.insertSelective(alarmLevel);
			//写入日志
			LogUtil.writeLog(request, LogEnum.LogLevel.ADD, LogEnum.OperatorModule.ALARM_MODULE, LogEnum.OperatorType.INSERTOPERATION, "添加告警级别："+alarmLevel.getName());
			PageUtil.toBeJsonByMap("true","","",response);
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
	@RequestMapping("/modifyAlarmLevelBefore")
	public String modifyAlarmLevelBefore(Model model,Integer levelId,HttpServletResponse response)throws Exception{
		VmsAlarmLevel alarmLevel = alarmLevelService.selectByPrimaryKey(levelId);
		model.addAttribute("levelId", levelId);
		model.addAttribute("alarmLevel", alarmLevel);
		return "configuration/alarmLevel/modifyAlarmLevel";
	}
	
	/**
	 * 修改告警等级
	 * @param alarmLevel
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping("/modifyAlarmLevel")
	public void modifyAlarmLevel(VmsAlarmLevel alarmLevel, HttpServletResponse response, HttpServletRequest request) throws Exception {
		try {
			//拼接变动
			StringBuilder action = new StringBuilder();
			VmsAlarmLevel oldLevel = alarmLevelService.selectByPrimaryKey(alarmLevel.getLevelId());
			if (oldLevel!=null) { 
				action.append("修改告警级别："+oldLevel.getName());
				if (CommonUtil.notEqualString(oldLevel.getName(), alarmLevel.getName())) {
					action.append("名称由"+oldLevel.getName()+"改为："+alarmLevel.getName()+"~");
				}
				if (CommonUtil.notEqualInteger(oldLevel.getValue(), alarmLevel.getValue())) {
					action.append("编码由"+oldLevel.getValue()+"改为："+alarmLevel.getValue()+"~");
				}
				if (CommonUtil.notEqualString(oldLevel.getColor(), alarmLevel.getColor())) {
					action.append("颜色由"+oldLevel.getColor()+"改为："+alarmLevel.getColor()+"~");
				}
				if (CommonUtil.notEqualString(oldLevel.getDescription(), alarmLevel.getDescription())) {
					action.append("描述由"+oldLevel.getDescription()+"改为："+alarmLevel.getDescription()+"~");
				}
			}
			alarmLevelService.updateByPrimaryKeySelective(alarmLevel);
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
	@RequestMapping("/deleteLevel")
	public void deleteLevel(Integer levelId, HttpServletResponse response, HttpServletRequest request) throws Exception {
		try {
			VmsAlarmLevel alarmLevel = alarmLevelService.selectByPrimaryKey(levelId);
			String name = alarmLevel!=null ? alarmLevel.getName() : null;
			alarmLevelService.deleteByPrimaryKey(levelId);
			//写入日志
			LogUtil.writeLog(request, LogEnum.LogLevel.DELETE, LogEnum.OperatorModule.ALARM_MODULE, LogEnum.OperatorType.PHYSICALDELOPERATION, "删除告警级别："+name);
			PageUtil.toBeJsonByMap("true","删除成功！","",response);
		} catch(Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			PageUtil.toBeJsonByMap("false","删除失败，请确认！","",response);
		}
	}
}
