package zst.core.controller;

import java.util.Iterator;
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
import zst.core.entity.VmsDeviceItem;
import zst.core.entity.VmsDeviceProperty;
import zst.core.entity.VmsDeviceType;
import zst.core.entity.VmsGroup;
import zst.core.entity.VmsThreshold;
import zst.core.service.IVmsAlarmLevelService;
import zst.core.service.IVmsDeviceItemService;
import zst.core.service.IVmsDevicePropertyService;
import zst.core.service.IVmsDeviceTypeService;
import zst.core.service.IVmsGoupService;
import zst.core.service.IVmsThresholdService;
import zst.extend.common.Constant;
import zst.extend.enums.LogEnum;
import zst.extend.exception.PlatformException;
import zst.extend.page.PageBean;
import zst.extend.util.LogUtil;
import zst.extend.util.PageUtil;

@Controller
@RequestMapping("/threshold")
public class VmsThresholdController {
	
	private static final Log logger = LogFactory.getLog(VmsThresholdController.class);

	@Resource
	private IVmsThresholdService thresholdService;
	
	@Resource
	private IVmsDeviceTypeService deviceTypeService;
	
	@Resource
	private IVmsDeviceItemService deviceItemService;
	
	@Resource
	private IVmsGoupService goupService;
	
	@Resource
	private IVmsDevicePropertyService devicePropertyService;
	
