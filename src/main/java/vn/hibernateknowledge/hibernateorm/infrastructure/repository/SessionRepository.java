package vn.hibernateknowledge.hibernateorm.infrastructure.repository;

import org.springframework.data.repository.CrudRepository;

import vn.hibernateknowledge.hibernateorm.infrastructure.entities.SessionEntity;

public interface SessionRepository extends CrudRepository<SessionEntity, Long>{

}