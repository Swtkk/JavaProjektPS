package com.example.store.projektstore.Service;

import com.example.store.projektstore.Model.InformationStore;
import com.example.store.projektstore.Repository.InformationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class InformationService {

    private final InformationRepository informationRepository;

    public InformationService(InformationRepository informationRepository) {
        this.informationRepository = informationRepository;
    }


    public Page<InformationStore> findPaginatedByUserLogin(String login, int page, int size, String sortBy, Sort.Direction sortDir, String filterCategory) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDir, sortBy));

        return filterCategory.isEmpty() ? informationRepository.findByUserLogin(login, pageable)
                : informationRepository.findByUserLoginAndCategoryName(login, filterCategory, pageable);
    }

    public Page<InformationStore> findAllOrderByCategoryCount(String login, int page, int size, Sort.Direction sortDir) {
        Pageable pageable = PageRequest.of(page, size);

        return sortDir == Sort.Direction.ASC ? informationRepository.findAllOrderByCategoryCountAsc(login, pageable)
                : informationRepository.findAllOrderByCategoryCountDesc(login, pageable);
    }

    public void deleteInformation(Long informationId) {
        informationRepository.deleteById(informationId);
    }

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

    public List<InformationStore> findReceivedInformations(String login) {
        return informationRepository.findBySenderLogin(login);

    }

    public boolean hasOldInformations(String username) {
        List<InformationStore> informations = informationRepository.findByUserLoginAndDateAddedBefore(username, LocalDate.now().minusDays(3));
        return !informations.isEmpty();
    }
}
