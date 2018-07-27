package zst.core.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import zst.core.entity.VmsModel;
import zst.core.service.IVmsModelService;
import zst.extend.util.PageUtil;

@Controller
@RequestMapping("/vmsModel")
public class VmsModelController {
	
	private static final Log logger = LogFactory.getLog(VmsModelController.class);
	
	@Resource
	private IVmsModelService modelService;
	
	/**
	 * 添加产品
	 * @param product
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/addModel")
	public void addModel(VmsModel vmsModel, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			modelService.insertSelective(vmsModel);
			//写入日志
//			LogUtil.writeLog(request, LogEnum.LogType.OPERATIONLOG, LogEnum.OperatorModule.SYSUSERMODULE, LogEnum.OperatorType.INSERTOPERATOR, null);
			PageUtil.toBeJsonByMap("true",""+vmsModel.getModelId(),"",response);
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
	@RequestMapping("/validateModelName")
	public void validateModelName(VmsModel vmsModel, HttpServletResponse response) throws Exception {
		long total = modelService.selectCountByObj(vmsModel);
		if(total>0) {
			PageUtil.toBeJsonByMap("false","","",response);
			return;
		}
		PageUtil.toBeJsonByMap("true","","",response);
	}
	
	/**
	 * 通过设备类型和产品类型过滤设备信号
	 * @param vmsModel
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/selectModelByDeviceProduct")
	public void selectModelByDeviceProduct(VmsModel vmsModel, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			List<VmsModel> modelList = modelService.selectListByObj(vmsModel);
			PageUtil.toBeJsonByMap("true","",modelList,response);//TODO 待优化，改成json更合适
		} catch(Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			PageUtil.toBeJsonByMap("false","新增失败，请确认！","",response);
		}
	}
}
