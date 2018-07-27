package zst.extend.test;

import java.rmi.Remote;

public interface HelloService extends Remote {
	String sayHello(String name);
}
