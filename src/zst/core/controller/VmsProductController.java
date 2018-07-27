package zst.core.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import zst.core.entity.VmsProduct;
import zst.core.service.IVmsProductService;
import zst.extend.util.PageUtil;

@Controller
@RequestMapping("/product")
public class VmsProductController {
	
	private static final Log logger = LogFactory.getLog(VmsProductController.class);
	
	@Resource
	private IVmsProductService productService;
	
	/**
	 * 添加产品
	 * @param product
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/addProduct")
	public void addVendor(VmsProduct product, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			product.setFlag(1);
			productService.insertSelective(product);
			//写入日志
//			LogUtil.writeLog(request, LogEnum.LogType.OPERATIONLOG, LogEnum.OperatorModule.SYSUSERMODULE, LogEnum.OperatorType.INSERTOPERATOR, null);
			PageUtil.toBeJsonByMap("true",""+product.getProductId(),"",response);
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
	@RequestMapping("/validateProductName")
	public void validateProductName(VmsProduct product, HttpServletResponse response) throws Exception {
		long total = productService.selectCountByObj(product);
		if(total>0) {
			PageUtil.toBeJsonByMap("false","","",response);
			return;
		}
		PageUtil.toBeJsonByMap("true","","",response);
	}
}
