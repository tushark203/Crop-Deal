package com.dealer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

import com.dealer.feign.DealerInterface;
import com.dealer.model.BankDetails;
import com.dealer.model.Booking;
import com.dealer.model.Dealer;
import com.dealer.repo.BankDetailsRepo;
import com.dealer.repo.BookingRepo;
import com.dealer.repo.DealerRepo;
import com.dealer.service.DealerService;

@ExtendWith(MockitoExtension.class)
public class DealerServiceTest {
	
	@Mock
    private DealerRepo dealerRepo;

    @Mock
    private BankDetailsRepo bankRepo;

    @Mock
    private BookingRepo bookingRepo;

    @Mock
    private DealerInterface dealerInterface;

    @InjectMocks
    private DealerService dealerService;
    
    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllDealer() {
        List<Dealer> dealers = List.of(new Dealer());
        when(dealerRepo.findAll()).thenReturn(dealers);

        ResponseEntity<List<Dealer>> response = dealerService.getAllDealer();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dealers, response.getBody());
    }
    
    @Test
    void testDeleteDealerById_Success() {
        when(dealerRepo.existsById(1)).thenReturn(true);

        ResponseEntity<String> response = dealerService.deleteDealerById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Dealer deleted succesfully", response.getBody());
        verify(dealerRepo, times(1)).deleteById(1);
    }

    @Test
    void testDeleteDealerById_NotFound() {
        when(dealerRepo.existsById(1)).thenReturn(false);

        ResponseEntity<String> response = dealerService.deleteDealerById(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Dealer with ID 1 does not exist", response.getBody());
    }

    @Test
    void testRegisterDealer_Success() {
        Dealer dealer = new Dealer();
        when(dealerRepo.save(dealer)).thenReturn(dealer);

        ResponseEntity<String> response = dealerService.registerDealer(dealer);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Dealer registered successfully", response.getBody());
    }

    @Test
    void testRegisterDealer_Failure() {
        when(dealerRepo.save(any())).thenReturn(null);

        ResponseEntity<String> response = dealerService.registerDealer(new Dealer());

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Dealer not registered successfully", response.getBody());
    }

    @Test
    void testGetDealerProfileDetailsById() {
        Dealer dealer = new Dealer();
        when(dealerRepo.findById(1)).thenReturn(Optional.of(dealer));

        ResponseEntity<Optional<Dealer>> response = dealerService.getDealerProfileDetailsById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isPresent());
    }

    @Test
    void testEditDealerProfile_Success() {
        Dealer dealer = new Dealer();
        when(dealerRepo.existsById(1)).thenReturn(true);

        ResponseEntity<String> response = dealerService.editDealerProfile(1, dealer);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Dealer profile updated successfully", response.getBody());
    }

    @Test
    void testEditDealerProfile_NotFound() {
        when(dealerRepo.existsById(1)).thenReturn(false);

        ResponseEntity<String> response = dealerService.editDealerProfile(1, new Dealer());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Dealer not found", response.getBody());
    }

    @Test
    void testAddBankDetails_Success() {
        Dealer dealer = new Dealer();
        when(dealerRepo.findById(1)).thenReturn(Optional.of(dealer));
        when(bankRepo.save(any())).thenReturn(new BankDetails());

        ResponseEntity<String> response = dealerService.addBankDetails(1, new BankDetails());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Bank details added successfully", response.getBody());
    }

    @Test
    void testAddBankDetails_DealerNotFound() {
        when(dealerRepo.findById(1)).thenReturn(Optional.empty());

        ResponseEntity<String> response = dealerService.addBankDetails(1, new BankDetails());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Dealer not found", response.getBody());
    }

    @Test
    void testUpdateBankDetails_Success() {
        BankDetails existingDetails = new BankDetails();
        Dealer dealer = new Dealer();
        dealer.setBankDetails(existingDetails);

        BankDetails newDetails = new BankDetails(1,"New Bank", "12345", "IFSC001", "upi@bank", "8888888888");

        when(dealerRepo.findById(1)).thenReturn(Optional.of(dealer));
        when(bankRepo.save(any())).thenReturn(existingDetails);

        ResponseEntity<String> response = dealerService.updateBankDetails(1, newDetails);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Bank details updated successfully", response.getBody());
    }

    @Test
    void testUpdateBankDetails_NoExisting() {
        Dealer dealer = new Dealer(); // no bank details
        when(dealerRepo.findById(1)).thenReturn(Optional.of(dealer));

        ResponseEntity<String> response = dealerService.updateBankDetails(1, new BankDetails());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("No existing bank details found.please add bank details", response.getBody());
    }

    @Test
    void testGetBankDetails_Success() {
        Dealer dealer = new Dealer();
        dealer.setBankDetails(new BankDetails());
        when(dealerRepo.existsById(1)).thenReturn(true);
        when(dealerRepo.findById(1)).thenReturn(Optional.of(dealer));

        ResponseEntity<BankDetails> response = dealerService.getBankDetails(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testGetBankDetails_NotFound() {
        when(dealerRepo.existsById(1)).thenReturn(false);

        ResponseEntity<BankDetails> response = dealerService.getBankDetails(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

//    @Test
//    void testGetAllPublishedCrops() {
//        List<CropDto> crops = List.of(new CropDto());
//        when(dealerInterface.getAllCrops()).thenReturn(ResponseEntity.ok(crops));
//
//        ResponseEntity<List<CropDto>> response = dealerService.getAllPubishedCrops();
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(crops, response.getBody());
//    }

    @Test
    void testBookPublishedCrop() {
        ResponseEntity<String> bookingResponse = new ResponseEntity<>("Booked", HttpStatus.OK);
        when(dealerInterface.bookCrop(eq(1), eq(2), eq(5.0))).thenReturn(bookingResponse);

        ResponseEntity<String> response = dealerService.bookPubishedCrop(1, 2, 5.0);

        assertEquals("Booked", response.getBody());
        verify(bookingRepo, times(1)).save(any(Booking.class));
    }

    @Test
    void testCancelBooking() {
        ResponseEntity<String> cancelResponse = new ResponseEntity<>("Cancelled", HttpStatus.OK);
        when(dealerInterface.cancelCropBooking(eq(1), eq(2), eq(5.0))).thenReturn(cancelResponse);

        ResponseEntity<String> response = dealerService.cancelBooking(1, 2, 5.0);

        assertEquals("Cancelled", response.getBody());
        verify(bookingRepo, times(1)).save(any(Booking.class));
    }

//    @Test
//    void testGetMyBookedCrops() {
//        List<CropDto> crops = List.of(new CropDto());
//        when(dealerInterface.getListOfCropsByDealerId(1)).thenReturn(ResponseEntity.ok(crops));
//
//        ResponseEntity<List<CropDto>> response = dealerService.getMyBookedCrops(1);
//
//        assertEquals(crops, response.getBody());
//    }
}
	


