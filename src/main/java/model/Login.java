package model;

import java.io.Serializable;

public class Login implements Serializable{
	private static final long serialVersionUID = 1L;
	private String userName;
	private String pw;
	private String objID;
	
	public Login(String user, String pw, String objID) {
		this.userName = user;
		this.pw = pw;
		this.objID = objID;
	}
	
	public String getU() { return userName; }
	public String getPW() { return pw; }
	public String getObjID() { return objID; }
	
}
