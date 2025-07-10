package com.farmer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.farmer.feign.FarmerInterface;
import com.farmer.model.Farmer;
import com.farmer.repo.BankDetailsRepo;
import com.farmer.repo.FarmerRepo;
import com.farmer.service.FarmerService;
import com.farmer.model.BankDetails;
import com.farmer.model.CropDto;

@ExtendWith(MockitoExtension.class)
public class FarmerServiceTest {
	
	    @Mock
		private FarmerRepo farmerRepo;
		
		@Mock
		private BankDetailsRepo bankRepo;
		
		@Mock
		private FarmerInterface farmerInterface;
		
		@InjectMocks
		private FarmerService farmerService;
		
		@BeforeEach
	    void setup() {
	        MockitoAnnotations.openMocks(this);
	    }
		
//		@Test
//	    void testGetAllFarmers() {
//			BankDetails details = new BankDetails(1,"Bank", "123", "IFSC123", "upi@bank", "9999999999");
//	        List<Farmer> farmers = List.of(new Farmer(1,"Suhas","Patil","suhas@gmail.com","morgav","pune","1234","Active","123456789",details));
//	        when(farmerRepo.findAll()).thenReturn(farmers);
//
//	        ResponseEntity<List<Farmer>> response = farmerService.getAllFarmers();
//
//	        assertEquals(HttpStatus.OK, response.getStatusCode());
//	        assertEquals(farmers, response.getBody());
//	    }
//		
		@Test
	    void testDeleteFarmerById_Success() {
	        when(farmerRepo.existsById(1)).thenReturn(true);

	        ResponseEntity<String> response = farmerService.deleteFarmerById(1);

	        assertEquals(HttpStatus.OK, response.getStatusCode());
	        assertEquals("Farmer deleted succesfully", response.getBody());
	        verify(farmerRepo, times(1)).deleteById(1);
	    }
		
		@Test
	    void testDeleteFarmerById_NotFound() {
	        when(farmerRepo.existsById(1)).thenReturn(false);

	        ResponseEntity<String> response = farmerService.deleteFarmerById(1);

	        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	        assertEquals("Farmer with ID 1 does not exist", response.getBody());
	    }
		
		@Test
	    void testRegisterFarmer() {
			BankDetails details = new BankDetails(1,"Bank", "123", "IFSC123", "upi@bank", "9999999999");
	        Farmer farmer = new Farmer(1,"Suhas","Patil","suhas@gmail.com","morgav","pune","1234","Active","123456789",details);
	        when(farmerRepo.save(farmer)).thenReturn(farmer);

	        ResponseEntity<String> response = farmerService.registerFarmer(farmer);

	        assertEquals(HttpStatus.CREATED, response.getStatusCode());
	        assertEquals("farmer registered successfully", response.getBody());
	    }
//		 @Test
//	    void testGetFarmerProfileDetailsById() {
//			 BankDetails details = new BankDetails(1,"Bank", "123", "IFSC123", "upi@bank", "9999999999");
//	        Farmer farmer = new Farmer(1,"Suhas","Patil","suhas@gmail.com","morgav","pune","1234","Active","123456789",details);
//	        Optional<Farmer> optionalFarmer = Optional.of(farmer);
//	        when(farmerRepo.findById(1)).thenReturn(optionalFarmer);
//
//	        ResponseEntity<Optional<Farmer>> response = farmerService.getFarmerProfileDetailsById(1);
//
//	        assertEquals(HttpStatus.OK, response.getStatusCode());
//	        assertEquals(optionalFarmer, response.getBody());
//	    }
		 
//		 @Test
//	    void testEditFarmerProfile_Success() {
//			 BankDetails details = new BankDetails(1,"Bank", "123", "IFSC123", "upi@bank", "9999999999");
//	        Farmer farmer = new Farmer(1,"Suhas","Patil","suhas@gmail.com","morgav","pune","1234","Active","123456789",details);
//	        when(farmerRepo.existsById(1)).thenReturn(true);
//
//	        ResponseEntity<String> response = farmerService.editFarmerProfile(1, farmer);
//
//	        assertEquals(HttpStatus.OK, response.getStatusCode());
//	        assertEquals("farmer profile updated successfully", response.getBody());
//	    }
//		 
//		 @Test
//	    void testEditFarmerProfile_NotFound() {
//			 BankDetails details = new BankDetails(1,"Bank", "123", "IFSC123", "upi@bank", "9999999999");
//	        Farmer farmer = new Farmer(1,"Suhas","Patil","suhas@gmail.com","morgav","pune","1234","Active","123456789",details);
//	        when(farmerRepo.existsById(1)).thenReturn(false);
//
//	        ResponseEntity<String> response = farmerService.editFarmerProfile(1, farmer);
//
//	        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//	        assertEquals("farmer not found", response.getBody());
//	    }
		 
		 @Test
	    void testAddBankDetails_Success() {
			 BankDetails details = new BankDetails(1,"Bank", "123", "IFSC123", "upi@bank", "9999999999");
		     Farmer farmer = new Farmer(1,"Suhas","Patil","suhas@gmail.com","morgav","pune","1234","Active","123456789",details);
	        BankDetails bankDetails = new BankDetails();
	        Optional<Farmer> optionalFarmer = Optional.of(farmer);

	        when(farmerRepo.findById(1)).thenReturn(optionalFarmer);
	        when(bankRepo.save(bankDetails)).thenReturn(bankDetails);

	        ResponseEntity<String> response = farmerService.addBankDetails(1, bankDetails);

	        assertEquals(HttpStatus.OK, response.getStatusCode());
	        assertEquals("Bank details added successfully", response.getBody());
	    }
		 
