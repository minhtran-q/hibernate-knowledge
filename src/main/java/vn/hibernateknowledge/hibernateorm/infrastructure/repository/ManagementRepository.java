package vn.hibernateknowledge.hibernateorm.infrastructure.repository;

import org.springframework.data.repository.CrudRepository;

import vn.hibernateknowledge.hibernateorm.infrastructure.entities.ManagementEntity;

public interface ManagementRepository extends CrudRepository<ManagementEntity, Long>{

}