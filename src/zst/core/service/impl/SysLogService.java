package zst.core.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;

import zst.core.dao.SysLogMapper;
import zst.core.entity.SysLog;
import zst.core.entity.SysUser;
import zst.core.service.ISysLogService;
import zst.extend.common.Constant;
import zst.extend.enums.LogEnum;
import zst.extend.util.CollectionUtil;
import zst.extend.util.CommonUtil;
import zst.extend.util.StringUtil;

/**
 * 日志操作记录相关业务接口
 * @author Ablert
 * @date 2018-1-25 下午5:38:20
 */
@Service
public class SysLogService implements ISysLogService{
	
	private static final Log logger = LogFactory.getLog(SysLogService.class);
	
	@Resource
	private SysLogMapper logMapper;

	@Override
	public int insertSelective(SysLog record) {
		return logMapper.insertSelective(record);
	}

	@Override
	public SysLog selectByPrimaryKey(Integer id) {
		return logMapper.selectByPrimaryKey(id);
	}

	@Override
	public long selectCountByObj(SysLog sysLog) throws Exception {
		return logMapper.selectCountByObj(sysLog);
	}

	@Override
	public List<SysLog> selectListByObj(SysLog sysLog) throws Exception {
		return logMapper.selectListByObj(sysLog);
	}

	@Override
	public int insertLog(HttpServletRequest request, Integer logLevel, String operateModule, Integer operateType, String operateAction) throws Exception {
		//获取登陆人
		SysUser user = (SysUser) request.getSession().getAttribute(Constant.USER_INFO);//获取session中user
		if (null ==user) {
			logger.error("****insertSysJournalOperation***用户信息不存在！***");
			return 0;
		}
		//定义日志操作记录对象
		SysLog sysLog = new SysLog();
		Date date = new Date();
		//获取ip地址
		String ip = CommonUtil.getIpAddr(request);
		//获取登陆人id
		sysLog.setUserId(user.getUserId());
		//操作时间
		sysLog.setOperatorDate(date);
		//操作模块
		sysLog.setOperatorModule(operateModule);
		//操作类型
		sysLog.setOperatorType(operateType);
		//操作描述
		if (StringUtil.validateStr(operateAction)) {
			String desc = String.format("%s，ip:%s。",operateAction,ip);
			sysLog.setOperatorDesc(desc);
		} else {
			String desc = String.format("对%s模块进行操作，ip:%s。",operateModule,ip);
			sysLog.setOperatorDesc(desc);
		}
		//哪个端
		sysLog.setLogGroup(LogEnum.MANAGER_END);
		//日志级别
		sysLog.setLogLevel(logLevel);
		//服务器创建日志时间
		sysLog.setCreateTime(date);
		return logMapper.insertSelective(sysLog);
	}

	@Override
	public HSSFWorkbook createExcel(List<SysLog> logs) throws Exception {
		// 创建一个webbook，对应一个excel文件 
	    HSSFWorkbook workbook = new HSSFWorkbook(); 
	    // 在webbook中添加一个sheet,对应excel文件中的sheet 
	    HSSFSheet sheet = workbook.createSheet("日志信息表");
	    // 设置列宽 
	    sheet.setColumnWidth(0, 25 * 100); 
	    sheet.setColumnWidth(1, 35 * 100); 
	    sheet.setColumnWidth(2, 35 * 100); 
	    sheet.setColumnWidth(3, 40 * 100); 
	    sheet.setColumnWidth(4, 100 * 100); 
	    sheet.setColumnWidth(5, 45 * 100); 
	    // 在sheet中添加表头第0行 
	    HSSFRow row = sheet.createRow(0); 
	    // 创建单元格，并设置表头，设置表头居中 
	    HSSFCellStyle style = workbook.createCellStyle(); 
	    // 创建一个居中格式 
	    style.setAlignment(HSSFCellStyle.ALIGN_CENTER); 
	    // 带边框 
	    style.setBorderBottom(HSSFCellStyle.BORDER_THIN); 
	    // 生成一个字体 
	    HSSFFont font = workbook.createFont(); 
	    // 字体增粗 
	    font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); 
	    // 字体大小 
	    font.setFontHeightInPoints((short) 12); 
	    // 把字体应用到当前的样式 
	    style.setFont(font);
	    // 单独设置整列居中或居左 
	    HSSFCellStyle style1 = workbook.createCellStyle(); 
	    style1.setAlignment(HSSFCellStyle.ALIGN_CENTER); 
	    HSSFCellStyle style2 = workbook.createCellStyle(); 
	    style2.setAlignment(HSSFCellStyle.ALIGN_LEFT); 
	  
