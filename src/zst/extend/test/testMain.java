package zst.extend.test;

import java.io.IOException;

import org.junit.Test;

import zst.core.entity.SysUser;


public class testMain {

	public static void main(String[] args) throws IOException {
		 // 创建默认的httpClient实例.    
//        HttpClient client = new HttpClient();
//        HttpMethod method=new GetMethod("zstvmc://videomonitoringclient/login?username=liwanli.qlsh&password=qlsh@1234&from=ad");
//        client.executeMethod(method);
//        method.releaseConnection();
		
//		String url = "zstvmc://videomonitoringclient/login?username=liwanli.qlsh&password=qlsh@1234&from=ad";
//		ProcessBuilder builder = new ProcessBuilder("C:\\Program Files (x86)\\Internet Explorer\\iexplore.exe",url);
//		builder.start();
//		String cmd = "cmd /c start iexplore "+url;
//		Runtime.getRuntime().exec("G:\\clientSoft\\VMC\\VideoMonitoringClient.exe");
		
		String s = "123中国4";
		
		System.out.println(s.length());
	}
	
	public void test(SysUser s){
		System.out.println(s);
		s = new SysUser();
		s.setLoginName("马");
	}
	
	@Test
	public void test1() {
		SysUser user = new SysUser();
		user.setLoginName("李");
		System.out.println(user);;
		test(user);
		System.out.println(user.getLoginName());
	}
}
