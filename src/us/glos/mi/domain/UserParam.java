package us.glos.mi.domain;


public class UserParam{
		
	private String userName;
	private String password;
	private String lastUri;
	private boolean isRemembered;
	private boolean isLogout;
	public UserParam()
	{
		userName=null;
		password=null;
		lastUri=null;
		isRemembered=false;
		isLogout=false;
	}
	public UserParam(String uname,String passwd,boolean rem,boolean logout)
	{
		this.userName=uname;
		this.password=passwd;
		this.isRemembered=rem;
		this.isLogout=logout;
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	public boolean isRemembered() {
		return isRemembered;
	}

	public boolean isLogout() {
		return isLogout;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setRemembered(boolean isRemembered) {
		this.isRemembered = isRemembered;
	}
	public void setLogout(boolean isLogout) {
		this.isLogout = isLogout;
	}
	public String getUserParamName()
	{
		return "usr";
	}
	public String getPasswordParamName()
	{
		return "passwd";
	}
	public String getRememberedParamName()
	{
		return "rme";
	}
	public String getLogoutParamName()
	{
		return "logout";
	}
	public String getLastUrlParamName()
	{
		return "url";
	}
	public static String getClassName()
	{
		return UserParam.class.getName();
	}
	public String getLastUri() {
		return lastUri;
	}
	public void setLastUri(String lastUri) {
		this.lastUri = lastUri;
	}
	
}
