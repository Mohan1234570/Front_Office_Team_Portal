package in.ashokit.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.ashokit.binding.DashboardForm;
import in.ashokit.binding.EnquiryForm;
import in.ashokit.binding.EnquirySearchCriteria;
import in.ashokit.entity.StudentEnqEntity;
import in.ashokit.repo.StudentEnqRepository;
import in.ashokit.service.EnquiryService;
import jakarta.servlet.http.HttpSession;

@Controller
public class EnquiryController {

	@Autowired
	private HttpSession session;

	@Autowired
	private EnquiryService enquiryService;
	
	@Autowired
	private StudentEnqRepository stuEnqRepository;

	@GetMapping("/logout")
	public String logout() {
		session.invalidate();
		return "index";
	}

	@GetMapping("/dashboard")
	public String dashBoardpage(Model model) {
		Integer userId = (Integer)session.getAttribute("userId");

		DashboardForm dashboardData = enquiryService.getDashBoardData(userId);

		model.addAttribute("dashboardData", dashboardData);

		return "dashboard";
	}

	@GetMapping("/enquiry")
	public String addEnquiryPage(Model model) {
		initForm(model);
		return "add-enquiry";
	}

	@PostMapping("/addEnq")
	public String addEnquiry(@ModelAttribute("formObj") EnquiryForm formObj, Model model) {
		System.out.println(formObj);

		boolean status = enquiryService.saveEnquiry(formObj);

		if (status) {
			model.addAttribute("succMsg", "Enquiry Added");
		} else {
			model.addAttribute("errMsg", "Problem Occured while Adding");
		}

		return "add-enquiry";
	}

	private void initForm(Model model) {
		//get courses for drop down
		List<String> courses=	enquiryService.getCourses();
		//get status for drop down
		List<String> enqStatuses=	enquiryService.getEnquiryStatus();

		//create binding class object
		EnquiryForm formObj = new EnquiryForm();

		//set data in model object
		model.addAttribute("courseNames",courses );
		model.addAttribute("statusNames", enqStatuses);
		model.addAttribute("formObj", formObj);

	}

	@GetMapping("/enquires")
	public String viewEnquiryPage(Model model) {
		initForm(model);
		model.addAttribute("searchForm", new EnquirySearchCriteria());
		List<StudentEnqEntity> enquiries = enquiryService.getEnquiries();
		model.addAttribute("enquiries", enquiries);
		return "view-enquiries";
	}
	
	@GetMapping("/filter-enquiries")
	public String getFilteredEnquiries(@RequestParam String cname,
			@RequestParam String mode, @RequestParam String status, Model model) {
		
		EnquirySearchCriteria criteria = new EnquirySearchCriteria();
		criteria.setCourseName(cname);
		criteria.setClassMode(mode);
		criteria.setEnqStatus(status);
		
		Integer userId = (Integer) session.getAttribute("userId");
		List<StudentEnqEntity> filteredEnqs= enquiryService.getFilterEnqs(criteria, userId);

		model.addAttribute("enquiries", filteredEnqs);
		
		return "filter-enquiries";
	}
	
	@GetMapping("/edit")
	public String edit(@RequestParam("id") Integer id, Model model) {
		initForm(model);
		Optional<StudentEnqEntity> findById= stuEnqRepository.findById(id);
		if (findById.isPresent()) {
			StudentEnqEntity entity = findById.get();
			model.addAttribute("formObj", entity);
		}
		//enquiryService.ge
		return "add-enquiry";
	}
}
