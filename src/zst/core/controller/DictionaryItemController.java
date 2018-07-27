package zst.core.controller;


import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import zst.core.entity.DictionaryInfo;
import zst.core.entity.DictionaryItem;
import zst.core.entity.VmsVendor;
import zst.core.service.IDictionaryInfoService;
import zst.core.service.IDictionaryItemService;
import zst.core.service.IVmsVendorService;
import zst.extend.exception.PlatformException;
import zst.extend.page.PageBean;
import zst.extend.util.CommonUtil;
import zst.extend.util.DicCacheUtil;
import zst.extend.util.PageUtil;
import zst.extend.util.SettingEnums;
/**
 * 字典条目
 * @author songxiang
 */
@Controller
@RequestMapping("/dictionaryItem/*")
public class DictionaryItemController {
	private static final Log logger = LogFactory.getLog(DictionaryItemController.class);
	
	private @Resource IDictionaryItemService dictionaryItemService;
	
	@Resource
	private IDictionaryInfoService dictionaryInfoService;
	
	//注入厂商的service,用于嵌套数据字典
	@Resource
	private IVmsVendorService iVmsVendorService;
	
	/**
	 * 跳转字典新增
	 * @author songxiang
	 * @throws Exception 
	 */
	@RequestMapping("toOpenDictionaryItem")
	public String toOpenDictionaryItem(Model model ,DictionaryItem dictionaryItem,String dictionaryName,String funtType) throws Exception{
		logger.info("****跳转新增或者编辑字典条目页面开始***toAddDictionaryItem***start***");
		if("edit".equals(funtType) || "view".equals(funtType) || "copy".equals(funtType)){
			//根据主键查询字典条目
			dictionaryItem = dictionaryItemService.selectByObj(dictionaryItem);
			
			//获取字典类型名
			DictionaryInfo dicInfo = new DictionaryInfo();
			dicInfo.setDictionaryId(dictionaryItem.getDictionaryId());
			dicInfo.setPage(null, null);
			List<DictionaryInfo> infoList = dictionaryInfoService.selectListByObj(dicInfo);
			if(infoList.size()>0) {
				dictionaryName = infoList.get(0).getDictionaryName();
			}
			if("copy".equals(funtType)) {
				dictionaryItem.setId(null);
				dictionaryItem.setItemId(null);
				dictionaryItem.setItemName(null);
				dictionaryItem.setItemDesc(null);
			}
		}
		model.addAttribute("dictionaryItem", dictionaryItem);
		model.addAttribute("funtType", funtType);
		model.addAttribute("dictionaryName", dictionaryName);

		logger.info("****跳转新增或者编辑字典条目页面结束***toAddDictionaryItem***end***");
		return "sys/dictionary/operateDictionary";
	}
	
	/**
	 * 查看 编辑 复制厂家类型 齐鲁石化专用
	 * @param model
	 * @param vmsVendor
	 * @param funtType
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("toOpenFactoryItem")
	public String toOpenFactoryItem(Model model ,VmsVendor vmsVendor, String funtType) throws Exception{
		logger.info("****跳转新增或者编辑字典条目页面开始***toOpenFactoryItem***start***");
		if("edit".equals(funtType) || "view".equals(funtType) || "copy".equals(funtType)){
			//根据主键查询字典条目
			vmsVendor = iVmsVendorService.selectByPrimaryKey(vmsVendor.getVendorId());
			if("copy".equals(funtType)) {
				vmsVendor.setVendorId(null);
				vmsVendor.setName(null);
				vmsVendor.setOrderNumber(null);
			}
		}
		model.addAttribute("vmsVendor", vmsVendor);
		model.addAttribute("funtType", funtType);

		logger.info("****跳转新增或者编辑字典条目页面结束***toOpenFactoryItem***end***");
		return "sys/dictionary/operateFactoryType";
	}
	
	/**
	 * 增加设备厂家  齐鲁石化
	 * @param vmsVendor
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping("addFactoryItem")
	public void addFactoryItem(VmsVendor vmsVendor,HttpServletResponse response,HttpServletRequest request) throws Exception {
		logger.info("****新增字典条目开始***addFactoryItem***start***");
		try {
			//新增字典条目
			int res = iVmsVendorService.insert(vmsVendor);
			if (1 > res) {
				logger.error("***新增字典条目返回数***res"+res);
				PageUtil.toBeJsonByMap("false","新增失败，请确认！","",response);
			}
			PageUtil.toBeJsonByMap("true","","",response);
			logger.info("****新增字典条目结束***addFactoryItem***end***");
			return;
		} catch (Exception e) {
			logger.error("***新增字典条目异常***"+e.getMessage());
			e.printStackTrace();
			PageUtil.toBeJsonByMap("false","新增失败，请确认！","",response);
		}
	}
	
	
	/**
	 * 修改设备厂家
	 * @param dictionaryItem
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping("updateFactoryItem")
	public void updateFactoryItem(VmsVendor vmsVendor, HttpServletResponse response,HttpServletRequest request) throws Exception {
		logger.info("****修改字典条目开始***updateFactoryItem***start***");
		try {
			int res = iVmsVendorService.updateByPrimaryKeySelective(vmsVendor);
			if(1>res){
				logger.info("****修改字典条目返回数***res"+res);
				PageUtil.toBeJsonByMap("false","修改失败，请确认！","",response);
				return;
			}
			
			PageUtil.toBeJsonByMap("true","","",response);
			logger.info("****修改字典条目结束***updateFactoryItem***end***");
			return;
		} catch (Exception e) {
			logger.error("***修改字典条目异常***"+e.getMessage());
			e.printStackTrace();
			PageUtil.toBeJsonByMap("false","修改失败，请确认！","",response);
		}
	}
	
	/**验证字典条目是否重复 **/
	@RequestMapping("/validateItemId")
	public void validateItemId(DictionaryItem item, HttpServletResponse response) throws Exception {
		item.setItemStatus("1");
		long total = dictionaryItemService.selectCountByObj(item);
		if(total>0) {
			PageUtil.toBeJsonByMap("false","","",response);
			return;
		}
		PageUtil.toBeJsonByMap("true","","",response);
	}
	
