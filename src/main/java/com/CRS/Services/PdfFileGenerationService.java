package com.CRS.Services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class PdfFileGenerationService {
	ComplaintService cmpService=new ComplaintService();
	public byte[] POSTRequest(String gdNo) {
		
		//cmpService.getAllComplaintsByGdOrCrimeType(gdNo, "");
		//List<Object[]> allComplaints= cmpService.getAllComplaintsByGdOrCrimeType(gdNo, "");
		/*for(Object[] complaint : allComplaints) {
			JSONObject entity = new JSONObject();
			entity.put("gdNo", complaint[0]);
			entity.put("crimeType", complaint[1]);
			entity.put("description", complaint[2]);
			entity.put("city", complaint[3]);
			entity.put("thana", complaint[4]);
			entity.put("location", complaint[5]);
			*/
			
	    JSONObject jsReportShortObject=new JSONObject();
	    jsReportShortObject.put("shortid", "H1gLv-VaVr");//rkJTnK2ce
	    JSONObject jsObject=new JSONObject();
	    jsObject.put("template", jsReportShortObject);
	    jsObject.put("data", reportData(gdNo));
	    
	    System.out.println(jsObject.toString());

	    URL obj;
	    byte[] byteArray=null;
		try {
			obj = new URL("http://localhost:5488/api/report");
			 HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
			 postConnection.setRequestMethod("POST");
			 //postConnection.setRequestProperty("userId", "a1bcdefgh");
			 postConnection.setRequestProperty("Content-Type", "application/json");
			 postConnection.setDoOutput(true);
			 OutputStream os = postConnection.getOutputStream();
			 os.write(jsObject.toString().getBytes());
			 os.flush();
			 os.close();
			 int responseCode = postConnection.getResponseCode();
			 System.out.println("POST Response Code :  " + responseCode);
			 System.out.println("POST Response Message : " + postConnection.getResponseMessage());
			 if (responseCode == HttpURLConnection.HTTP_OK) { //success
			        BufferedReader in = new BufferedReader(new InputStreamReader(
			            postConnection.getInputStream()));
			        byteArray=postConnection.getInputStream().readAllBytes();
			        //String inputLine;
//			        System.out.println("######"+byteArray.length);
			        StringBuffer response = new StringBuffer();
			        String pdfName="D:\\Report\\"+gdNo+".pdf";
			        //FileWriter fstream = new FileWriter("D:\\out.pdf");
			        OutputStream out = new FileOutputStream(pdfName);
			        //BufferedWriter out = new BufferedWriter(fstream);  
			        for (byte b: byteArray) {  
			            out.write(b);  
			        }  
			        out.close();  
			        in .close();
			        // print result
			        System.out.println(response.toString());
//
			        
			    } else {
			        System.out.println("POST NOT WORKED");
			        
			    }
		} catch (IOException e) {
			e.printStackTrace();
		}
		return byteArray;

	}
	public JSONObject reportData(String gdNo) {
		JSONObject jsReportDataAttrObject=new JSONObject();
		List<Object[]> allComplaints= cmpService.getAllComplaintsByGdForDownload(gdNo);
		System.out.println("_____"+jsReportDataAttrObject.toString());
				for(Object[] complaint : allComplaints) {
					//JSONObject jsReportDataAttrObject=new JSONObject();;
					jsReportDataAttrObject.put("gdNo", complaint[0]);
					jsReportDataAttrObject.put("dis", complaint[1]);
					jsReportDataAttrObject.put("thana", complaint[2]);
					jsReportDataAttrObject.put("pres_address", complaint[3]);
					jsReportDataAttrObject.put("perm_address", complaint[4]);
					jsReportDataAttrObject.put("father_name", complaint[5]);
					jsReportDataAttrObject.put("mather_name", complaint[6]);
					jsReportDataAttrObject.put("mobile", complaint[7]);
					jsReportDataAttrObject.put("nid", complaint[8]);
				}
//		JSONObject jsReportDataSellerAttrObject=new JSONObject();
//		jsReportDataSellerAttrObject.put("name", "Rony");
//		jsReportDataSellerAttrObject.put("road", "Kodomtola");
//		jsReportDataSellerAttrObject.put("country", "Bang");
//		JSONObject jsReportDataBuyerAttrObject=new JSONObject();
//		jsReportDataBuyerAttrObject.put("name", "Shuvo");
//		jsReportDataBuyerAttrObject.put("road", "Mirpur");
//		jsReportDataBuyerAttrObject.put("country", "Bang");
//		JSONObject jsReportDataItemsAttrObject=new JSONObject();
//		jsReportDataItemsAttrObject.put("name", "Coding");
//		jsReportDataItemsAttrObject.put("price", 3000);
//		JSONArray jsReportDataItemsAttrArray=new JSONArray();
//		jsReportDataItemsAttrArray.put(jsReportDataItemsAttrObject);
//		jsReportDataAttrObject.put("number", 456);
//		jsReportDataAttrObject.put("seller", jsReportDataSellerAttrObject);
//		jsReportDataAttrObject.put("buyer", jsReportDataBuyerAttrObject);
//		jsReportDataAttrObject.put("items", jsReportDataItemsAttrArray);
		
		System.out.println("_____"+jsReportDataAttrObject.toString());
		return jsReportDataAttrObject;
	}

}
