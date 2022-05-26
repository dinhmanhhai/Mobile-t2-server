package com.example.mobileserver;

import com.example.mobileserver.controller.MobileController;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@Slf4j
@EnableJpaAuditing
//@ComponentScan(basePackageClasses = MobileController.class, MobileServicesImpl.class)
@ConfigurationPropertiesScan
@EntityScan(basePackages = {"com.example.mobileserver.entities"})
@EnableJpaRepositories(basePackages = {"com.example.mobileserver.repositories"})
public class MobileServerApplication {

  private static final Logger logger = LoggerFactory.getLogger(MobileServerApplication.class);

  public static void main(String[] args) {
    SpringApplication.run(MobileServerApplication.class, args);
  }

}
