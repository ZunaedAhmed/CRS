package com.CRS.Controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.CRS.Model.ComplaintModel;
import com.CRS.Model.CrimeTypeModel;
import com.CRS.Services.ComplaintService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class CitizenController {
	@Autowired
	private ComplaintService complaintService;
	
	@PostMapping(path= "/saveComplaints", consumes = MediaType.APPLICATION_JSON_VALUE ,produces = "application/json")
	public ResponseEntity<String> saveComplaints(@RequestBody ComplaintModel complaintModel){
		complaintModel.setGdNo(genGdNo());
		complaintModel.setStatus("Waiting for Investigation");;
		boolean isSuccessful=complaintService.saveComplaints(complaintModel);
		if(isSuccessful) {
				JSONArray passArray= new JSONArray();
				JSONObject passObject=new JSONObject();
				passObject.put("mobile", complaintService.getMobileNo(complaintModel.getCitizenName()));
				passObject.put("gdNo",complaintModel.getGdNo() );
				passObject.put("message", "Saved Compalints");
				passArray.put(passObject);
				System.out.println("Ebtwe"+passArray);
				
				return new ResponseEntity<String>(passArray.toString(),HttpStatus.OK);
		}else
			return new ResponseEntity<String>("",HttpStatus.BAD_REQUEST);
	}
	public String genGdNo() {
		int randomGdNo=0;
		String finalNo="";
		 SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
			String gdDate="";
			gdDate=formatter.format(new Date());
			Random randomNumGen= new Random();
			randomGdNo=randomNumGen.nextInt(100);
			finalNo=gdDate+String.valueOf(randomGdNo);
			System.out.println(finalNo);
		return finalNo;
	}
	
	@GetMapping(path= "/getCrimeType",produces = "application/json")
	public ResponseEntity<String> getCrimeType(){
		JSONArray passArray= new JSONArray();
		List<JSONObject> jsonObjectEntities = new ArrayList<>();
		List<CrimeTypeModel> crimeType=new ArrayList<CrimeTypeModel>();
		//crimeType=complaintService.getAllCrimeType();
		for(CrimeTypeModel ctm:crimeType) {
			//System.out.println("____1____"+ctm.getCrimeType());
			JSONObject passObject=new JSONObject();
			//passObject.put("crimeType",ctm.getCrimeType());
			jsonObjectEntities.add(passObject);
		}
		//passObject.put("string", 1);
		passArray.put(jsonObjectEntities);
		System.out.println("#####"+passArray.toString());
		return new ResponseEntity<String>(passArray.toString(),HttpStatus.OK);
	}
//	@GetMapping(path= "/allComplaints",produces = "application/json")
//	public ResponseEntity<String> allComplaints(){
//		JSONArray passArray= new JSONArray();
//		JSONObject finalObject=new JSONObject();
//		List<JSONObject> jsonObjectEntities = new ArrayList<>();
//		List<ComplaintModel> complaints=new ArrayList<ComplaintModel>();
//		complaints=complaintService.getAllComplaint();
//		for(ComplaintModel ctm:complaints) {
//			//System.out.println("____1____"+ctm.getCrimeType());
//			JSONObject passObject=new JSONObject();
//			passObject.put("gdno",ctm.getGdNo());
//			passObject.put("typeOfCrime",ctm.getTypeOfCrime());
//			passObject.put("description",ctm.getComplaint());
//			passObject.put("status",ctm.getStatus());
//			passArray.put(passObject);
//		}
//		
//		//passObject.put("string", 1);
//		//passArray.put(jsonObjectEntities);
//		System.out.println("#121####"+passArray.toString());
//		
//		System.out.println("#####"+passArray.toString());
//		return new ResponseEntity<String>(passArray.toString(),HttpStatus.OK);
//	}
	@GetMapping(path= "/allComplaintsByCitizen/{name}",produces = "application/json")
	public ResponseEntity<String> allComplaintsByCitizen(@PathVariable("name") String name){
		
		JSONArray passArray= new JSONArray();
		List<ComplaintModel> complaints=new ArrayList<ComplaintModel>();
		complaints=complaintService.getComplaintByCitizenName(name);
		for(ComplaintModel ctm:complaints) {
			//System.out.println("____1____"+ctm.getCrimeType());
			JSONObject passObject=new JSONObject();
			passObject.put("gdno",ctm.getGdNo());
			passObject.put("typeOfCrime",ctm.getTypeOfCrime());
			passObject.put("description",ctm.getComplaint());
			passObject.put("status",ctm.getStatus());
			passArray.put(passObject);
		}
//		int a=complaintService.getComplaintByCitizenName(name).size();
//		System.out.println("~~~~"+a);
//		System.out.println("___"+a);
		return new ResponseEntity<String>(passArray.toString(),HttpStatus.OK);

	}
	@GetMapping(path= "/totalComplaintsOfCitizenName/{name}")
	public int totalComplaintsOfCitizenName(@PathVariable("name") String name){
		int complntNo=complaintService.getAllComplaintsNoByCitizenName(name);
		System.out.println("&&&&"+complntNo);
		
		  return complntNo;
	}
	@GetMapping(path= "/getComplaintStatusOfCitizenName/{name}", produces = "application/json")
	public ResponseEntity<String> getComplaintStatus(@PathVariable("name") String name){
		JSONArray passArray= new JSONArray();
		List<Object[]> allComplaints= complaintService.getAllComplaintsStatusNoByCitizenName(name);
		for(Object[] complaint : allComplaints) {
			JSONObject entity = new JSONObject();
			entity.put("status", complaint[0]);
			entity.put("number", complaint[1]);
			
			passArray.put(entity);
		}
		return new ResponseEntity<String>(passArray.toString(),HttpStatus.OK);
		
	}
	
	

}
