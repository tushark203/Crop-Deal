package com.payment.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.payment.model.Payment;

public interface PaymentRepo extends JpaRepository<Payment, Integer> {

	@Query(value="SELECT * FROM payment p WHERE p.farmer_id= :farmer_id",nativeQuery = true)
	List<Payment> findForFarmer(int farmer_id);

	@Query(value="SELECT * FROM payment p WHERE p.dealer_id= :dealer_id",nativeQuery = true)
	List<Payment> findForDealer(int dealer_id);

}
