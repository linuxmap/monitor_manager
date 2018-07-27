package zst.core.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import zst.core.entity.SysBaseInfo;
import zst.core.entity.SysMessage;
import zst.core.entity.VmsAlarmLevel;
import zst.core.service.ISysBaseService;
import zst.core.service.IVmsAlarmLevelService;
import zst.extend.enums.LogEnum;
import zst.extend.util.CollectionUtil;
import zst.extend.util.LogUtil;
import zst.extend.util.PageUtil;
import zst.extend.util.StringUtil;

/**
 * message短信配置的控制器
 * @author Ablert
 * @date 2018-3-8 下午5:02:30
 */
@Controller
@RequestMapping("/message")
public class SysMessageController {
	
	private static final Log logger = LogFactory.getLog(SysMessageController.class);
	
	@Resource
	private IVmsAlarmLevelService alarmLevelService;
	
	@Resource
	private ISysBaseService baseService;
	
	//短信参数key配置
	private static final String MESSAGE_GROUP = "SendSMS";
	private static final String MESSAGE_LEVEL = "SendSMSLevel";
	private static final String MESSAGE_PHONES = "SendSMSPhoneNo";
	private static final String MESSAGE_CONTENT = "SendSMSTemplate";

	/**
	 * 进入短信配置
	 * @param model
	 * @return
	 */
	@RequestMapping("/messageConfig")
	public String messageConfig(Model model) {
		try {
			//查询信息配置格式
			SysBaseInfo baseInfo = new SysBaseInfo();
			baseInfo.setGroup(MESSAGE_GROUP);
			List<SysBaseInfo> messageConfig = baseService.selectListByObj(baseInfo);
			if (CollectionUtil.isNotEmpty(messageConfig)) {
				String levelRegex = ",";
				String levelValue = null;
				String phoneValue = null;
				String contentValue = null;
				Set<Integer> levelIdSet = new HashSet<Integer>();
				for (SysBaseInfo sysBaseInfo : messageConfig) {
					if (MESSAGE_LEVEL.equals(sysBaseInfo.getName())) {
						levelValue = sysBaseInfo.getValue();
					} else if (MESSAGE_PHONES.equals(sysBaseInfo.getName())) {
						phoneValue = sysBaseInfo.getValue();
					} else if (MESSAGE_CONTENT.equals(sysBaseInfo.getName())) {
						contentValue = sysBaseInfo.getValue();
					}
				}
				if (StringUtil.validateStr(levelValue)) {
					String[] level = levelValue.split(levelRegex);
					for (String levelId : level) {
						levelIdSet.add(Integer.valueOf(levelId));
					}
				}
				if (phoneValue!=null) {
					model.addAttribute("phoneValue", phoneValue);	
				}
				if (contentValue!=null) {
					model.addAttribute("contentValue", contentValue);
				}
				//查询告警等级
				List<VmsAlarmLevel> levelList = alarmLevelService.selectListByObj(null);
				if (CollectionUtil.isNotEmpty(levelList)) {
					for (VmsAlarmLevel alarmLevel : levelList) {
						if (levelIdSet.contains(alarmLevel.getLevelId())) {
							alarmLevel.setCheckAttr("checked");
						}
					}
				}
				model.addAttribute("levelList", levelList);
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
			e.printStackTrace();
		}
		return "configuration/sysconfig/messageConfig";
	}
	
	/**
	 * 保存短信配置信息
	 * @param sysMessage
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/saveMessageInfo")
	public void saveMessageInfo(SysMessage sysMessage, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			SysBaseInfo baseInfo = new SysBaseInfo();
			baseInfo.setGroup(MESSAGE_GROUP);
			List<SysBaseInfo> messageConfig = baseService.selectListByObj(baseInfo);
			
			if (CollectionUtil.isNotEmpty(messageConfig)) {
				for (SysBaseInfo sysBaseInfo : messageConfig) {
					if (MESSAGE_LEVEL.equals(sysBaseInfo.getName())) {
						sysBaseInfo.setValue(sysMessage.getLevelIds()!=null ? sysMessage.getLevelIds() : "");
						sysBaseInfo.setName(null);
						sysBaseInfo.setGroup(null);
						baseService.updateByPrimaryKeySelective(sysBaseInfo);
					} else if (MESSAGE_PHONES.equals(sysBaseInfo.getName())) {
						sysBaseInfo.setValue(sysMessage.getPhones()!=null ? sysMessage.getPhones() : "");
						sysBaseInfo.setName(null);
						sysBaseInfo.setGroup(null);
						baseService.updateByPrimaryKeySelective(sysBaseInfo);
					} else if (MESSAGE_CONTENT.equals(sysBaseInfo.getName())) {
						sysBaseInfo.setValue(sysMessage.getContent()!=null ? sysMessage.getContent() : "");
						sysBaseInfo.setName(null);
						sysBaseInfo.setGroup(null);
						baseService.updateByPrimaryKeySelective(sysBaseInfo);
					}
				}
			}
			//写入日志
			LogUtil.writeLog(request, LogEnum.LogLevel.MODIFY, LogEnum.OperatorModule.MESSAGE_CONFIG, LogEnum.OperatorType.UPDATEOPERATION, "配置短信格式与级别");
			PageUtil.toBeJsonByMap("true","","",response);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			PageUtil.toBeJsonByMap("false","保存失败，请确认！","",response);
		}
	}
}
