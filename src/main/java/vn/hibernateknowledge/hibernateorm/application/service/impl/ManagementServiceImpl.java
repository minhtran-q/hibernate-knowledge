package vn.hibernateknowledge.hibernateorm.application.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.hibernateknowledge.hibernateorm.application.service.ManagementService;
import vn.hibernateknowledge.hibernateorm.infrastructure.entities.DataEntity;
import vn.hibernateknowledge.hibernateorm.infrastructure.entities.ManagementEntity;
import vn.hibernateknowledge.hibernateorm.infrastructure.repository.DataRepository;
import vn.hibernateknowledge.hibernateorm.infrastructure.repository.ManagementRepository;

@Service("managementService")
@Transactional
public class ManagementServiceImpl implements ManagementService {

    @Autowired
    private ManagementRepository managementRepository;

    @Autowired
    private DataRepository dataRepository;
    
    @Override
    @Transactional
    public void createManagement() {

        final ManagementEntity managementEntity = new ManagementEntity();
        
        final DataEntity dataEntity = new DataEntity();

        managementEntity.setStartTime(System.currentTimeMillis());
        managementEntity.setEndTime(System.currentTimeMillis() + 1000);
        managementEntity.setStatus("DONE");
        managementEntity.setDataEntity(dataEntity);
        
        this.managementRepository.save(managementEntity);
        
        System.out.println("Create management.");
    }

    @Override
    public void updateManagement() {

    }
}