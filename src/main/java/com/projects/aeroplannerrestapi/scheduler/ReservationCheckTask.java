package com.projects.aeroplannerrestapi.scheduler;

import com.projects.aeroplannerrestapi.entity.Flight;
import com.projects.aeroplannerrestapi.entity.Reservation;
import com.projects.aeroplannerrestapi.enums.ReservationStatusEnum;
import com.projects.aeroplannerrestapi.exception.ResourceNotFoundException;
import com.projects.aeroplannerrestapi.repository.FlightRepository;
import com.projects.aeroplannerrestapi.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Component
@RequiredArgsConstructor
public class ReservationCheckTask {

    private final ReservationRepository reservationRepository;
    private final FlightRepository flightRepository;

    @Transactional
    @Scheduled(cron = "0 * * * * ?")
    public void checkReservations() {
        List<Reservation> reservations = reservationRepository.findByReservationStatus(ReservationStatusEnum.CONFIRMED);
        reservations.stream().forEach(reservation -> {
            String reservationDate = reservation.getReservationDate();
            LocalDateTime givenDateTime = LocalDateTime.parse(reservationDate, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            LocalDateTime twoDaysBefore = givenDateTime.minusDays(2);
            LocalDateTime currentDate = LocalDateTime.now();
            if (currentDate.isAfter(twoDaysBefore) && currentDate.isBefore(givenDateTime)) {
                Long flightId = reservation.getFlightId();
                reservation.setReservationStatus(ReservationStatusEnum.CANCELLED);
                Reservation updatedReservation = reservationRepository.save(reservation);
                Flight flight = flightRepository.findById(flightId)
                        .orElseThrow(() -> new ResourceNotFoundException("Flight", "id", flightId.toString()));
                flight.setCurrentAvailableSeat(flight.getCurrentAvailableSeat() + updatedReservation.getSeatNumber());
                flightRepository.save(flight);
            }
        });
    }
}