	/**
	 * 新增字典条目
	 * @author songxiang
	 */
	@RequestMapping("addDictionaryItem")
	public void addDictionaryItem(DictionaryItem dictionaryItem,HttpServletResponse response,HttpServletRequest request) throws Exception {
		logger.info("****新增字典条目开始***addDictionaryItem***start***");
		try {
			Date date = new Date();
			dictionaryItem.setCreateUserId(CommonUtil.getSessionUser(request).getUserId());
			dictionaryItem.setUpdateTime(date);
			dictionaryItem.setCreateTime(date);
			//新增字典条目
			int res = dictionaryItemService.insertDictionaryItem(dictionaryItem);
			if(1>res){
				logger.error("***新增字典条目返回数***res"+res);
				PageUtil.toBeJsonByMap("false","新增失败，请确认！","",response);
			}
			
			DicCacheUtil.updateDicMap(dictionaryItem.getDictionaryId(),request);//更新字典缓存
			
			PageUtil.toBeJsonByMap("true","","",response);
			logger.info("****新增字典条目结束***addDictionaryItem***end***");
			return;
		} catch (Exception e) {
			logger.error("***新增字典条目异常***"+e.getMessage());
			e.printStackTrace();
			PageUtil.toBeJsonByMap("false","新增失败，请确认！","",response);
		}
	}
	
	/**
	 * 修改字典条目信息
	 * @author songxiang
	 */
	@RequestMapping("updateDictionaryItem")
	public void updateDictionaryItem(DictionaryItem dictionaryItem,HttpServletResponse response,HttpServletRequest request) throws Exception {
		logger.info("****修改字典条目开始***updateDictionaryItem***start***");
		try {
			dictionaryItem.setUpdateTime(new Date());
			//修改字典条目
			int res = dictionaryItemService.updateDictionaryItem(dictionaryItem);
			if(1>res){
				logger.info("****修改字典条目返回数***res"+res);
				PageUtil.toBeJsonByMap("false","修改失败，请确认！","",response);
				return;
			}
			
			DicCacheUtil.updateDicMap(dictionaryItem.getDictionaryId(),request);//更新字典缓存
			
			PageUtil.toBeJsonByMap("true","","",response);
			logger.info("****修改字典条目结束***updateDictionaryItem***end***");
			return;
		} catch (Exception e) {
			logger.error("***修改字典条目异常***"+e.getMessage());
			e.printStackTrace();
			PageUtil.toBeJsonByMap("false","修改失败，请确认！","",response);
		}
	}
	
	/**
	 * 齐鲁石化变更  工厂配置 设备类型配置 需要专门判断，展示独立设计的表
	 * @author songxiang
	 */
	@RequestMapping("queryDictionaryItem")
	public String queryDictionaryItem(Model model,DictionaryItem dictionaryItem,String rows,String page,String dictionaryName) throws PlatformException{
		logger.info("****根据字典类型展示字典条目开始***queryDictionaryItem***start***");
		try {
			//齐鲁石化厂家配置
			if(SettingEnums.EQU_FACTORY.equals(dictionaryItem.getDictionaryId())){
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
				model.addAttribute("dictionaryItem", dictionaryItem);
				return "sys/dictionary/listDictionaryItemFactory";
			//齐鲁石化设备类型配置
			}else if(SettingEnums.EQU_TYPE.equals(dictionaryItem.getDictionaryId())){
				
				return "sys/dictionary/listDictionaryEquType";
			}else{
				//设置分页参数
				dictionaryItem.setPage(rows, page);
				//定义字典条目集合
				List<DictionaryItem>dictionaryItemList = null;
				//根据条件查询字典条目
				long totalRows = dictionaryItemService.selectFuzzyCountByObj(dictionaryItem);
				if(0<totalRows){
					//查询字典条目集合
					dictionaryItemList = dictionaryItemService.selectFuzzyListByObj(dictionaryItem);
				}
				//设置分页
				PageBean myPage = PageBean.PageSetParameter(rows,page,totalRows);
				
				model.addAttribute("list",dictionaryItemList);
				model.addAttribute("myPage", myPage);
				model.addAttribute("dictionaryName", dictionaryName);
				model.addAttribute("dictionaryItem", dictionaryItem);
				logger.info("***条件查询字典条目集合结束*****queryDictionaryInfoList***end***");
				return "sys/dictionary/listDictionaryItem";
			}
		} catch (Exception e) {
			logger.error("***条件查询字典条目集合*****queryDictionaryInfoList***异常***");
			e.printStackTrace();
			throw new PlatformException(e.getMessage());
		}
	}
	
}
