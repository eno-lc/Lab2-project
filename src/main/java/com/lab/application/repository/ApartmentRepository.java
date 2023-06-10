package com.lab.application.repository;

import com.lab.application.entity.Apartment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApartmentRepository extends JpaRepository<Apartment, Long> {

    List<Apartment> findApartmentsByName(String name);
    List<Apartment> findApartmentsByOnUse(boolean onUse);

    List<Apartment> findApartmentsByOnUseAndName(boolean onUse, String name);

}
