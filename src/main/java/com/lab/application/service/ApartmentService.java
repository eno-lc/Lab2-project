package com.lab.application.service;

import com.lab.application.entity.Apartment;

import java.util.List;

public interface ApartmentService {

    long getApartmentCount();
    long getApartmentsByName(String apartmentName);
    long getOnUseApartments(boolean onUse);
    long findApartmentsByOnUseAndName(boolean onUse, String name);
}
