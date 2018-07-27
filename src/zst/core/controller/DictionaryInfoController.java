package zst.core.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import zst.core.entity.DictionaryInfo;
import zst.core.service.IDictionaryInfoService;
import zst.extend.exception.PlatformException;
import zst.extend.page.PageBean;
import zst.extend.util.PageUtil;

/**
 * <p>类名称: DictionaryController</p>
 * <p>服务描述: 数据字典相关服务</p>
 * <p>创建人：songxiang</p>
 */
@Controller
@RequestMapping("/dictionaryInfo/*")
public class DictionaryInfoController {
	private static final Log logger = LogFactory.getLog(DictionaryInfoController.class);
	
	@Resource
	private IDictionaryInfoService dictionaryInfoService;
	
	/**打开字典维护页面 **/
	@RequestMapping("openDicInfoManage")
	public String openDicInfoManage(Model model, String funtId) throws Exception {
		model.addAttribute("funtId", funtId);
		return "sys/dictionary/treeDictionary";
	}
	
	/**
	 * 条件查询字典类型集合
	 * @author songxiang
	 * @throws PlatformException
	 * 分页 
	 */
	@RequestMapping("queryDictionaryInfoList")
	public String queryDictionaryInfoList(Model model,DictionaryInfo dictionaryInfo,String rows,String page) throws PlatformException{
		logger.info("***条件查询字典类型集合开始*****queryDictionaryInfoList***start***");
		try {
			//设置分页参数
			dictionaryInfo.setPage(rows, page);
			//定义字典类型集合
			List<DictionaryInfo>dictionaryInfoList = null;
			//查询字典类型总数
			long totalRows = dictionaryInfoService.selectCountByObj(dictionaryInfo);
			logger.info("***字典类型总数********totalRows"+totalRows);
			if(0<totalRows){
				//查询字典类型集合
				dictionaryInfoList = dictionaryInfoService.selectListByObj(dictionaryInfo);
			}
			//设置分页参数
			PageBean myPage = PageBean.PageSetParameter(rows,page,totalRows);
			
			model.addAttribute("list",dictionaryInfoList);
			model.addAttribute("myPage", myPage);
			logger.info("***条件查询字典类型集合结束*****queryDictionaryInfoList***end***");
			return "";
		} catch (Exception e) {
			logger.error("***条件查询字典类型集合*****queryDictionaryInfoList***异常***");
			e.printStackTrace();
			throw new PlatformException(e.getMessage());
		}
	}
	/**
	 * 获取字典类型树
	 * @author songxiang
	 */
	@RequestMapping("queryDictionaryTree")
	public void queryDictionaryTree(HttpServletResponse response) throws Exception {
		logger.info("***条件查询字典类型集合开始*****queryDictionaryTree***start***");
		try {
			//查询字典类型总数
			long totalRows = dictionaryInfoService.selectCountByObj(null);
			logger.info("***字典类型总数********totalRows"+totalRows);
			if(0<totalRows){
				//查询字典类型集合
				 List<DictionaryInfo>dictionaryInfoList = dictionaryInfoService.selectListByObj(null);
				if(null != dictionaryInfoList && dictionaryInfoList.size()>0){
					JSONArray trees = new JSONArray();
					// 遍历list
					for(DictionaryInfo dictionary:dictionaryInfoList){
						JSONObject root = new JSONObject();
						root.put("id", dictionary.getId());
						root.put("name", dictionary.getDictionaryName());
						root.put("code", dictionary.getDictionaryId());
						root.put("open", true);
						trees.add(root);
					}
					logger.info("***条件查询字典类型集合开始*****queryDictionaryTree***end***");
					PageUtil.writeDataToClient(trees,response);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new PlatformException(e.getMessage());
		}
	}
	
}
