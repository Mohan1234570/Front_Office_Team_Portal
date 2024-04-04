package in.ashokit.service;

import java.util.List;

import in.ashokit.binding.DashboardForm;
import in.ashokit.binding.EnquiryForm;
import in.ashokit.binding.EnquirySearchCriteria;
import in.ashokit.entity.StudentEnqEntity;

public interface EnquiryService {
	
	public  List<String> getCourses();
	
	public List<String>  getEnquiryStatus();
	
	public  DashboardForm getDashBoardData(Integer userId);
	
	public boolean saveEnquiry(EnquiryForm enquiryForm);  //upsertEnquiry //saveEnquiry
	
	public List<EnquiryForm> viewEnqieries(Integer userId, EnquirySearchCriteria searchCriteria);
	
	public  EnquiryForm getEnquiry(Integer userId);
	
	public List<StudentEnqEntity> getEnquiries();
	
	public List<StudentEnqEntity> getFilterEnqs(EnquirySearchCriteria criteria,Integer userId);

	
 
}
