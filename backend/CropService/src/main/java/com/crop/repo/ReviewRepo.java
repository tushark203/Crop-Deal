package com.crop.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.crop.model.Review;



@Repository
public interface ReviewRepo extends JpaRepository<Review, Integer> {

	@Query(value="SELECT * FROM review r WHERE r.crop_id= :crop_id",nativeQuery = true)
	List<Review> findAllByCropId(int crop_id);

	@Query(value="SELECT * FROM review r WHERE r.dealer_id= :dealer_id",nativeQuery = true)
	List<Review> findByDealerId(int dealer_id);
	
	



}
