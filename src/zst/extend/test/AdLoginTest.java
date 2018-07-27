package zst.extend.test;

import java.util.Hashtable;

import javax.naming.AuthenticationException;
import javax.naming.CommunicationException;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.ldap.InitialLdapContext;

public class AdLoginTest {

	private static final String AD_PROTOCAL = "LDAP://";//AD域协议
	private static final String HOST = "10.110.3.11";//齐鲁石化AD域IP，必须填写正确
	private static final String PORT = "389"; //端口，一般默认389
	private static final String SECURITY_AUTHENTICATION = "simple";//LDAP访问安全级别(none,simple,strong),一种模式
	private static final String SUFFIX = "@sinopec.com";
	
	private static String USERNAME = "liwanli.qlsh";//单点登录用户名验证  属于测试账号
	private static String PASSWORD = "qlsh@1234";//单点登录密码验证   属于测试账号
	
	//ldap://10.110.3.11:389/DC=sinopec,DC=ad??one?(objectClass=*)
	public static void main(String[] args) {
		DirContext ctx = null;
		try {
			String url = AD_PROTOCAL + HOST + ":" + PORT;//固定写法
			Hashtable<Object,Object> env = new Hashtable<Object,Object>();//实例化一个Env
			env.put(Context.SECURITY_AUTHENTICATION, SECURITY_AUTHENTICATION);
			env.put(Context.SECURITY_PRINCIPAL, USERNAME+SUFFIX); //用户名
			env.put(Context.SECURITY_CREDENTIALS, PASSWORD);//密码
			env.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");// LDAP工厂类
			env.put(Context.PROVIDER_URL, url);//URL
			ctx = new InitialLdapContext(env, null);
			
		} catch (AuthenticationException e) {
			e.printStackTrace();
		} catch (CommunicationException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(ctx != null) {
				try {
					ctx.close();
				} catch (NamingException e) {
					e.printStackTrace();
				}
				ctx = null;
			}
		}
	}
}
