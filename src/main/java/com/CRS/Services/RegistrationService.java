package com.CRS.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.CRS.Model.LoginModel;
import com.CRS.Model.RegistrationModel;
import com.CRS.Repositories.RegistrationRepositories;

@Service
public class RegistrationService {
	@Autowired
	private RegistrationRepositories registrationRepositories;
	
	public boolean saveCitizen(RegistrationModel registrationModel) {
		
		try {
			registrationRepositories.save(registrationModel);
			return true;
		} catch(Exception ex){
			return false;
		}
		
	}
public boolean findCitizen(LoginModel loginModel) {
		
		try {
			registrationRepositories.findCtizen(loginModel.getName(), loginModel.getPassWord());
			return true;
		} catch(Exception ex){
			return false;
		}
		
	}

}
