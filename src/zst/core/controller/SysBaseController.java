package zst.core.controller;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import zst.core.entity.SysBaseInfo;
import zst.core.service.ISysBaseService;
import zst.extend.enums.LogEnum;
import zst.extend.util.CollectionUtil;
import zst.extend.util.LogUtil;
import zst.extend.util.PageUtil;
import zst.extend.util.PropertiesUtil;

/**
 * 基本信息配置的控制器
 * @author Ablert
 * @date 2017-12-21
 */
@Controller
@RequestMapping("/baseInfo")
public class SysBaseController {

	private static final Log logger = LogFactory.getLog(SysBaseController.class);
	
	@Resource
	private ISysBaseService baseService;
	
	/** logo默认路径 **/
	private static final String LOGO_PATH = "img/";
	/** logo默认名**/
	private static final String LOGO_NAME = "login_logo.png";
	/** ico默认名**/
	private static final String ICO_NAME = "mbec.ico";
	
	private static final String FILE_NAME_KEY = "fileNameKey";//文件名在数据库中的key
	
	private static final String FILE_PATH_KEY = "filePathKey";//文件磁盘路径在数据库中的key
	
	private static final String GROUP = "clienSoftWare";//客户端配置信息标识
	
	//private static final String ABSOLUTE_CLIENT_EXE_PATH_KEY = "startClientExe";
	
	private static final String THIRDPARTYGROUP = "PlatformSwitch";//第三方group
	private static final String UNIVIEWKEY = "UniviewPlatformSdk";//宇视开关
	private static final String RECODERKEY = "RecorderPlatform";//讯对讲开关
	
