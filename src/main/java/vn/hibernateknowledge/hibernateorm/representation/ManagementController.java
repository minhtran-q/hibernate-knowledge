package vn.hibernateknowledge.hibernateorm.representation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.hibernateknowledge.hibernateorm.application.service.ManagementService;

@RestController
public class ManagementController {

    @Autowired
    private ManagementService managementService;
    
    @PostMapping("/management")
    public void saveManagemenet() {
        this.managementService.createManagement();
    }
        
}