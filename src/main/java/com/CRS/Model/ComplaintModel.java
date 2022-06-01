package com.CRS.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="complaint_info")
public class ComplaintModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name= "complaint_id")
	private Long id;

	@Column(name= "citizen_name")
	private String citizenName;
	@Column(name= "complaint")
	private String complaint;

	@Column(name= "gd_no")
	private String gdNo;
	@Column(name= "type_of_crime")
	private String typeOfCrime;
	@Column(name= "status")
	private String status;
	
	public String getTypeOfCrime() {
		return typeOfCrime;
	}
	public void setTypeOfCrime(String typeOfCrime) {
		this.typeOfCrime = typeOfCrime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getGdNo() {
		return gdNo;
	}
	public void setGdNo(String gdNo) {
		this.gdNo = gdNo;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCitizenName() {
		return citizenName;
	}
	public void setCitizenName(String citizenName) {
		this.citizenName = citizenName;
	}
	public String getComplaint() {
		return complaint;
	}
	public void setComplaint(String complaint) {
		this.complaint = complaint;
	}
	
	

}