	    HSSFCellStyle style3 = workbook.createCellStyle(); 
	    style3.setAlignment(HSSFCellStyle.ALIGN_LEFT); 
	    HSSFFont hssfFont = workbook.createFont(); 
	    hssfFont.setColor(HSSFFont.COLOR_RED); 
	    hssfFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); 
	    style3.setFont(hssfFont); 
	  
	    HSSFCellStyle style4 = workbook.createCellStyle(); 
	    style4.setAlignment(HSSFCellStyle.ALIGN_LEFT); 
	    HSSFFont hssfFont1 = workbook.createFont(); 
	    hssfFont1.setColor(HSSFFont.COLOR_NORMAL); 
	    hssfFont1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); 
	    style4.setFont(hssfFont1);
	    
	    HSSFCell cell = row.createCell(0);
	    cell.setCellValue("序号"); 
	    cell.setCellStyle(style); 
	  
	    cell = row.createCell(1); 
	    cell.setCellValue("日志模块"); 
	    cell.setCellStyle(style); 
	  
	    cell = row.createCell(2); 
	    cell.setCellValue("操作人"); 
	    cell.setCellStyle(style); 
	  
	    cell = row.createCell(3); 
	    cell.setCellValue("平台"); 
	    cell.setCellStyle(style); 
	  
	    cell = row.createCell(4); 
	    cell.setCellValue("日志描述");
	    cell.setCellStyle(style); 
	  
	    cell = row.createCell(5); 
	    cell.setCellValue("操作时间"); 
	    cell.setCellStyle(style); 
	    
	    if (CollectionUtil.isNotEmpty(logs)) {
	    	for (int i = 0; i < logs.size(); i++) {
	    		SysLog sysLog = logs.get(i);
	    		row = sheet.createRow(i + 1); 
	    		// 创建单元格，并设置值 
	    		// 编号列居左 
	    		HSSFCell c1 = row.createCell(0); 
	    		c1.setCellStyle(style2); 
	    		c1.setCellValue(i + 1);
	    		
	    		HSSFCell c2 = row.createCell(1); 
	    		c2.setCellStyle(style1); 
	    		c2.setCellValue(sysLog.getOperatorModule());//日志模块 
	    		
	    		HSSFCell c3 = row.createCell(2);//操作人
	    		c3.setCellStyle(style1); 
	    		c3.setCellValue(sysLog.getUserName()); 
	    		
	    		HSSFCell c4 = row.createCell(3);//平台
	    		c4.setCellStyle(style1);
	    		if (sysLog.getLogGroup().intValue() == LogEnum.CLIENT_END) {
	    			c4.setCellValue(LogEnum.CLIENT);
	    		} else if (sysLog.getLogGroup().intValue() == LogEnum.MANAGER_END) {
	    			c4.setCellValue(LogEnum.MANAGER);
	    		}
	    		
	    		HSSFCell c5 = row.createCell(4);//日志描述
	    		c5.setCellStyle(style1); 
	    		c5.setCellValue(sysLog.getOperatorDesc()); 
	    		
	    		HSSFCell c6 = row.createCell(5);//操作时间
	    		c6.setCellStyle(style1);
	    		c6.setCellValue(CommonUtil.getYMdDate(sysLog.getOperatorDate()));
	    	} 
	    }
	    return workbook; 
	}

}
