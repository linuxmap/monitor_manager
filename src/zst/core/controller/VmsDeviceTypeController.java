package zst.core.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import zst.core.entity.VmsDeviceType;
import zst.core.service.IVmsDeviceTypeService;
import zst.extend.util.PageUtil;

@Controller
@RequestMapping("/deviceType")
public class VmsDeviceTypeController {

	private static final Log logger = LogFactory.getLog(VmsDeviceTypeController.class);
	
	@Resource
	private IVmsDeviceTypeService typeService;
	
	/**
	 * 添加设备类型
	 * @param deviceType
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/addDeviceType")
	public void addUser(VmsDeviceType deviceType, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			//设置是否需要登录信息
			
			typeService.insertSelective(deviceType);
			//写入日志
//			LogUtil.writeLog(request, LogEnum.LogType.OPERATIONLOG, LogEnum.OperatorModule.SYSUSERMODULE, LogEnum.OperatorType.INSERTOPERATOR, null);
			PageUtil.toBeJsonByMap("true",""+deviceType.getId(),deviceType.getLoginflag(),response);
		} catch(Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			PageUtil.toBeJsonByMap("false","新增失败，请确认！","",response);
		}
	}
	
	/**
	 * 名称重复问题验证
	 * @param deviceType
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/validateTypeName")
	public void validateTypeName(VmsDeviceType deviceType, HttpServletResponse response) throws Exception {
		long total = typeService.selectCountByObj(deviceType);
		if(total>0) {
			PageUtil.toBeJsonByMap("false","","",response);
			return;
		}
		PageUtil.toBeJsonByMap("true","","",response);
	}
}
