package com.dealer.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dealer.model.BankDetails;

@Repository
public interface BankDetailsRepo extends JpaRepository<BankDetails, Integer> {

}
