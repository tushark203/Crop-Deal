package com.dealer.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;

import com.dealer.model.Booking;

public interface BookingRepo extends JpaRepository<Booking, Integer> {

	
	List<Booking> findAllByDealerId(int dealer_id);
	
	List<Booking> findAllByDealerIdAndStatus(int dealerId, String status);

}
