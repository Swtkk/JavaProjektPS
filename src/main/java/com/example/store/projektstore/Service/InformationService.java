package com.example.store.projektstore.Service;

import com.example.store.projektstore.Model.InformationStore;
import com.example.store.projektstore.Repository.InformationRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InformationService {

    @Autowired
    InformationRepository informationRepository;

    public InformationStore saveInformation(InformationStore information) {
        return informationRepository.save(information);
    }
}
