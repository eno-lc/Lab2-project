package com.lab.application.service.impl;

import com.lab.application.entity.Resources;
import com.lab.application.repository.ResourcesRepository;
import com.lab.application.service.ResourcesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ResourcesServiceImpl implements ResourcesService {

    private final ResourcesRepository resourcesRepository;

    public Long getResourcesValue(){

        Optional<Resources> resource = resourcesRepository.findById(1L);

        if(resource.isPresent()) {
            return resource.get().getValue();
        }
        return 0L;
    }



}
