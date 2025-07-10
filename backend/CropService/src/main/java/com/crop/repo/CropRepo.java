package com.crop.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.crop.model.Crop;


@Repository
public interface CropRepo extends JpaRepository<Crop, Integer>{
	
	@Query(value="SELECT * FROM crop c WHERE c.farmer_id= :farmer_id",nativeQuery = true)
	public List<Crop> findCropsByFarmerId(int farmer_id);


	@Query(value="SELECT * FROM crop c WHERE c.quantity_available >:d",nativeQuery = true)
	public List<Crop> findCropByQuantityAvailableGreaterThan(double d);

	@Query(value="SELECT * FROM crop c WHERE c.quantity_available =:d",nativeQuery = true)
	public List<Crop> findCropByQuantityAvailableIsZero(double d);

}
