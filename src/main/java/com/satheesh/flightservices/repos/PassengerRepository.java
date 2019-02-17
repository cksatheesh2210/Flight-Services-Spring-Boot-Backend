package com.satheesh.flightservices.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.satheesh.flightservices.entities.Passenger;

public interface PassengerRepository extends JpaRepository<Passenger, Integer> {

}
