package com.marketingapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.marketingapp.entity.Lead;
import com.marketingapp.service.LeadService;
import com.marketingapp.util.EmailService;

@Controller
public class LeadController {

	@Autowired
	private LeadService leadService;
	
	@Autowired
	private EmailService emailService;
	
	//http://localhost:8080/view
	@RequestMapping("/view")
	public String viewLeadPage() {
		return "create_lead"; //page name
		//request dispatcher
	}
	
	//http://localhost:8080/saveLead
	@RequestMapping("/saveLead")
	public String saveOneLead(Lead lead, Model model) {
		leadService.saveLead(lead);
		emailService.sendEmail(lead.getEmail(), "Welcome", "test");
		model.addAttribute("msg", "Lead is saved!!");
		return "create_lead";
	}
	
	//http://localhost:8080/listall
	@RequestMapping("/listall")
	public String getAllLeads(Model model) {
		List<Lead> leads = leadService.getLeads();
		model.addAttribute("leads", leads);
		System.out.println(leads);
		return "list_leads";
	}
	
	
	@RequestMapping("/delete")
	public String deleteLeadById(@RequestParam("id") long id, ModelMap modelMap) {
		leadService.deleteLead(id);
		
		List<Lead> leads = leadService.getLeads();
		modelMap.addAttribute("leads", leads);
		System.out.println(leads);
		return "list_leads";
	}
	
	@RequestMapping("/update")
	public String getLeadById(@RequestParam("id") long id, Model model) {
		Lead lead = leadService.getLeadById(id);
		model.addAttribute("lead", lead);
		return "update_lead";
	}
	
	@RequestMapping("/updateLead")
	public String updateOneLead(Lead lead, Model model) {
		leadService.saveLead(lead);
		
		List<Lead> leads = leadService.getLeads();
		model.addAttribute("leads", leads);
		return "list_leads";
	}
}
