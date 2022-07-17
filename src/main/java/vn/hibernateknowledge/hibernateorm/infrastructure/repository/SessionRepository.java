package vn.hibernateknowledge.hibernateorm.infrastructure.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import vn.hibernateknowledge.hibernateorm.infrastructure.entities.SessionEntity;

public interface SessionRepository extends JpaRepository<SessionEntity, Long>{

    @Query("SELECT s FROM session s join fetch s.managementEntity m where m.managementId = ?1")
    List<SessionEntity> findSessionsByManagementId(Long id);
    
}