		@Test
	    void testAddBankDetails_FarmerNotFound() {
	        when(farmerRepo.findById(1)).thenReturn(Optional.empty());

	        ResponseEntity<String> response = farmerService.addBankDetails(1, new BankDetails());

	        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	        assertEquals("Farmer not found", response.getBody());
	    }
		
		@Test
	    void testUpdateBankDetails_Success() {
			 BankDetails existingDetails = new BankDetails(1,"Bank", "123", "IFSC123", "upi@bank", "9999999999");
		        Farmer farmer = new Farmer(1,"Suhas","Patil","suhas@gmail.com","morgav","pune","1234","Active","123456789",existingDetails);
	       
	        farmer.setBankDetails(existingDetails);

	        BankDetails newDetails = new BankDetails(1,"Bank", "123", "IFSC12355", "upi@bank", "9999999999");

	        when(farmerRepo.findById(1)).thenReturn(Optional.of(farmer));
	        when(bankRepo.save(existingDetails)).thenReturn(existingDetails);

	        ResponseEntity<String> response = farmerService.updateBankDetails(1, newDetails);

	        assertEquals(HttpStatus.OK, response.getStatusCode());
	        assertEquals("Bank details updated successfully", response.getBody());
	    }
		
		@Test
	    void testUpdateBankDetails_NoExisting() {
	        Farmer farmer = new Farmer(); // no bank details
	        when(farmerRepo.findById(1)).thenReturn(Optional.of(farmer));

	        ResponseEntity<String> response = farmerService.updateBankDetails(1, new BankDetails());

	        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	        assertEquals("No existing bank details found.please add bank details", response.getBody());
	    }
		
		@Test
	    void testGetBankDetails_Success() {
	        BankDetails bankDetails = new BankDetails();
	        Farmer farmer = new Farmer();
	        farmer.setBankDetails(bankDetails);
	        when(farmerRepo.existsById(1)).thenReturn(true);
	        when(farmerRepo.findById(1)).thenReturn(Optional.of(farmer));

	        ResponseEntity<BankDetails> response = farmerService.getBankDetails(1);

	        assertEquals(HttpStatus.OK, response.getStatusCode());
	        assertEquals(bankDetails, response.getBody());
	    }
		
		@Test
	    void testGetBankDetails_FarmerNotFound() {
	        when(farmerRepo.existsById(1)).thenReturn(false);

	        ResponseEntity<BankDetails> response = farmerService.getBankDetails(1);

	        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	        assertNull(response.getBody());
	    }
		
		@Test
	    void testPublishCrop() {
	        CropDto cropDto = new CropDto();
	        ResponseEntity<String> responseEntity = new ResponseEntity<>("Published", HttpStatus.OK);
	        when(farmerInterface.publishCrop(any())).thenReturn(responseEntity);

	        ResponseEntity<String> result = farmerService.publishCrop(1, cropDto);

	        assertEquals(HttpStatus.OK, result.getStatusCode());
	        assertEquals("Published", result.getBody());
	    }
		
		@Test
	    void testEditCropByFarmer() {
	        CropDto cropDto = new CropDto();
	        ResponseEntity<String> response = new ResponseEntity<>("Edited", HttpStatus.OK);
	        when(farmerInterface.editCrop(eq(1), any())).thenReturn(response);

	        ResponseEntity<String> result = farmerService.editCropByFarmer(1, 1, cropDto);

	        assertEquals("Edited", result.getBody());
	    }
		
		 @Test
	    void testDeleteCropByFarmer() {
	        ResponseEntity<String> response = new ResponseEntity<>("Deleted", HttpStatus.OK);
	        when(farmerInterface.deleteCrop(1)).thenReturn(response);

	        ResponseEntity<String> result = farmerService.deleteCropByFarmer(1, 1);

	        assertEquals("Deleted", result.getBody());
	    }
		 
		 @Test
	    void testGetAllCrops() {
	        List<CropDto> crops = List.of(new CropDto());
	        when(farmerInterface.getAllCrops()).thenReturn(ResponseEntity.ok(crops));

	        ResponseEntity<List<CropDto>> response = farmerService.getAllCrops();

	        assertEquals(crops, response.getBody());
	    }
		 
//		 @Test
//	    void testGetMyCrops() {
//	        List<CropDto> crops = List.of(new CropDto());
//	        when(farmerInterface.getListOfCropsByFarmerId(1)).thenReturn(ResponseEntity.ok(crops));
//
//	        ResponseEntity<List<CropDto>> response = farmerService.getMyCrops(1);
//
//	        assertEquals(crops, response.getBody());
//	    }
		 
		 @Test
	    void testGetCropStatus() {
	        ResponseEntity<String> status = new ResponseEntity<>("Available", HttpStatus.OK);
	        when(farmerInterface.CropStatus(1)).thenReturn(status);

	        ResponseEntity<String> response = farmerService.getCropStatus(1);

	        assertEquals("Available", response.getBody());
	    }


}
