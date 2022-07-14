package vn.hibernateknowledge.hibernateorm.infrastructure.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import vn.hibernateknowledge.hibernateorm.infrastructure.repository.ManagementRepository;

@Configuration
@EnableJpaRepositories(basePackageClasses = {ManagementRepository.class})
@EnableTransactionManagement
public class JpaConfiguration {

}