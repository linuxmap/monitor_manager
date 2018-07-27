package zst.core.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;

import zst.core.dao.VmsSmsMapper;
import zst.core.entity.VmsSms;
import zst.core.service.IVmsSmsService;
import zst.extend.util.CollectionUtil;
import zst.extend.util.CommonUtil;

@Service
public class VmsSmsService implements IVmsSmsService {
	
	@Resource
	private VmsSmsMapper smsMapper;

	@Override
	public int deleteByPrimaryKey(Integer id) throws Exception {
		return smsMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(VmsSms record) throws Exception {
		return smsMapper.insert(record);
	}

	@Override
	public int insertSelective(VmsSms record) throws Exception {
		return smsMapper.insertSelective(record);
	}

	@Override
	public VmsSms selectByPrimaryKey(Integer id) throws Exception {
		return smsMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(VmsSms record) throws Exception {
		return smsMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(VmsSms record) throws Exception {
		return smsMapper.updateByPrimaryKey(record);
	}

	@Override
	public long selectCountByObj(VmsSms vmsSms) throws Exception {
		return smsMapper.selectCountByObj(vmsSms);
	}

	@Override
	public List<VmsSms> selectListByObj(VmsSms vmsSms) throws Exception {
		return smsMapper.selectListByObj(vmsSms);
	}

	@Override
	public HSSFWorkbook createExcel(List<VmsSms> vmsList) throws Exception {
		// 创建一个webbook，对应一个excel文件 
	    HSSFWorkbook workbook = new HSSFWorkbook(); 
	    // 在webbook中添加一个sheet,对应excel文件中的sheet 
	    HSSFSheet sheet = workbook.createSheet("短信发送记录表");
	    // 设置列宽 
	    sheet.setColumnWidth(0, 25 * 100); 
	    sheet.setColumnWidth(1, 35 * 100); 
	    sheet.setColumnWidth(2, 35 * 100); 
	    sheet.setColumnWidth(3, 30 * 100); 
	    sheet.setColumnWidth(4, 100 * 100); 
	    sheet.setColumnWidth(5, 30 * 100);
	    sheet.setColumnWidth(6, 60 * 100); 
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
	    cell.setCellValue("操作人"); 
	    cell.setCellStyle(style); 
	  
	    cell = row.createCell(2); 
	    cell.setCellValue("手机号码"); 
	    cell.setCellStyle(style); 
	  
	    cell = row.createCell(3); 
	    cell.setCellValue("状态"); 
	    cell.setCellStyle(style); 
	  
	    cell = row.createCell(4); 
	    cell.setCellValue("内容");
	    cell.setCellStyle(style); 
	  
	    cell = row.createCell(5); 
	    cell.setCellValue("次数"); 
	    cell.setCellStyle(style);
	    
	    cell = row.createCell(6); 
	    cell.setCellValue("时间"); 
	    cell.setCellStyle(style);
	    
	    if (CollectionUtil.isNotEmpty(vmsList)) {
	    	for (int i = 0; i < vmsList.size(); i++) {
	    		VmsSms sms = vmsList.get(i);
	    		row = sheet.createRow(i + 1); 
	    		// 创建单元格，并设置值 
	    		// 编号列居左 
	    		HSSFCell c1 = row.createCell(0); 
	    		c1.setCellStyle(style2); 
	    		c1.setCellValue(i + 1);
	    		
	    		HSSFCell c2 = row.createCell(1); 
	    		c2.setCellStyle(style1); 
	    		c2.setCellValue(sms.getUserName());
	    		
	    		HSSFCell c3 = row.createCell(2);
	    		c3.setCellStyle(style1); 
	    		c3.setCellValue(sms.getPhoneno()); 
	    		
	    		HSSFCell c4 = row.createCell(3);
	    		c4.setCellStyle(style1);
	    		if (sms.getStatus().intValue()==1) {
	    			c4.setCellValue("成功");
	    		} else if (sms.getStatus().intValue()==2) {
	    			c4.setCellValue("失败");
	    		}
	    		
	    		HSSFCell c5 = row.createCell(4);
	    		c5.setCellStyle(style1); 
	    		c5.setCellValue(sms.getContent()); 
	    		
	    		HSSFCell c6 = row.createCell(5);
	    		c6.setCellStyle(style1);
	    		c6.setCellValue(sms.getSendcount());
	    		
	    		HSSFCell c7 = row.createCell(6);
	    		c7.setCellStyle(style1);
	    		c7.setCellValue(CommonUtil.getYMdDate(sms.getTime()));
	    	} 
	    }
	    return workbook; 
	}

}
