package com.example.store.projektstore.Repository;

import com.example.store.projektstore.Model.InformationStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InformationRepository extends JpaRepository<InformationStore, Long> {
}
