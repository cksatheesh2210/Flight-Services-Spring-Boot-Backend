package com.satheesh.flightservices.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.satheesh.flightservices.entities.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

}
