package com.CRS.Controller;


import java.util.List;

import javax.servlet.ServletContext;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.CRS.Model.LoginModel;
import com.CRS.Model.RegistrationModel;
import com.CRS.Services.ComplaintService;
import com.CRS.Services.PdfFileGenerationService;
import com.CRS.Services.RegistrationService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class AdminController {
	@Autowired 
	public RegistrationService registrationService;
	@Autowired
	private ComplaintService complaintService;
	
	@Autowired
	private PdfFileGenerationService pdfFileGenerationService;
	@Autowired
    private ServletContext servletContext;
	
	@GetMapping("/")
	public String hello() {
		return "hello";
	}
	@PostMapping(path= "/saveCitizen", consumes = MediaType.APPLICATION_JSON_VALUE,produces = "application/json")
	public ResponseEntity<String> saveUser(@RequestBody RegistrationModel registrationModel) {
		
		boolean result=registrationService.saveCitizen(registrationModel);
		if(result) {
			JSONArray passArray= new JSONArray();
			JSONObject passObject=new JSONObject();
			passObject.put("status", 10101);
			passObject.put("message", "Login");
			passArray.put(passObject);
			System.out.println("Ebtwe"+passArray);
			return new ResponseEntity<String>(passArray.toString(),HttpStatus.OK);
		}else
			return new ResponseEntity<String>("",HttpStatus.BAD_REQUEST);
		
	}
	@PostMapping(path= "/login", consumes = MediaType.APPLICATION_JSON_VALUE ,produces = "application/json")
	public ResponseEntity<String> findCitizen(@RequestBody LoginModel loginModel) {
		
		boolean result=registrationService.findCitizen(loginModel);
		if(result) {
			System.out.println("login successfully");
		}
		JSONArray passArray= new JSONArray();
		JSONObject passObject=new JSONObject();
		passObject.put("status", 10101);
		passObject.put("message", "Login");
		passArray.put(passObject);
		System.out.println("Ebtwe"+passArray);
		return new ResponseEntity<String>(passArray.toString(),HttpStatus.OK);
		
	}
	@GetMapping(path= "/allComplaints",produces = "application/json")
	public ResponseEntity<String> allComplaints(){
		JSONArray passArray= new JSONArray();
		List<Object[]> allComplaints= complaintService.getAllComplaints();
		for(Object[] complaint : allComplaints) {
			JSONObject entity = new JSONObject();
			entity.put("gdNo", complaint[0]);
			entity.put("crimeType", complaint[1]);
			entity.put("description", complaint[2]);
			entity.put("city", complaint[3]);
			entity.put("thana", complaint[4]);
			entity.put("location", complaint[5]);
			passArray.put(entity);
		}
		return new ResponseEntity<String>(passArray.toString(),HttpStatus.OK);
	}
	@PostMapping(path= "/changeComplaintStatus/{gd}/{status}")
	public String changeComplaintStatus(@PathVariable("gd") String gdNo,@PathVariable("status") String status){
		int res=complaintService.statusChanged(gdNo, status);
		String s=String.valueOf(res);
		return s;
	}
	@GetMapping(path= "/allComplaintsByParam/{gd}", produces = "application/json")
	public ResponseEntity<String> allComplaintsByParam(@PathVariable("gd") String gdNo){
		System.out.println(gdNo);
		
		JSONArray passArray= new JSONArray();
		List<Object[]> allComplaints= complaintService.getAllComplaintsByGdOrCrimeType(gdNo, "");
		for(Object[] complaint : allComplaints) {
			JSONObject entity = new JSONObject();
			entity.put("gdNo", complaint[0]);
			entity.put("crimeType", complaint[1]);
			entity.put("description", complaint[2]);
			entity.put("city", complaint[3]);
			entity.put("thana", complaint[4]);
			entity.put("location", complaint[5]);
			passArray.put(entity);
		}
		return new ResponseEntity<String>(passArray.toString(),HttpStatus.OK);
	}
	@GetMapping(path= "/downloadPDF/{gd}")
	public ResponseEntity<ByteArrayResource> downloadPDF(@PathVariable("gd") String gdNo){
		System.out.println(gdNo);
		
		//ByteArrayResource resource = new ByteArrayResource(pdfFileGenerationService.POSTRequest(gdNo));
		byte[] pdfDocument = pdfFileGenerationService.POSTRequest(gdNo);
//		return  ResponseEntity.ok()
//				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + gdNo+".pdf")
//	            .contentLength(pdfFileGenerationService.POSTRequest(gdNo).length)
//	            //.contentType(MediaType.parseMediaType("application/octet-stream"))
//	            .contentType(MediaType.APPLICATION_OCTET_STREAM)
//	            .body(resource);
		return ResponseEntity
		        .ok()
		        .contentLength(pdfDocument.length)
		        .body(new ByteArrayResource(pdfDocument));
	}
	
	public ResponseEntity<?> downloadPDF1(@PathVariable("gd") String gdNo){
		System.out.println(gdNo);

		byte[] content = pdfFileGenerationService.POSTRequest(gdNo);
		
		HttpHeaders headers = new HttpHeaders();
		  headers.setContentType(MediaType.parseMediaType("application/pdf"));
		  return   new ResponseEntity<byte[]>(content, headers, HttpStatus.OK);
	}
	@GetMapping(path= "/totalComplaints")
	public long totalComplaintsNo(){
		long complntNo=complaintService.getAllComplaintsNo();
		System.out.println("&&&&"+complntNo);
		
		  return complntNo;
	}
	@GetMapping(path= "/getComplaintStatus", produces = "application/json")
	public ResponseEntity<String> getComplaintStatus(){
		JSONArray passArray= new JSONArray();
		List<Object[]> allComplaints= complaintService.getAllComplaintsStatusNo();
		for(Object[] complaint : allComplaints) {
			JSONObject entity = new JSONObject();
			entity.put("status", complaint[0]);
			entity.put("number", complaint[1]);
			
			passArray.put(entity);
		}
		return new ResponseEntity<String>(passArray.toString(),HttpStatus.OK);
		
	}
	

}
