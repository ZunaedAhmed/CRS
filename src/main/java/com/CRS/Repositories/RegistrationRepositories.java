package com.CRS.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.CRS.Model.RegistrationModel;

public interface RegistrationRepositories extends JpaRepository<RegistrationModel, Long>{
	
	  @Query(value = "select count(*) from citizen_info where name= ?1 and password= ?2 " , nativeQuery = true)
	    public int findCtizen(String name,String pass);
	  @Query(value = "select mobile_no from citizen_info where name= ?1" , nativeQuery = true)
	  public String getMobileNo(String name);
}
