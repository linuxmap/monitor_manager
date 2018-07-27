package zst.core.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import zst.core.entity.SysRole;
import zst.core.entity.SysUser;
import zst.core.service.ISysRoleService;
import zst.core.service.ISysUserRoleService;
import zst.core.service.ISysUserService;
import zst.extend.enums.LogEnum;
import zst.extend.util.CollectionUtil;
import zst.extend.util.LogUtil;
import zst.extend.util.PageUtil;

/**
 * 用户角色Controller
 * @author: liuyy
 * @date: 2017-6-13 下午1:59:27
 */
@Controller
@RequestMapping("/userRole")
public class SysUserRoleController {

	@Resource
	ISysUserRoleService sysUserRoleService;
	
	@Resource
	private ISysUserService userService;
	
	@Resource
	private ISysRoleService roleService;
	
	/**
	 * 保存已赋予的角色
	 * @param userId
	 * @param roleIds
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/saveSelectRole")
	public void saveSelectRole(Integer userId,Integer[] roleIds,HttpServletRequest request, HttpServletResponse response) throws Exception{ 
		try {
			SysUser user = userService.selectByPrimaryKey(userId);
			String name = user!=null ? user.getUserName() : null;
			sysUserRoleService.saveSelectRole(userId, roleIds);
			
			String roleName = null;
			if (roleIds!=null && roleIds.length>0) {
				SysRole role = roleService.selectByPrimaryKey(roleIds[0]);
				roleName = role!=null ? role.getRoleName() : null;
			} else {
				roleName = "取消该角色授权";
			}
			LogUtil.writeLog(request, LogEnum.LogLevel.MODIFY, LogEnum.OperatorModule.USER_MODULE, LogEnum.OperatorType.UPDATEOPERATION, "给用户："+name+"分配角色："+roleName);
			PageUtil.toBeJsonByMap("true","操作成功！","",response);
		}catch(Exception e) {
			e.printStackTrace();
			PageUtil.toBeJsonByMap("false","操作失败，请确认！","",response);
		}
		
	}
	
	/**
	 * 批量设置角色
	 * @param userIds
	 * @param roleId
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/saveBatchSelectRole")
	public void saveBatchSelectRole(String userIds,Integer roleId,HttpServletRequest request, HttpServletResponse response) throws Exception{ 
		try {
			SysRole role = roleService.selectByPrimaryKey(roleId);
			String name = role!=null ? role.getRoleName() : null;
			sysUserRoleService.saveBatchSelectRole(userIds, roleId);
			
			StringBuilder userString = new StringBuilder();
			if (userIds!=null && userIds.split(",")!=null && userIds.split(",").length>0) {
				String[] userIdArray = userIds.split(",");
				List<Integer> idList = new ArrayList<Integer>();
				for (int i = 0; i < userIdArray.length; i++) {
			        idList.add(Integer.valueOf(userIdArray[i]));
			    }
				List<SysUser> userList = userService.selectListByIdList(idList);
				if (CollectionUtil.isNotEmpty(userList)) {
					for (int i=0;i<userList.size();i++) {
						userString.append(userList.get(i).getUserName());
						if (i<userList.size()-1) {
							userString.append(",");
						}
					}
				}
			}
			LogUtil.writeLog(request, LogEnum.LogLevel.MODIFY, LogEnum.OperatorModule.USER_MODULE, LogEnum.OperatorType.UPDATEOPERATION, "给用户："+userString.toString()+"分配角色："+name);
			PageUtil.toBeJsonByMap("true","操作成功！","",response);
		}catch(Exception e) {
			e.printStackTrace();
			PageUtil.toBeJsonByMap("false","操作失败，请确认！","",response);
		}
		
	}
}
