package com.example.controller;

import com.example.models.Company;
import com.example.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CompanyController {

    private final CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("/companies")
    public String listCompanies(Model model) {
        model.addAttribute("companies", companyService.getAllCompanies());
        return "/company/companies";
    }

    @GetMapping("/companies/new")
    public String createCompanyForm(Model model) {
        model.addAttribute("company", new Company());
        return "/company/create_company";
    }

    @PostMapping("/companies")
    public String saveCompany(@ModelAttribute("company") Company company) {
        companyService.addCompany(company);
        return "redirect:/companies";
    }

    @GetMapping("/companies/edit/{id}")
    public String editCompanyForm(@PathVariable Long id, Model model) {
        model.addAttribute("company", companyService.getCompanyById(id));
        return "/company/edit_company";
    }

    @PostMapping("/companies/{id}")
    public String updateCompany(@PathVariable Long id, @ModelAttribute("company") Company company) {
        companyService.updateCompany(company);
        return "redirect:/companies";
    }

    @GetMapping("/companies/{id}")
    public String deleteCompany(@PathVariable Long id) {
        companyService.deleteCompanyById(id);
        return "redirect:/companies";
    }
}