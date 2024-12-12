package com.boraandege.carrental.repository;

import com.boraandege.carrental.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {


    Optional<Member> findByEmail(String email);


    Optional<Member> findByPhone(String phone);


    boolean existsByDrivingLicenseNumber(String drivingLicenseNumber);
}
