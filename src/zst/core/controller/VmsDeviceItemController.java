package zst.core.controller;

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

import zst.core.entity.VmsDeviceItem;
import zst.core.service.IVmsDeviceItemService;
import zst.extend.enums.LogEnum;
import zst.extend.util.LogUtil;
import zst.extend.util.PageUtil;

@Controller
@RequestMapping("/deviceItem")
public class VmsDeviceItemController {
	
	private static final Log logger = LogFactory.getLog(VmsDeviceItemController.class);

	@Resource
	private IVmsDeviceItemService deviceItemService;
	
	
	/**
	 * 在告警配置中添加监控项
	 * @param deviceItem
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/addDeviceItemInConfig")
	public void addDeviceItemInConfig(VmsDeviceItem deviceItem,HttpServletRequest request, HttpServletResponse response) throws Exception {
		try{
			deviceItemService.insertSelective(deviceItem);
			//LogUtil.writeLog(request, LogEnum.LogLevel.ADD, LogEnum.OperatorModule.THRESHOLD_MODULE, LogEnum.OperatorType.INSERTOPERATION, null);
			PageUtil.toBeJsonByMap("true",deviceItem.getId()+"","",response);
		}catch (Exception e) {
			PageUtil.toBeJsonByMap("false","","",response);
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * 在告警配置中修改监控项
	 * @param deviceItem
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/modifyDeviceItemInConfig")
	public void modifyDeviceItemInConfig(VmsDeviceItem deviceItem, HttpServletResponse response) throws Exception {
		try{
			deviceItemService.updateByPrimaryKeySelective(deviceItem);
			
			PageUtil.toBeJsonByMap("true",deviceItem.getId()+"","",response);
		}catch (Exception e) {
			PageUtil.toBeJsonByMap("false","","",response);
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * 在告警配置中删除监控项
	 * @param id
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/deleteDeviceItemInConfig")
	public void deleteDeviceItemInConfig(Integer id,HttpServletRequest request, HttpServletResponse response) throws Exception {
		try{
			deviceItemService.deleteByPrimaryKey(id);
			LogUtil.writeLog(request, LogEnum.LogLevel.DELETE, LogEnum.OperatorModule.THRESHOLD_MODULE, LogEnum.OperatorType.PHYSICALDELOPERATION, null);
			PageUtil.toBeJsonByMap("true","","",response);
		}catch (Exception e) {
			PageUtil.toBeJsonByMap("false","","",response);
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * 名称重复问题验证
	 * @param deviceItem
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/validateLevelName")
	public void validateLevelName(VmsDeviceItem deviceItem, HttpServletResponse response) throws Exception {
		long total = deviceItemService.selectCountByObj(deviceItem);
		if(total>0) {
			PageUtil.toBeJsonByMap("false","","",response);
			return;
		}
		PageUtil.toBeJsonByMap("true","","",response);
	}
	
	/**
	 * 批量删除监控项
	 * @param itemArr
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping("/deleteBatchDeviceItem")
	public void deleteBatchDeviceItem(@RequestParam(value = "itemArr[]")Integer[] itemArr, HttpServletResponse response, HttpServletRequest request) throws Exception { 
		try{
			List<Integer> itemIdList = Arrays.asList(itemArr);
			deviceItemService.deleteBatchByIds(itemIdList);
			PageUtil.toBeJsonByMap("true","删除成功！","",response);
			//写入日志
			LogUtil.writeLog(request, LogEnum.LogLevel.DELETE, LogEnum.OperatorModule.THRESHOLD_MODULE, LogEnum.OperatorType.PHYSICALDELOPERATION, "批量删除监控项");
		}catch(Exception e) {
			logger.error(e.getMessage());
			PageUtil.toBeJsonByMap("false","删除失败，请确认！","",response);
			e.printStackTrace();
		}
	}
}
