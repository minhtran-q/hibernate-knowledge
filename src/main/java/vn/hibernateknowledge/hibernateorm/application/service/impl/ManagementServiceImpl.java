package vn.hibernateknowledge.hibernateorm.application.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.hibernateknowledge.hibernateorm.application.service.ManagementService;
import vn.hibernateknowledge.hibernateorm.infrastructure.entities.DataEntity;
import vn.hibernateknowledge.hibernateorm.infrastructure.entities.ManagementEntity;
import vn.hibernateknowledge.hibernateorm.infrastructure.entities.SessionEntity;
import vn.hibernateknowledge.hibernateorm.infrastructure.repository.ManagementRepository;
import vn.hibernateknowledge.hibernateorm.infrastructure.repository.SessionRepository;

@Service("managementService")
@Transactional
public class ManagementServiceImpl implements ManagementService {

    @Autowired
    private ManagementRepository managementRepository;
    
    @Autowired
    private SessionRepository sessionRepository;

    @Override
    @Transactional(rollbackFor = { RuntimeException.class })
    public void createManagement() {

        final ManagementEntity managementEntity = new ManagementEntity();

        final DataEntity dataEntity = new DataEntity();

        managementEntity.setStartTime(System.currentTimeMillis());
        managementEntity.setEndTime(System.currentTimeMillis() + 1000);
        managementEntity.setStatus("DONE");
        managementEntity.setDataEntity(dataEntity);
        
        this.managementRepository.save(managementEntity);
        
        List<SessionEntity> sessionEntities = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            SessionEntity sessionEntity = new SessionEntity();
            sessionEntity.setManagementEntity(managementEntity);
            sessionEntities.add(sessionEntity);
        }
        this.sessionRepository.saveAll(sessionEntities);
        
        System.out.println("Create management");
        System.out.println("Done");
    }

    @Override
    public void updateManagement() {

    }
}