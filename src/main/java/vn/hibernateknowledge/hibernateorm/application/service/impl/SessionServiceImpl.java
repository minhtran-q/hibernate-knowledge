package vn.hibernateknowledge.hibernateorm.application.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.hibernateknowledge.hibernateorm.application.service.SessionService;
import vn.hibernateknowledge.hibernateorm.infrastructure.entities.SessionEntity;
import vn.hibernateknowledge.hibernateorm.infrastructure.repository.SessionRepository;

@Service
public class SessionServiceImpl implements SessionService {

    @Autowired
    private SessionRepository sessionRepository;

    @Override
    @Transactional
    public void findSessions() {

        final List<SessionEntity> sessionEntities = sessionRepository.findSessionsByManagementId(1L);
        System.out.println(sessionEntities.size());
    }
    
    @Override
    @Transactional
    public void findSessionsWithoutJoin() {
        // first query
        Optional<SessionEntity> optional = sessionRepository.findById(1L);
        SessionEntity sessionEntity = optional.orElseThrow();
        
        // second query
        sessionEntity.getManagementEntity().getStatus();
    }
}