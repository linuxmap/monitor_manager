package zst.core.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import zst.core.entity.VmsDeviceProperty;
import zst.core.service.IVmsDevicePropertyService;
import zst.extend.enums.LogEnum;
import zst.extend.util.LogUtil;
import zst.extend.util.PageUtil;

@Controller
@RequestMapping("/deviceProperty")
public class VmsDevicePropertyController {

	private static final Log logger = LogFactory.getLog(VmsDevicePropertyController.class);
	
	@Resource
	private IVmsDevicePropertyService devicePropertyService;
	
	/**
	 * 批量添加监控项和设备类型的关系
	 * @param devicePropertyId
	 * @param ids
	 * @param deviceProperty
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/saveDeviceTypeAndItem")
	public void saveDeviceTypeAndItem(Integer typeId ,String ids,Integer groupId, VmsDeviceProperty deviceProperty,HttpServletRequest request, HttpServletResponse response) throws Exception {
		try{
			List<VmsDeviceProperty> devicePs = new ArrayList<VmsDeviceProperty>();
			if(ids!=null && ids.length()>0){
				String[] idArray = ids.split(",");
				for(int i=0; i<idArray.length; i++){
					deviceProperty = new VmsDeviceProperty();
					deviceProperty.setDevicetypeId(typeId);
					deviceProperty.setDeviceitemId(Integer.valueOf(idArray[i]));
					deviceProperty.setOrderNumber(1);//默认1
					deviceProperty.setGroupId(groupId);
					devicePs.add(deviceProperty);
				}
				//批量添加到数据库
				devicePropertyService.addList(devicePs);
			}
			LogUtil.writeLog(request, LogEnum.LogLevel.ADD, LogEnum.OperatorModule.THRESHOLD_MODULE, LogEnum.OperatorType.INSERTOPERATION, "添加设备类型对应的监控项");
			PageUtil.toBeJsonByMap("true","","",response);
		}catch (Exception e) {
			PageUtil.toBeJsonByMap("false","添加关系失败","",response);
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * 批量修改关系device_property
	 * @param typeId
	 * @param ids
	 * @param groupId
	 * @param deviceProperty
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/updateDeviceTypeAndItem")
	public void updateDeviceTypeAndItem(Integer typeId ,String ids,Integer groupId, VmsDeviceProperty deviceProperty,HttpServletRequest request, HttpServletResponse response) throws Exception {
		try{
			List<VmsDeviceProperty> devicePs = new ArrayList<VmsDeviceProperty>();
			if(ids!=null && ids.length()>0){
				String[] idArray = ids.split(",");
				for(int i=0; i<idArray.length; i++){
					deviceProperty = new VmsDeviceProperty();
					deviceProperty.setId(Integer.valueOf(idArray[i]));
					deviceProperty.setGroupId(groupId);//只设置分组
					devicePs.add(deviceProperty);
				}
				//批量修改到数据库
				devicePropertyService.updateList(devicePs);
			}
			LogUtil.writeLog(request, LogEnum.LogLevel.MODIFY, LogEnum.OperatorModule.THRESHOLD_MODULE, LogEnum.OperatorType.UPDATEOPERATION, "修改设备类型对应的监控项");
			PageUtil.toBeJsonByMap("true","","",response);
		}catch (Exception e) {
			PageUtil.toBeJsonByMap("false","设置分组失败","",response);
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}
	
	/**
	 * 删除设备类型对应的监控项，批量删除device_property中的数据，根据其id
	 * @param orgIds
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping("/deleteBatch")
	public void deleteBatch(@RequestParam(value = "dpIds[]")Integer[] dpIds, HttpServletResponse response, HttpServletRequest request) throws Exception {
		try {
			if (dpIds != null && dpIds.length>0) {
				List<Integer> list = Arrays.asList(dpIds);
				devicePropertyService.deleteBatchByIds(list);
				//写入日志
				LogUtil.writeLog(request, LogEnum.LogLevel.DELETE, LogEnum.OperatorModule.THRESHOLD_MODULE, LogEnum.OperatorType.PHYSICALDELOPERATION, "删除设备类型对应的监控项");
				PageUtil.toBeJsonByMap("true","删除成功！","",response);
			} else {
				PageUtil.toBeJsonByMap("false","传递空值！","",response);
			}
		} catch(Exception e) {
			logger.error(e.getMessage());
			PageUtil.toBeJsonByMap("false","删除失败，请确认！","",response);
		}
	}
}
