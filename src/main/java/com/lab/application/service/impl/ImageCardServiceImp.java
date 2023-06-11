package com.lab.application.service.impl;

import com.lab.application.entity.ImageCard;
import com.lab.application.repository.ImageCardRepository;
import com.lab.application.service.ImageCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageCardServiceImp implements ImageCardService {

    private final ImageCardRepository imageCardRepository;


    @Override
    public List<ImageCard> findAll(String filterText) {
        if(filterText == null || filterText.isEmpty()){
            return imageCardRepository.findAll();
        } else {
            return imageCardRepository.search(filterText);
        }
    }

    @Override
    public List<ImageCard> findAll() {
        return imageCardRepository.findAll();
    }

    @Override
    public void deleteImageCard(Long id) {
        imageCardRepository.deleteById(id);
    }

    @Override
    public void saveImageCard(ImageCard imageCard) {
        imageCardRepository.save(imageCard);
    }
}
