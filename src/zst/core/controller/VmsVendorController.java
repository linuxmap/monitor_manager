package zst.core.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import zst.core.entity.VmsVendor;
import zst.core.service.IVmsVendorService;
import zst.extend.exception.PlatformException;
import zst.extend.page.PageBean;
import zst.extend.util.PageUtil;

@Controller
@RequestMapping("/vmsvendor")
public class VmsVendorController {

	private static final Log logger = LogFactory.getLog(VmsVendorController.class);
	
	@Resource
	private IVmsVendorService iVmsVendorService;
	
	@RequestMapping("queryVendors")
	public String queryVendors(Model model, String rows,String page) throws PlatformException{
		try {
			VmsVendor vmsVendor = new VmsVendor();
			List<VmsVendor> vmsVendorList = null;
			vmsVendor.setPage(rows, page);
			long totalRows = iVmsVendorService.selectCountByObj(vmsVendor);
			if(0<totalRows){
				//查询字典条目集合
				vmsVendorList = iVmsVendorService.selectListByObj(vmsVendor);
			}
			//设置分页
			PageBean myPage = PageBean.PageSetParameter(rows,page,totalRows);
			model.addAttribute("list",vmsVendorList);
			model.addAttribute("myPage", myPage);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new PlatformException(e.getMessage());
		}
		return "sys/dictionary/listDictionaryItemFactory";
	}
	
	/**
	 * 添加厂商
	 * @param vmsVendor
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/addVendor")
	public void addVendor(VmsVendor vmsVendor, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			vmsVendor.setFlag(1);
			iVmsVendorService.insertSelective(vmsVendor);
			//写入日志
//			LogUtil.writeLog(request, LogEnum.LogType.OPERATIONLOG, LogEnum.OperatorModule.SYSUSERMODULE, LogEnum.OperatorType.INSERTOPERATOR, null);
			PageUtil.toBeJsonByMap("true",""+vmsVendor.getVendorId(),"",response);
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
	@RequestMapping("/validateVendorName")
	public void validateVendorName(VmsVendor vmsVendor, HttpServletResponse response) throws Exception {
		long total = iVmsVendorService.selectCountByObj(vmsVendor);
		if(total>0) {
			PageUtil.toBeJsonByMap("false","","",response);
			return;
		}
		PageUtil.toBeJsonByMap("true","","",response);
	}
}
