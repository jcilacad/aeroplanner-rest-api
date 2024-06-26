package com.projects.aeroplannerrestapi.controller;

import com.projects.aeroplannerrestapi.dto.request.ReservationRequest;
import com.projects.aeroplannerrestapi.dto.response.PaginatedAndSortedReservationResponse;
import com.projects.aeroplannerrestapi.dto.response.ReservationResponse;
import com.projects.aeroplannerrestapi.service.ReservationService;
import com.projects.aeroplannerrestapi.util.AssertionsUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class ReservationControllerTest {

    @InjectMocks
    ReservationController reservationController;

    @Mock
    ReservationService reservationService;

    @Test
    void testConstructor() {
        ReservationController reservationController = new ReservationController(reservationService);
        Assertions.assertInstanceOf(ReservationController.class, reservationController);
    }

    @Test
    void testCreateReservation() {
        ReservationRequest reservationDto = Mockito.mock(ReservationRequest.class);
        ResponseEntity<ReservationResponse> response = reservationController.createReservation(reservationDto);
        AssertionsUtil.assertNullBodyStatusCode(HttpStatus.CREATED, response);
    }

    @Test
    void testGetAllReservations() {
        ResponseEntity<PaginatedAndSortedReservationResponse> response = reservationController.getAllReservations(0, 1, "sort by", "sort dir");
        AssertionsUtil.assertNullBodyStatusCode(HttpStatus.OK, response);
    }

    @Test
    void testGetReservation() {
        ResponseEntity<ReservationResponse> response = reservationController.getReservation(0L);
        AssertionsUtil.assertNullBodyStatusCode(HttpStatus.OK, response);
    }

    @Test
    void testUpdateReservation() {
        ReservationRequest reservationRequest = Mockito.mock(ReservationRequest.class);
        ResponseEntity<ReservationResponse> response = reservationController.updateReservation(0L, reservationRequest);
        AssertionsUtil.assertNullBodyStatusCode(HttpStatus.OK, response);
    }

    @Test
    void testCancelReservation() {
        ResponseEntity<Void> response = reservationController.cancelReservation(0L);
        AssertionsUtil.assertNullBodyStatusCode(HttpStatus.NO_CONTENT, response);
    }

}
