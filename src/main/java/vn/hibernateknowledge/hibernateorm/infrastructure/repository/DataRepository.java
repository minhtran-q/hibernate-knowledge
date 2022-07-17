package vn.hibernateknowledge.hibernateorm.infrastructure.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import vn.hibernateknowledge.hibernateorm.infrastructure.entities.DataEntity;

public interface DataRepository extends CrudRepository<DataEntity, Long>{

    @Query("select d from data d where d.id = ?1")
    Optional<DataEntity> findData(long id);
    
}