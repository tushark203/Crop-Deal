package com.dealer.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dealer.model.Dealer;




@Repository
public interface DealerRepo extends JpaRepository<Dealer, Integer> {
	@Query(value="SELECT email FROM dealer d",nativeQuery = true)
	public List<String> getAllEmails();
	
	Optional<Dealer> findByEmail(String email);

}
