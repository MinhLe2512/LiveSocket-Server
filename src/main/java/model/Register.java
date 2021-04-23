package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;


public class Register implements Serializable {
	private static final long serialVersionUID = 1L;
	private String userName;
	private String firstName;
	private String lastName;
	private String pw;
	private LocalDate dob;
	private String telNo;
	private String address;
	private String objID;
	
	public Register(String userName, String pw, String lastName,  String firstName, 
			LocalDate dob, String address, String telNo, String objID) {
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
	public LocalDate getDob(){ return dob; }
	public String getTelNo(){ return telNo; }
	public String getAddress(){ return address; }
	public String getObjID(){ return objID; }
}