	@Resource
	private IVmsAlarmLevelService alarmLevelService;
	/**
	 * 查询告警阀值列表
	 * @param model
	 * @param alarmLevel
	 * @param rows
	 * @param page
	 * @param orgName
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/queryThreshold")
	public String queryThreshold(Model model ,VmsThreshold threshold, String rows,String page,String funtId ,HttpServletRequest request) throws Exception {
		try {
			threshold.setPage(rows, page);
			List<VmsThreshold> thresholdList = null;
			long totalRows = thresholdService.selectCountByObj(threshold);
			if(totalRows > 0) {
				thresholdList = thresholdService.selectListByObj(threshold);
			}
			
			PageBean myPage = new PageBean();
			myPage.setPageSize(rows);
			myPage.setPageNum(page);
			myPage.setTotalRows(totalRows);
			model.addAttribute("list",thresholdList);
			model.addAttribute("myPage", myPage);
			model.addAttribute("funtId", funtId);
			//处理所用有的组织按钮权限
			Map<Integer, List<String>> map = (Map<Integer, List<String>>) request.getSession().getAttribute(Constant.USER_BUTTON);
			if (map != null) {
				List<String> list = map.get(funtId+"");
				JSONArray fromObject = JSONArray.fromObject(list);
				model.addAttribute(Constant.BUTTON_LIST, fromObject.toString());
			}
			return "configuration/threshold/thresholdList";
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new PlatformException(e.getMessage());
		}
	}
	
	/**
	 * 跳转到新增页面  需要携带设备类型
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addThresholdBefore")
	public String addThresholdBefore(Model model,String deviceTypeId, VmsDeviceProperty deviceProperty, String rows, String page,String funtId) throws Exception {
		deviceProperty.setPage(rows, page);
		List<VmsDeviceType> deviceTypeList = deviceTypeService.selectListByObj(null);
		model.addAttribute("deviceTypeList", deviceTypeList);//设备类型
		
		List<VmsDeviceProperty> devicePropertyList = null;
		if(deviceTypeId != null){
			deviceProperty.setDevicetypeId(Integer.valueOf(deviceTypeId));
			long totalRows = devicePropertyService.selectCombinedCountByObj(deviceProperty);
			if(totalRows > 0) {
				devicePropertyList = devicePropertyService.selectConbinedListByObj(deviceProperty);
			}
			PageBean myPage = new PageBean();
			myPage.setPageSize(rows);
			myPage.setPageNum(page);
			myPage.setTotalRows(totalRows);
			model.addAttribute("myPage", myPage);
		}else if(deviceProperty.getDevicetypeId()!=null){
			long totalRows = devicePropertyService.selectCombinedCountByObj(deviceProperty);
			if(totalRows > 0) {
				devicePropertyList = devicePropertyService.selectConbinedListByObj(deviceProperty);
			}
			PageBean myPage = new PageBean();
			myPage.setPageSize(rows);
			myPage.setPageNum(page);
			myPage.setTotalRows(totalRows);
			model.addAttribute("myPage", myPage);
		}
		model.addAttribute("deviceProperty", deviceProperty);
		model.addAttribute("devicePropertyList", devicePropertyList);
		if(deviceTypeId!=null){
			model.addAttribute("deviceTypeId", deviceTypeId);
		}else{
			model.addAttribute("deviceTypeId", deviceProperty.getDevicetypeId());
		}
		model.addAttribute("funtId", funtId);
		return "configuration/threshold/addthreshold";
	}
	
	/**
	 * 进入选择监控项页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/chooseItem")
	public String chooseItem(Model model, Integer deviceTypeId) throws Exception {
		List<VmsDeviceItem> deviceItemList = deviceItemService.selectListByObj(null);//该设备对应的所有的监控项
//		JSONArray jsonArray = JSONArray.fromObject(deviceItemList);
//		String java = jsonArray.toString();
//		model.addAttribute("zTreeNodes", java);//监控项
		//查出该设备类型已有的监控项
		VmsDeviceProperty deviceProperty = new VmsDeviceProperty();
		deviceProperty.setDevicetypeId(deviceTypeId);
		List<VmsDeviceProperty> currentDPList = devicePropertyService.selectListByObj(deviceProperty);
		if(currentDPList != null && currentDPList.size()>0 && deviceItemList != null && deviceItemList.size()>0){
			//两个集合去重
			for(VmsDeviceProperty dp : currentDPList){
				for(Iterator<VmsDeviceItem> it = deviceItemList.iterator() ; it.hasNext();){
					if(dp.getDeviceitemId() == it.next().getId()){
						it.remove();
						//Iterator 在工作的时候是不允许被迭代的对象被改变的。但你可以使用 Iterator 本身的方法 remove() 来删除对象， Iterator.remove() 方法会在删除当前迭代对象的同时维护索引的一致性
						break;
					}
				}
			}
		}
		
		model.addAttribute("deviceItemList", deviceItemList);//监控项  改设备类型未选择的监控项
		model.addAttribute("deviceTypeId", deviceTypeId);//设备类型
		return "configuration/threshold/deviceItemChooseTree";
	}
	
	/**
	 * 进入选择分组界面
	 * @param model
	 * @param deviceTypeId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/configGroup")
	public String configGroup(Model model,Integer deviceTypeId, String ids) throws Exception {
		List<VmsGroup> groupList = goupService.selectListByObj(null);
		model.addAttribute("groupList", groupList);//监控项
		model.addAttribute("deviceTypeId", deviceTypeId);//设备类型
		model.addAttribute("ids", ids);//关系id
		return "configuration/threshold/groupChooseTree";
	}
	
	/**
	 * 在监控项选择中进入选择分组界面
	 * @param model
	 * @param deviceTypeId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/configGroupFromItem")
	public String configGroupFromItem(Model model, String deviceTypeId,String ids) throws Exception {
		List<VmsGroup> groupList = goupService.selectListByObj(null);
		model.addAttribute("groupList", groupList);//监控项
		model.addAttribute("deviceTypeId", deviceTypeId);//设备类型
		model.addAttribute("ids", ids);//监控项集合
		return "configuration/threshold/groupChooseInItemTree";
	}
	
	/**
	 * 进入阀值设置页面
	 * @param model
	 * @param devicePropertyId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/thresholdSet")
	public String thresholdSet(Model model, Integer devicePropertyId,Integer deviceitemId) throws Exception {
		VmsDeviceItem deviceItem = deviceItemService.selectByPrimaryKey(deviceitemId);
		
		model.addAttribute("deviceItem", deviceItem);//所配置的监控项
		List<VmsAlarmLevel> alarmList = alarmLevelService.selectListByObj(null);
		model.addAttribute("alarmList", alarmList);//告警等级
		
		VmsThreshold threshold = new VmsThreshold();
		threshold.setDevicePropertyId(devicePropertyId);
		List<VmsThreshold> thredholdList = thresholdService.selectListByObj(threshold);
		model.addAttribute("thredholdList", thredholdList);//查出现有的阀值
		model.addAttribute("devicePropertyId", devicePropertyId);
		return "configuration/threshold/thresholdSetTree";
	}
	
	/**
	 * 进入阀值那就页面
	 * @param model
	 * @param devicePropertyId 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/thresholdEditBefore")
	public String thresholdEditBefore(Model model, Integer devicePropertyId) throws Exception {
		VmsDeviceProperty record = new VmsDeviceProperty();
		record.setId(devicePropertyId);
		List<VmsDeviceProperty> recordList = devicePropertyService.selectConbinedListByObj(record);
		if(recordList!=null){
			model.addAttribute("deviceProperty", recordList.get(0));
		}
		return "configuration/threshold/thresholdEditTree";
	}
	
	/**
	 * 名称重复问题验证
	 * @param alarmLevel
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/validateThresholdName")
	public void validateThresholdName(VmsThreshold threshold, HttpServletResponse response) throws Exception {
		long total = thresholdService.selectCountByObj(threshold);
		if(total>0) {
			PageUtil.toBeJsonByMap("false","","",response);
			return;
		}
		PageUtil.toBeJsonByMap("true","","",response);
	}
	
	/**
	 * 增加告警阀值
	 * @param alarmLevel
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping("/addThreshold")
	public void addThreshold(VmsThreshold threshold, HttpServletResponse response, HttpServletRequest request) throws Exception {
		try {
			thresholdService.insertSelective(threshold);
			//写入日志
			LogUtil.writeLog(request, LogEnum.LogLevel.ADD, LogEnum.OperatorModule.THRESHOLD_MODULE, LogEnum.OperatorType.INSERTOPERATION, "添加告警阀值："+threshold.getName());
			PageUtil.toBeJsonByMap("true","","",response);
		} catch(Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			PageUtil.toBeJsonByMap("false","新增失败，请确认！","",response);
		}
	}
	
	/**
	 * 编辑告警阀值
	 * @param threshold
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping("/updateThreshold")
	public void updateThreshold(VmsThreshold threshold, HttpServletResponse response, HttpServletRequest request) throws Exception {
		try {
			thresholdService.updateByPrimaryKeySelective(threshold);
			//写入日志
			LogUtil.writeLog(request, LogEnum.LogLevel.MODIFY, LogEnum.OperatorModule.THRESHOLD_MODULE, LogEnum.OperatorType.UPDATEOPERATION, "修改告警阀值："+threshold.getName());
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
	@RequestMapping("/modifyThresholdBefore")
	public String modifyThresholdBefore(Model model,Integer id,HttpServletResponse response)throws Exception{
		VmsThreshold threshold = thresholdService.selectByPrimaryKey(id);
		model.addAttribute("id", id);
		model.addAttribute("threshold", threshold);
		return "configuration/threshold/modifyThreshold";
	}
	
	/**
	 * 修改
	 * @param alarmLevel
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping("/modifyThreshold")
	public void modifyThreshold(VmsThreshold threshold, HttpServletResponse response, HttpServletRequest request) throws Exception {
		try {
			thresholdService.updateByPrimaryKeySelective(threshold);
			//写入日志
			LogUtil.writeLog(request, LogEnum.LogLevel.MODIFY, LogEnum.OperatorModule.THRESHOLD_MODULE, LogEnum.OperatorType.UPDATEOPERATION, null);
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
	@RequestMapping("/deleteThreshold")
	public void deleteThreshold(Integer id, HttpServletResponse response, HttpServletRequest request) throws Exception {
		try {
			thresholdService.deleteByPrimaryKey(id);
			//写入日志
			LogUtil.writeLog(request, LogEnum.LogLevel.DELETE, LogEnum.OperatorModule.THRESHOLD_MODULE, LogEnum.OperatorType.PHYSICALDELOPERATION, "删除告警等级");
			PageUtil.toBeJsonByMap("true","删除成功！","",response);
		} catch(Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			PageUtil.toBeJsonByMap("false","删除失败，请确认！","",response);
		}
	}
}
