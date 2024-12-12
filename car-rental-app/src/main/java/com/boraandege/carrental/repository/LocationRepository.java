package com.boraandege.carrental.repository;

import com.boraandege.carrental.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    Optional<Location> findByCode(String code);


    List<Location> findByNameContaining(String keyword);


    boolean existsByCode(String code);
}
