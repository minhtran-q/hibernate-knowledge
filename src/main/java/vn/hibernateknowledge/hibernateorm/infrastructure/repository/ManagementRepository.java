package vn.hibernateknowledge.hibernateorm.infrastructure.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import vn.hibernateknowledge.hibernateorm.infrastructure.entities.ManagementEntity;

public interface ManagementRepository extends JpaRepository<ManagementEntity, Long>{

    @Query("select m from management m where m.managementId = ?1")
    Optional<ManagementEntity> findManagement(long id);
    
}