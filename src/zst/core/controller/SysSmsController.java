package zst.core.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import zst.core.entity.VmsSms;
import zst.core.service.IVmsSmsService;
import zst.extend.enums.LogEnum;
import zst.extend.exception.PlatformException;
import zst.extend.page.PageBean;
import zst.extend.util.LogUtil;

/**
 * 短信发送记录查询和导出
 * @author Ablert
 * @date 2018-1-25 下午5:48:23
 */
@Controller
@RequestMapping("/sms")
public class SysSmsController {
	
	private static final Log logger = LogFactory.getLog(SysSmsController.class);

	@Resource
	private IVmsSmsService smsService;
	
	/**
	 * 分页查询短信发送记录
	 * @param model
	 * @param vmsSms
	 * @param rows
	 * @param page
	 * @param funtId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getSmsList")
	public String getSmsList(Model model,VmsSms vmsSms, String rows,String page ,Integer funtId) throws Exception{
		logger.info("****分页查询短信发送记录开始***getSmsList***start***");
		try {
			//设置分页参数
			vmsSms.setPage(rows, page);
			//定义操作日志集合
			List<VmsSms> smsList = null;
			//条件查询操作日志条数
			long totalRows = smsService.selectCountByObj(vmsSms);
			if(0<totalRows){
				smsList = smsService.selectListByObj(vmsSms);
			}
			//设置分页
			PageBean myPage = PageBean.PageSetParameter(rows,page,totalRows);
			model.addAttribute("list",smsList);
			model.addAttribute("myPage", myPage);
			model.addAttribute("vmsSms", vmsSms);
			model.addAttribute("totalRows", totalRows);//将个数传到后台用于记录判断
			logger.info("****分页查询短信发送记录结束***getSmsList***end***");
			return "sys/sms/smsList";
		} catch (Exception e) {
			logger.error("****分页查询操作日志结束***getSmsList***返回异常信息");
			e.printStackTrace();
			throw new PlatformException(e.getMessage());
		}
	}
	
	/**
	 * 跳转查看短信发送日志详情
	 * @param model
	 * @param vmsSms
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("smsDetail")
	public String smsDetail(Model model,VmsSms vmsSms) throws Exception{
		try {
			logger.info("****跳转查看短信发送记录详情***smsDetail***start");
			//根据主键主键查询操作日志详情
			vmsSms = smsService.selectByPrimaryKey(vmsSms.getId());
			//设置返回信息
			model.addAttribute("vmsSms", vmsSms);
			logger.info("****跳转查看短信发送记录详情***smsDetail***end");
			return "sys/sms/smsDetail";
		} catch (Exception e) {
			logger.error("****跳转查看短信发送记录详情***smsDetail***返回异常信息");
			throw new PlatformException(e.getMessage());
		}
	}
	
	/**
	 * 导出日志信息
	 * @param vmsSms
	 * @param response
	 * @throws PlatformException
	 */
	@RequestMapping("/exportSms")
	public void exportSms(VmsSms vmsSms,HttpServletRequest request, HttpServletResponse response) throws PlatformException {
		response.setContentType("application/binary;charset=UTF-8");
		try {
			List<VmsSms> smsList = smsService.selectListByObj(vmsSms);
			// 获取需要导出的数据List
			// 使用方法生成excle模板样式
			HSSFWorkbook workbook = smsService.createExcel(smsList);
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss"); // 定义文件名格式
			// 定义excle名称 ISO-8859-1防止名称乱码
			String msg = new String(
					("短信发送记录信息_" + format.format(new Date()) + ".xls").getBytes(),"ISO-8859-1");
			// 以导出时间作为文件名
			response.setContentType("application/vnd.ms-excel");
			response.addHeader("Content-Disposition", "attachment;filename=" + msg);
			workbook.write(response.getOutputStream());
		    LogUtil.writeLog(request, LogEnum.LogLevel.QUERY, LogEnum.OperatorModule.MESSAGE_QUERY, LogEnum.OperatorType.QUERYOPERATION, "导出短信发送记录");
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
			throw new PlatformException(e.getMessage());
		}
		
	}
}
