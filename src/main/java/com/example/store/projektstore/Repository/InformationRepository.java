package com.example.store.projektstore.Repository;

import com.example.store.projektstore.Model.InformationStore;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InformationRepository extends JpaRepository<InformationStore, Long> {
    Optional<InformationStore> getInformationById(Long informationId);

    Page<InformationStore> findByUserLogin(String login, Pageable pageable);
    Page<InformationStore> findByUserLoginAndCategoryName(String login, String categoryName, Pageable pageable);

    @Query("SELECT i FROM InformationStore i WHERE i.user.login = :login ORDER BY (SELECT COUNT(ic) FROM InformationStore ic WHERE ic.category = i.category) DESC")
    Page<InformationStore> findAllOrderByCategoryCountDesc(String login, Pageable pageable);

    @Query("SELECT i FROM InformationStore i WHERE i.user.login = :login ORDER BY (SELECT COUNT(ic) FROM InformationStore ic WHERE ic.category = i.category) ASC")
    Page<InformationStore> findAllOrderByCategoryCountAsc(String login, Pageable pageable);

    List<InformationStore> findBySenderLogin(String senderLogin);
}
