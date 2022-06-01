package com.CRS.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="citizen_info")
public class RegistrationModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name= "citizen_id")
	private Long id;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPresentaddress() {
		return presentaddress;
	}
	public void setPresentaddress(String presentaddress) {
		this.presentaddress = presentaddress;
	}
	public String getPremanentaddress() {
		return premanentaddress;
	}
	public void setPremanentaddress(String premanentaddress) {
		this.premanentaddress = premanentaddress;
	}
	@Column(name= "name")
	private String name;
	@Column(name= "bang_name")
	private String bangName;
	@Column(name= "fathers_name")
	private String fathersName;
	
	@Column(name= "mothers_name")
	private String mothersName;
	
	@Column(name= "mobile_no")
	private String mobileNo;
	@Column(name= "email")
	private String email;
	@Column(name= "occupation")
	private String occupation;
	@Column(name= "nid")
	private String nid;
	@Column(name= "gender")
	private String gender;
	@Column(name= "district")
	private String district;
	@Column(name= "thana")
	private String thana;
	@Column(name= "password")
	private String password;
	@Column(name= "present_address")
	private String presentaddress;
	@Column(name= "permanent_address")
	private String premanentaddress;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBangName() {
		return bangName;
	}
	public void setBangName(String bangName) {
		this.bangName = bangName;
	}
	public String getFathersName() {
		return fathersName;
	}
	public void setFathersName(String fathersName) {
		this.fathersName = fathersName;
	}
	public String getMothersName() {
		return mothersName;
	}
	public void setMothersName(String mothersName) {
		this.mothersName = mothersName;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getOccupation() {
		return occupation;
	}
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	public String getNid() {
		return nid;
	}
	public void setNid(String nid) {
		this.nid = nid;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getThana() {
		return thana;
	}
	public void setThana(String thana) {
		this.thana = thana;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPresenttaddress() {
		return presentaddress;
	}
	public void setPresenttaddress(String presentaddress) {
		this.presentaddress = presentaddress;
	}

}
