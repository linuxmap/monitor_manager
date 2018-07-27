package zst.extend.util;

import java.util.Hashtable;

import javax.naming.AuthenticationException;
import javax.naming.CommunicationException;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.ldap.InitialLdapContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 用于AD域用户的验证
 * @author Ablert
 * @date 2017-12-26 上午10:58:10
 */
public final class LDAPValidationUtil {
	
	private static final Log logger = LogFactory.getLog(LDAPValidationUtil.class);

	private static final String AD_PROTOCAL = "LDAP://";//AD域协议
	private static final String HOST = "10.110.3.11";//齐鲁石化AD域IP，必须填写正确
	private static final String PORT = "389"; //端口，一般默认389
	private static final String SECURITY_AUTHENTICATION = "simple";//LDAP访问安全级别(none,simple,strong),一种模式
	private static final String SUFFIX = "@sinopec.com";//domain域名
	
//	private static String USERNAME = "liwanli.qlsh";//单点登录用户名验证  属于测试账号
//	private static String PASSWORD = "qlsh@1234";//单点登录密码验证   属于测试账号
	
	/**
	 * AD域验证 通过返回true，不通过返回false
	 * @param username
	 * @param password
	 * @return
	 */
	public static boolean LdapValidation(String username, String password) {
		DirContext ctx = null;
		try {
			String url = AD_PROTOCAL + HOST + ":" + PORT;//固定写法
			Hashtable<Object,Object> env = new Hashtable<Object,Object>();//实例化一个Env
			env.put(Context.SECURITY_AUTHENTICATION, SECURITY_AUTHENTICATION);
			env.put(Context.SECURITY_PRINCIPAL, username+SUFFIX); //用户名
			env.put(Context.SECURITY_CREDENTIALS, password);//密码
			env.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");// LDAP工厂类
			env.put(Context.PROVIDER_URL, url);//URL
			ctx = new InitialLdapContext(env, null);
			if (ctx != null) {//不为null返回
				return true;
			}
		} catch (AuthenticationException e) {
			logger.error(e.getMessage());
		} catch (CommunicationException e) {
			logger.error(e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			if(ctx != null) {
				try {
					ctx.close();
				} catch (NamingException e) {
					logger.error(e.getMessage());
					e.printStackTrace();
				}
				ctx = null;
			}
		}
		return false;
	}
}
