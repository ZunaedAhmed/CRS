package com.CRS.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="type_of_crime")
public class CrimeTypeModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name= "crime_id")
	private Long id;
	@Column(name= "crime_name")
	private String crimeName;
	public String getCrimeName() {
		return crimeName;
	}
	public void setCrimeName(String crimeName) {
		this.crimeName = crimeName;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
}