	/**
	 * 进入系统配置页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/openSystemConfig")
	public String openSystemConfig(Model model) throws Exception {
		String sysName = PropertiesUtil.getPropertiesValue("sysConfig.properties", "sys_name");
		model.addAttribute("sysName", sysName);
		logger.info("进入系统配置页面");
		return "configuration/sysconfig/baseConfig";
	}
	
	/**
	 * 修改logo和名称
	 * @param request
	 * @param response
	 * @param sysName
	 * @param logoImg
	 * @throws Exception
	 */
	@RequestMapping("/uploadLogoAndSysName")
	public String uploadLogoAndSysName(HttpServletRequest request, HttpServletResponse response,String sysName, MultipartFile logoImg) throws Exception {
		try {
			StringBuilder sb = new StringBuilder();
			// 上传图片
			if (logoImg != null && !logoImg.isEmpty()) {
				//获得文件类型
	            String contentType = logoImg.getContentType(); 
	            if(!contentType.contains("image")) {
	            	
	            }
				//获得物理路径webapp所在路径
				String path = request.getServletContext().getRealPath("/") + LOGO_PATH;
				File uploadFile = new File(path);
				if (!uploadFile.exists()) {
					uploadFile.mkdirs();
				}
				
				logoImg.transferTo(new File(path, LOGO_NAME));
				logoImg.transferTo(new File(path, ICO_NAME));
				
				sb.append("上传图片:");
			}
			
			//如果系统名为输入，则不设置
			if(StringUtils.isNotBlank(sysName)) {
				PropertiesUtil.setProperties("sysConfig.properties", "sys_name", sysName);
				sb.append("更新平台名称为"+sysName);
			}
			//写入日志
			LogUtil.writeLog(request, LogEnum.LogLevel.MODIFY, LogEnum.OperatorModule.SYSCONFIG_MODULE, LogEnum.OperatorType.UPDATEOPERATION, sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return "redirect:/baseInfo/openSystemConfig.action";
	}
	
	/**
	 * 进入软件配置页面
	 * @return
	 */
	@RequestMapping("/clientSoftWareManage")
	public String clientSoftWareManage(SysBaseInfo baseInfo, Model model){
		try {
			String filePath = null;
			String fileName = null;
			baseInfo.setGroup(GROUP);
			List<SysBaseInfo> clientInofList = baseService.selectListByObj(baseInfo);
			if(clientInofList != null && clientInofList.size() == 2){
				for(SysBaseInfo base : clientInofList){
					if(FILE_PATH_KEY.equals(base.getName())){
						filePath = base.getValue();
					}
					if(FILE_NAME_KEY.equals(base.getName())){
						fileName = base.getValue();
					}
				}
				model.addAttribute("filePath", filePath);
				model.addAttribute("fileName", fileName);
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return "configuration/sysconfig/clientSoftInfoManage";
	}
	
	/**
	 * 保存客户端软件的文件名称和服务器本地磁盘路径
	 * @param filePath
	 * @param fileName
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/saveClientSoftInfo")
	@ResponseBody
	public void saveClientSoftInfo(SysBaseInfo sysBaseInfo, String filePath, String fileName, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			//处理文件路径
			sysBaseInfo.setName(FILE_PATH_KEY);
			List<SysBaseInfo> filePathEntry = baseService.selectListByObj(sysBaseInfo);
			if(filePathEntry == null || filePathEntry.size() == 0){
				//新增
				if(filePath != null) {
					SysBaseInfo pathVo = new SysBaseInfo();
					pathVo.setName(FILE_PATH_KEY);
					pathVo.setGroup(GROUP);
					pathVo.setValue(filePath);
					baseService.insertSelective(pathVo);
				}
			} else if(filePathEntry.size() == 1) {
				//修改
				SysBaseInfo currentPathVo = filePathEntry.get(0);
				if(currentPathVo!=null){
					currentPathVo.setValue(filePath);
					baseService.updateByPrimaryKeySelective(currentPathVo);
				}
			} else {
				//TODO
				PageUtil.toBeJsonByMap("false","文件路径数据冗余","",response);
			}
			//处理文件名
			sysBaseInfo.setName(FILE_NAME_KEY);
			List<SysBaseInfo> fileNameEntry = baseService.selectListByObj(sysBaseInfo);
			if(fileNameEntry == null || fileNameEntry.size() == 0){
				//新增
				if(fileName != null) {
					SysBaseInfo nameVo = new SysBaseInfo();
					nameVo.setName(FILE_NAME_KEY);
					nameVo.setGroup(GROUP);
					nameVo.setValue(fileName);
					baseService.insertSelective(nameVo);
				}
			} else if(fileNameEntry.size() == 1) {
				//修改
				SysBaseInfo currentNameVo = fileNameEntry.get(0);
				if(currentNameVo!=null){
					currentNameVo.setValue(fileName);
					baseService.updateByPrimaryKeySelective(currentNameVo);
				}
			} else {
				//TODO
				PageUtil.toBeJsonByMap("false","","文件名称冗余",response);
			}
			LogUtil.writeLog(request, LogEnum.LogLevel.MODIFY, LogEnum.OperatorModule.SYSCONFIG_MODULE, LogEnum.OperatorType.UPDATEOPERATION, "更新客户端软件下载地址");
			PageUtil.toBeJsonByMap("true","","",response);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			PageUtil.toBeJsonByMap("false","配置失败，请确认！","",response);
		}
	}
	
	/**
	 * 进入第三方配置页面
	 * @return
	 */
	@RequestMapping("/thirdPartyPlatform")
	public String thirdPartyPlatform(SysBaseInfo baseInfo, Model model){
		try {
			String univiewValue = null;
			String recoderValue = null;
			baseInfo.setGroup(THIRDPARTYGROUP);
			List<SysBaseInfo> clientInofList = baseService.selectListByObj(baseInfo);
			if(clientInofList != null && clientInofList.size() == 2){
				for(SysBaseInfo base : clientInofList){
					if(UNIVIEWKEY.equals(base.getName())){
						univiewValue = base.getValue();
					}
					if(RECODERKEY.equals(base.getName())){
						recoderValue = base.getValue();
					}
				}
				model.addAttribute("uniview", univiewValue);
				model.addAttribute("recoder", recoderValue);
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return "configuration/sysconfig/thirdPartyPlatform";
	}
	
	/**
	 * 保存第三方平台的开关置
	 * @param sysBaseInfo
	 * @param uniview
	 * @param recoder
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/saveThirdPartyPlatform")
	@ResponseBody
	public void saveThirdPartyPlatform(SysBaseInfo sysBaseInfo, String uniview, String recoder, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			//处理宇视开关
			sysBaseInfo.setName(UNIVIEWKEY);
			List<SysBaseInfo> univiewEntry = baseService.selectListByObj(sysBaseInfo);
			if (CollectionUtil.isNotEmpty(univiewEntry) && univiewEntry.size()==1 && uniview != null) {
				SysBaseInfo univiewSys = univiewEntry.get(0);
				univiewSys.setValue(uniview);
				baseService.updateByPrimaryKeySelective(univiewSys);
			}
			//处理讯对讲开关
			sysBaseInfo.setName(RECODERKEY);
			List<SysBaseInfo> recoderEntry = baseService.selectListByObj(sysBaseInfo);
			if (CollectionUtil.isNotEmpty(recoderEntry) && recoderEntry.size()==1 && recoder!=null) {
				SysBaseInfo recoderSys = recoderEntry.get(0);
				recoderSys.setValue(recoder);
				baseService.updateByPrimaryKeySelective(recoderSys);
			}
			LogUtil.writeLog(request, LogEnum.LogLevel.MODIFY, LogEnum.OperatorModule.SYSCONFIG_MODULE, LogEnum.OperatorType.UPDATEOPERATION, "更新客第三方平台开关配置");
			PageUtil.toBeJsonByMap("true","","",response);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			PageUtil.toBeJsonByMap("false","配置失败，请确认！","",response);
		}
	}
	
	/**
	 * 启动客户端程序
	 * @param request
	 * @param response
	 */
	@RequestMapping("/startUpClientExe")
	@ResponseBody
	public void startUpClientExe( HttpServletRequest request, HttpServletResponse response) throws Exception  {
		try {
			Runtime.getRuntime().exec("G:\\clientSoft\\VMC\\VideoMonitoringClient.exe");
			LogUtil.writeLog(request, LogEnum.LogLevel.OPERATE, LogEnum.OperatorModule.LOGIN_MODULE, LogEnum.OperatorType.OPERATERECORD, "启动客户端软件");
			PageUtil.toBeJsonByMap("true","","",response);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			PageUtil.toBeJsonByMap("false","配置失败，请确认！","",response);
		}
	}
}
