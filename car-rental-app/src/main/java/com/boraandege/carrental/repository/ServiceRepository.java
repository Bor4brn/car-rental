package com.boraandege.carrental.repository;

import com.boraandege.carrental.model.AdditionalService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServiceRepository extends JpaRepository<AdditionalService, Long> {

    Optional<AdditionalService> findByName(String name);

    boolean existsByName(String name);
}
