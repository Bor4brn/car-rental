package com.boraandege.carrental.repository;

import com.boraandege.carrental.model.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Long> {

    Optional<Equipment> findByName(String name);

    boolean existsByName(String name);
}
