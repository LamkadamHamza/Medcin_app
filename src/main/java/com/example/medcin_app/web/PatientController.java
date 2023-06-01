package com.example.medcin_app.web;

import com.example.medcin_app.entities.Patient;
import com.example.medcin_app.repository.PatientRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
@AllArgsConstructor
public class PatientController {


    PatientRepository patientRepository;

    @GetMapping("/user/index")
    @PreAuthorize("hasRole('USER')")
    public  String index(Model model,
                         @RequestParam(name = "page", defaultValue = "0") int p ,
                         @RequestParam(name = "size", defaultValue = "4") int s ,
                         @RequestParam(name = "keyword", defaultValue ="") String kw){
      Page<Patient> listpatient = patientRepository.findByNomContains(kw,PageRequest.of(p,s));
      model.addAttribute("MaListPatient", listpatient.getContent());
      model.addAttribute("pages",new int[listpatient.getTotalPages()]);
      model.addAttribute("currentPage",p);
      model.addAttribute("keyword",kw);
        return "patients";
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('USER')")
    public  String home(){
        return  "redirect:/user/index";
    }
    @GetMapping("/admin/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public String delete (Long id , String keyword , int page){
        patientRepository.deleteById(id);
        return "redirect:/index?page="+page+"&keyword="+keyword;
    }

    @GetMapping("/admin/formPatients")
    @PreAuthorize("hasRole('ADMIN')")
    public  String formPatients(Model model){
        model.addAttribute("patient", new Patient());
        return "formPatients";
    }


    @PostMapping(path = "/admin/save")
    @PreAuthorize("hasRole('ADMIN')")
    public  String save(Model model , @Valid Patient patient , BindingResult bindingResult){
        if(bindingResult.hasErrors()) return "formPatients";
        patientRepository.save(patient);
        return "redirect:/index?keyword="+patient.getNom();
    }
    @GetMapping (path = "/admin/edit")
    @PreAuthorize("hasRole('ADMIN')")
    public  String editePatient(Model model , Long id){
        Patient patient = patientRepository.findById(id).orElse(null);
        if (patient==null) throw  new RuntimeException("Patient n'existe pas ");
        model.addAttribute("patient",patient);

        return "editePatient";
    }

}
