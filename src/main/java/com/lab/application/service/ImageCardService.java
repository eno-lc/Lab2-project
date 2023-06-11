package com.lab.application.service;

import com.lab.application.entity.ImageCard;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ImageCardService {
    List<ImageCard> findAll(String filterText);
    List<ImageCard> findAll();
    void deleteImageCard(Long id);
    void saveImageCard(ImageCard imageCard);


}
