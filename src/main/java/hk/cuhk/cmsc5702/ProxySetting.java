package hk.cuhk.cmsc5702;

public class ProxySetting {
	private String login; 
	private String password;
	
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	private String proxy; 
	private int port;
	public String getProxy() {
		return proxy;
	}
	public void setProxy(String proxy) {
		this.proxy = proxy;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	} 
	

}
