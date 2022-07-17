package vn.hibernateknowledge.hibernateorm.representation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.hibernateknowledge.hibernateorm.application.service.ManagementService;
import vn.hibernateknowledge.hibernateorm.application.service.SessionService;

@RestController
public class Controller {

    @Autowired
    private ManagementService managementService;
    
    @Autowired
    private SessionService sessionService;
    
    @PutMapping("/management")
    public void saveManagemenet() {
        this.managementService.createManagement();
    }
    
    @PostMapping("/management")
    public void updateManagemenet() {
        this.managementService.updateManagement();
    } 
    
    @GetMapping("/session")
    public void findSession() {
        this.sessionService.findSessions();
    }
    
    @GetMapping("/notJoin/session")
    public void findSessionWithoutSession() {
        this.sessionService.findSessionsWithoutJoin();
    } 
}