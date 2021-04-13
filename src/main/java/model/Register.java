package model;

import java.io.Serializable;

public class Register implements Serializable {
	private static final long serialVersionUID = 1L;
	private String userName;
	private String firstName;
	private String lastName;
	private String pw;
	private String dob;
	private String telNo;
	private String address;
	private String objID;
	
	public Register(String userName, String firstName, String lastName, String pw, String dob, String telNo, String address, String objID) {
		this.userName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.pw = pw;
		this.dob = dob;
		this.telNo = telNo;
		this.address = address;
		this.objID = objID;
	}
	
	public String getUsername(){ return userName; }
	public String getFirstname(){ return firstName; }
	public String getLastname(){ return lastName; }
	public String getPassword(){ return pw; }
	public String getDob(){ return dob; }
	public String getTelNo(){ return telNo; }
	public String getAddress(){ return address; }
	public String getObjID(){ return objID; }
}
