package com.lab.application.service.impl;
import com.lab.application.entity.Apartment;
import com.lab.application.repository.ApartmentRepository;
import com.lab.application.service.ApartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApartmentServiceImpl implements ApartmentService {

    private final ApartmentRepository apartmentRepository;

    public long getApartmentCount(){
        return apartmentRepository.findAll().size();
    }

   public long getApartmentsByName(String apartmentName){
       List<Apartment> apartmentsByName = apartmentRepository.findApartmentsByName(apartmentName);
       return apartmentsByName.size();
    }

    public long getOnUseApartments(boolean onUse){

        List<Apartment> apartmentsByOnUse = apartmentRepository.findApartmentsByOnUse(onUse);

        return apartmentsByOnUse.size();
    }

    public long findApartmentsByOnUseAndName(boolean onUse, String name){
        return apartmentRepository.findApartmentsByOnUseAndName(onUse, name).size();
    }


}
