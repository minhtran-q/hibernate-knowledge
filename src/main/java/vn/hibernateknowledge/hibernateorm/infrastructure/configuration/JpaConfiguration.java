package vn.hibernateknowledge.hibernateorm.infrastructure.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import vn.hibernateknowledge.hibernateorm.infrastructure.repository.DataRepository;
import vn.hibernateknowledge.hibernateorm.infrastructure.repository.ManagementRepository;
import vn.hibernateknowledge.hibernateorm.infrastructure.repository.SessionRepository;

@Configuration
@EnableJpaRepositories(basePackageClasses = {ManagementRepository.class, SessionRepository.class, DataRepository.class})
@EnableTransactionManagement
public class JpaConfiguration {

}