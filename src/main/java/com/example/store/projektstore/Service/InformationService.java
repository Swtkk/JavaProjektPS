package com.example.store.projektstore.Service;

import com.example.store.projektstore.Model.InformationStore;
import com.example.store.projektstore.Repository.InformationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class InformationService {

    @Autowired
    InformationRepository informationRepository;

    public InformationStore saveInformation(InformationStore information) {
        return informationRepository.save(information);
    }

    public Optional<InformationStore> getInformationById(Long informationId) {
        return informationRepository.getInformationById(informationId);
    }

    public InformationStore updateInformation(Long informationId, InformationStore updatedInformation) {
        Optional<InformationStore> updatedInformationStore = informationRepository.getInformationById(informationId);

        if (updatedInformationStore.isPresent()) {
            InformationStore informationToUpdate = updatedInformationStore.get();

            informationToUpdate.setCategory(updatedInformation.getCategory());
            informationToUpdate.setLink(updatedInformation.getLink());
            informationToUpdate.setDescription(updatedInformation.getDescription());
            informationToUpdate.setTitle(updatedInformation.getTitle());
            informationToUpdate.setDateAdded(LocalDate.now());

            informationRepository.save(informationToUpdate);
            return informationToUpdate;
        }
        return null;
    }
}
