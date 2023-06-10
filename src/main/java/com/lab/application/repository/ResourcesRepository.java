package com.lab.application.repository;

import com.lab.application.entity.Resources;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ResourcesRepository extends JpaRepository<Resources, Long> {

}
