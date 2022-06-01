package com.CRS.Services;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.CRS.Model.ComplaintModel;
import com.CRS.Repositories.ComplaintsRepository;
import com.CRS.Repositories.CrimeTypeRepositories;
import com.CRS.Repositories.RegistrationRepositories;

@Service
public class ComplaintService {
	@Autowired
	private ComplaintsRepository complaintsRepository;
	@Autowired
	private RegistrationRepositories registrationRepositories;
	@Autowired
	private CrimeTypeRepositories crimeTypeRepositories;
	@Autowired
	private EntityManagerFactory entityManagerFactory;
	
	public boolean saveComplaints(ComplaintModel complaintModel) {
		boolean isSave=false;
		try {
			complaintsRepository.save(complaintModel);
			isSave=true;
			//messageSendService(complaintModel);
		} catch (Exception e) {
			return isSave;
		}
		
		return isSave;
	}
	public String messageSendService1(ComplaintModel complaintModel){
	//public static Map messageSendService(){
		String result ="";
	    final String uri = "http://sms.bulksmsroute.com/smsapi";
	    RestTemplate restTemplate = new RestTemplate();
	    HttpHeaders headers = new HttpHeaders();
	    headers.set("Content-Type", "application/json");
	    //Map newResult = new HashMap();
	    Map<String,String> map = new HashMap<String,String>();
        map.put("api_key", "C20018395b943b09230623.21859741");
        map.put("senderid", "8801847169884");
        map.put("type", "text");
        map.put("scheduledDateTime", "");
        map.put("type", "text");
        map.put("msg", messageGenarator(complaintModel.getGdNo(),complaintModel.getCitizenName()));
        map.put("contacts", getMobileNo(complaintModel.getCitizenName()));
        //Object message=restTemplate.postForObject(uri,map, Map.class);
        result="Successfully send";
        System.out.println("New");
         
		return result;    
	}
	public String messageSendService(ComplaintModel complaintModel){
		JSONObject messageBody=new JSONObject();
		messageBody.put("api_key", "C20018395b943b09230623.21859741");
		messageBody.put("senderid", "8801847169884");
		messageBody.put("type", "text");
		messageBody.put("scheduledDateTime", "");
		messageBody.put("type", "text");
		messageBody.put("msg", messageGenarator(complaintModel.getGdNo(),complaintModel.getCitizenName()));
		messageBody.put("contacts", getMobileNo(complaintModel.getCitizenName()));
		URL obj;
		try {
			obj = new URL("http://localhost:5488/api/report");
			HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
			 postConnection.setRequestMethod("POST");
			 postConnection.setDoOutput(true);
			 OutputStream os = postConnection.getOutputStream();
			 os.write(messageBody.toString().getBytes());
			 os.flush();
			 os.close();
			 int responseCode = postConnection.getResponseCode();
			 System.out.println("POST Response Code :  " + responseCode);
			 System.out.println("POST Response Message : " + postConnection.getResponseMessage());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 //postConnection.setRequestProperty("userId", "a1bcdefgh");
		return null;
		
	}
	public static String messageGenarator(String gdNo,String name) {
//		String gdNo="420";
//		String name="Rony";
		StringBuffer sf=new StringBuffer();
		sf.append("Dear "+name+" Sir/Madam.Your ");
		sf.append("complaint has been registered.we will take the necessary steps as early as possible.");
		sf.append("please preserve your G.D.No."+gdNo+" ");
		sf.append("for further Query.");
		sf .append("Thank you for your cooperation.");
		sf.append("DMP");
		return sf.toString();
	}
public String getMobileNo(String name) {
	String mobile=registrationRepositories.getMobileNo(name);
		return mobile;
	}
//public List<CrimeTypeModel> getAllCrimeType(){
//	
//	return crimeTypeRepositories.findAll();
//}
public List<ComplaintModel> getAllComplaint(){
	System.out.println("Enter");
	return complaintsRepository.findAll();
}
public List<ComplaintModel> getComplaintByCitizenName(String name){
	
	return complaintsRepository.findByCitizenName(name);
}
public List<Object[]> getAllComplaints(){
	EntityManager entityManager = entityManagerFactory.createEntityManager();
	entityManager.getTransaction().begin();
	Query q = entityManager.createNativeQuery("select ci.gd_no,ci.type_of_crime,ci.complaint,czi.district,czi.thana,czi.present_address "
			+ "from complaint_info as ci left join citizen_info as czi on czi.name=ci.citizen_name");
	List<Object[]> allComplaints = q.getResultList();
	entityManager.getTransaction().commit();
	entityManager.close();
	return allComplaints;
}
@Transactional
public int statusChanged(String gdNo,String status) {
	return complaintsRepository.updateStatus(gdNo, status);
}
public List<Object[]> getAllComplaintsByGdOrCrimeType(String gdNo ,String crimeType){
	EntityManager entityManager = entityManagerFactory.createEntityManager();
	entityManager.getTransaction().begin();
	Query q = entityManager.createNativeQuery("select ci.gd_no,ci.type_of_crime,ci.complaint,czi.district,czi.thana,czi.present_address from complaint_info as ci left join citizen_info as czi on czi.name=ci.citizen_name " + 
			"where ci.gd_no= '"+gdNo+"'");
	//or ci.type_of_crime in("+crimeType+")"
	List<Object[]> allComplaints = q.getResultList();
	entityManager.getTransaction().commit();
	entityManager.close();
	return allComplaints;
}
public List<Object[]> getAllComplaintsByGdForDownload(String gdNo ){
	EntityManager entityManager = entityManagerFactory.createEntityManager();
	entityManager.getTransaction().begin();
	Query q = entityManager.createNativeQuery("select ci.gd_no,ci.complaint,czi.thana,czi.present_address,czi.permanent_address,czi.fathers_name,czi.mothers_name,czi.mobile_no,czi.nid"+ 
			"from complaint_info as ci left join citizen_info as czi on czi.name=ci.citizen_name where ci.gd_no= '"+gdNo+"'");
	//or ci.type_of_crime in("+crimeType+")"
	List<Object[]> allComplaints = q.getResultList();
	entityManager.getTransaction().commit();
	entityManager.close();
	return allComplaints;
}
public long getAllComplaintsNo(){
	
	return complaintsRepository.count();
}
public List<Object[]> getAllComplaintsStatusNo(){
	EntityManager entityManager = entityManagerFactory.createEntityManager();
	entityManager.getTransaction().begin();
	String sql=" select ('Waiting for Investigation') as cstatus,count(1)as numb from complaint_info as ci where ci.status='Waiting for Investigation'\r\n" + 
			"union\r\n" + 
			"select ('ChargeSheet')as cstatus,count(1)as numb from complaint_info as ci where ci.status='ChargeSheet'\r\n" + 
			"union\r\n" + 
			"select ('Waiting For Judgement')as cstatus,count(1)as numb from complaint_info as ci where ci.status='Waiting For Judgement'\r\n" + 
			"union\r\n" + 
			"select('Release')as cstatus,count(1) as numb from complaint_info as ci where ci.status='Release'";
	Query q = entityManager.createNativeQuery(sql);
	//or ci.type_of_crime in("+crimeType+")"
	List<Object[]> allComplaints = q.getResultList();
	entityManager.getTransaction().commit();
	entityManager.close();
	return allComplaints;
} 
public int getAllComplaintsNoByCitizenName(String name){
	
	return complaintsRepository.findByCitizenName(name).size();
}
public List<Object[]> getAllComplaintsStatusNoByCitizenName(String name){
	EntityManager entityManager = entityManagerFactory.createEntityManager();
	entityManager.getTransaction().begin();
	String sql="select ('Waiting for Investigation') as cstatus,count(1)as numb from complaint_info as ci where ci.status='Waiting for Investigation' and ci.citizen_name='" +name +"'"+
			"union\r\n" + 
			"select ('ChargeSheet')as cstatus,count(1)as numb from complaint_info as ci where ci.status='ChargeSheet' and ci.citizen_name='" + name +"'"+
			"union\r\n" + 
			"select ('Waiting For Judgement')as cstatus,count(1)as numb from complaint_info as ci where ci.status='Waiting For Judgement' and ci.citizen_name='"+name +"'" +
			"union\r\n" + 
			"select('Release')as cstatus,count(1) as numb from complaint_info as ci where ci.status='Release' and ci.citizen_name='"+name +"'";
	Query q = entityManager.createNativeQuery(sql);
	//or ci.type_of_crime in("+crimeType+")"
	List<Object[]> allComplaints = q.getResultList();
	entityManager.getTransaction().commit();
	entityManager.close();
	return allComplaints;
}
}
