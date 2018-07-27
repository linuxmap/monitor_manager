package zst.core.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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

import zst.core.entity.SysBaseInfo;
import zst.core.entity.SysLog;
import zst.core.service.ISysBaseService;
import zst.core.service.ISysLogService;
import zst.extend.enums.LogEnum;
import zst.extend.exception.PlatformException;
import zst.extend.page.PageBean;
import zst.extend.util.CollectionUtil;
import zst.extend.util.LogUtil;

/**
 * 操作日志查询
 * @author Ablert
 * @date 2018-1-25 下午5:48:23
 */
@Controller
@RequestMapping("/log")
public class SysLogController {
	
	private static final Log logger = LogFactory.getLog(SysLogController.class);

	private @Resource ISysLogService logService;
	
	@Resource
	private ISysBaseService baseService;
	
	private static final String baseName = "SaveLogLevel";
	private static final String baseGroup = "SystemSetting";
	/**
	 * 分页查询操作日志
	 * @param model
	 * @param sysLog
	 * @param rows
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getLogList")
	public String getLogList(Model model,SysLog sysLog, String rows,String page) throws Exception{
		logger.info("****分页查询操作日志开始***queryLogList***start***");
		try {
			//从配置表中查询日志的级别 比如5 则查询日志级别小于等于5的日志
			SysBaseInfo baseInfo = new SysBaseInfo();
			baseInfo.setGroup(baseGroup);
			baseInfo.setName(baseName);
			List<SysBaseInfo> baseList = baseService.selectListByObj(baseInfo);
			if (CollectionUtil.isNotEmpty(baseList)) {
				String value = baseList.get(0).getValue();
				Integer levelMax = Integer.valueOf(value);
				List<Integer> queryLevelList = new ArrayList<Integer>();
				if (sysLog.getLogLevel() == null) {
					//如果是直接进入日志列表
					for (int i=1; i<=levelMax.intValue(); i++) {
						queryLevelList.add(i);
					}
				} else {
					//如果是查询
					queryLevelList.add(sysLog.getLogLevel());
				}
				if (CollectionUtil.isNotEmpty(queryLevelList)) {
					sysLog.setQueryLevelList(queryLevelList);
				}
			}
			//设置分页参数
			sysLog.setPage(rows, page);
			//定义操作日志集合
			List<SysLog> logList = null;
			//条件查询操作日志条数
			long totalRows = logService.selectCountByObj(sysLog);
			if(0<totalRows){
				logList = logService.selectListByObj(sysLog);
			}
			//登录模块查询集合
			String[] clientModuleArray = {"电子地图", "实时视频", "重大作业", "运维管理" , "告警联动" ,"录像回放","主界面", "移动视频", 
					"登录", "组织机构", "用户管理", "菜单管理", "数据权限管理", "功能权限管理", "系统配置", "告警设置" , "告警阈值配置", 
					"设备管理", "资源整合", "短信配置", "短信记录查询", "日志查询"};
			List<String> moduleList = Arrays.asList(clientModuleArray);
			//设置分页
			PageBean myPage = PageBean.PageSetParameter(rows,page,totalRows);
			model.addAttribute("list", logList);
			model.addAttribute("moduleList", moduleList);
			model.addAttribute("myPage", myPage);
			model.addAttribute("sysLog", sysLog);
			model.addAttribute("totalRows", totalRows);//将个数传到后台用于记录判断
			logger.info("****分页查询操作日志结束***queryLogList***end***");
			return "sys/log/logList";
		} catch (Exception e) {
			logger.error("****分页查询操作日志结束***queryLogList***返回异常信息");
			e.printStackTrace();
			throw new PlatformException(e.getMessage());
		}
	}
	
	/**
	 * 跳转查看操作日志详情
	 * @param model
	 * @param sysLog
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("logDetail")
	public String queryLogDetail(Model model,SysLog sysLog) throws Exception{
		try {
			logger.info("****跳转查看操作日志详情***logDetail***start");
			//根据主键主键查询操作日志详情
			sysLog = logService.selectByPrimaryKey(sysLog.getId());
			//设置返回信息
			model.addAttribute("sysLog", sysLog);
			logger.info("****跳转查看操作日志详情***logDetail***end");
			return "sys/log/logDetail";
		} catch (Exception e) {
			logger.error("****跳转查看操作日志详情***logDetail***返回异常信息");
			throw new PlatformException(e.getMessage());
		}
	}
	
	/**
	 * 导出日志信息
	 * @param sysLog
	 * @param response
	 * @throws PlatformException
	 */
	@RequestMapping("/exportLog")
	public void exportLog(SysLog sysLog, HttpServletRequest request, HttpServletResponse response) throws PlatformException {
		response.setContentType("application/binary;charset=UTF-8");
		try {
			//从配置表中查询日志的级别 比如5 则查询日志级别小于等于5的日志
			SysBaseInfo baseInfo = new SysBaseInfo();
			baseInfo.setGroup(baseGroup);
			baseInfo.setName(baseName);
			List<SysBaseInfo> baseList = baseService.selectListByObj(baseInfo);
			if (CollectionUtil.isNotEmpty(baseList)) {
				String value = baseList.get(0).getValue();
				Integer levelMax = Integer.valueOf(value);
				List<Integer> queryLevelList = new ArrayList<Integer>();
				if (sysLog.getLogLevel() == null) {
					//如果是直接进入日志列表
					for (int i=1; i<=levelMax.intValue(); i++) {
						queryLevelList.add(i);
					}
				} else {
					//如果是查询
					queryLevelList.add(sysLog.getLogLevel());
				}
				if (CollectionUtil.isNotEmpty(queryLevelList)) {
					sysLog.setQueryLevelList(queryLevelList);
				}
			}
			List<SysLog> logs = logService.selectListByObj(sysLog);
			// 获取需要导出的数据List
			// 使用方法生成excle模板样式
			HSSFWorkbook workbook = logService.createExcel(logs);
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss"); // 定义文件名格式
			// 定义excle名称 ISO-8859-1防止名称乱码
			String msg = new String(
					("日志信息_" + format.format(new Date()) + ".xls").getBytes(), "ISO-8859-1");
			// 以导出时间作为文件名
			response.setContentType("application/vnd.ms-excel");
			response.addHeader("Content-Disposition", "attachment;filename=" + msg);
			workbook.write(response.getOutputStream());
			LogUtil.writeLog(request, LogEnum.LogLevel.QUERY, LogEnum.OperatorModule.LOG_QUERY, LogEnum.OperatorType.QUERYOPERATION, "导出日志");
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
			throw new PlatformException(e.getMessage());
		}
		
	}
}
