package com.projects.aeroplannerrestapi.controller;

import com.projects.aeroplannerrestapi.dto.request.PaymentRequest;
import com.projects.aeroplannerrestapi.dto.response.PaymentResponse;
import com.projects.aeroplannerrestapi.entity.Payment;
import com.projects.aeroplannerrestapi.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.projects.aeroplannerrestapi.contstants.PathConstants.*;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
@RequestMapping(API_V1_PAYMENTS)
public class PaymentController {

    private final PaymentService paymentService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping(PAYMENT)
    public ResponseEntity<PaymentResponse> makePayment(@RequestBody @Valid PaymentRequest paymentRequest) {
        return new ResponseEntity<>(paymentService.processPayment(paymentRequest), HttpStatus.CREATED);
    }

    @GetMapping(ID)
    public ResponseEntity<Payment> getPaymentDetails(@PathVariable Long id) {
        return ResponseEntity.ok(paymentService.getPaymentDetails(id));
    }
}
