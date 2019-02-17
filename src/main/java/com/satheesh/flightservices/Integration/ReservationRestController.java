package com.satheesh.flightservices.Integration;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.satheesh.flightservices.Integration.dto.CreateReservationRequest;
import com.satheesh.flightservices.Integration.dto.UpdateReservationRequest;
import com.satheesh.flightservices.entities.Flight;
import com.satheesh.flightservices.entities.Passenger;
import com.satheesh.flightservices.entities.Reservation;
import com.satheesh.flightservices.repos.FlightRepository;
import com.satheesh.flightservices.repos.PassengerRepository;
import com.satheesh.flightservices.repos.ReservationRepository;

@RestController
public class ReservationRestController {

	@Autowired
	FlightRepository flightRepository;
	@Autowired
	ReservationRepository reservationRepository;
	@Autowired
	PassengerRepository passengerRepository;

	@RequestMapping(value="/flights", method = RequestMethod.GET)
	public List<Flight> findFlights(@RequestParam("from") String from, @RequestParam("to") String to, 
			@RequestParam("departureDate") @DateTimeFormat(pattern="MM-DD-YYYY") Date departureDate) {
		return flightRepository.findFlights(from, to, departureDate);

	}
	
	@RequestMapping(value = "/flights/{id}", method = RequestMethod.GET)
	public Flight findFlights(@PathVariable("id") int id) {
		return flightRepository.findById(id).get();

	}

	@RequestMapping(value = "/reservations", method = RequestMethod.POST)
	@Transactional
	public Reservation saveReservation(CreateReservationRequest request) {

		Flight flight = flightRepository.findById(request.getFlightId()).get();

		Passenger passenger = new Passenger();
		passenger.setFirstName(request.getPassengerFirstName());
		passenger.setLastName(request.getPassengerLastName());
		passenger.setMiddleName(request.getPassengerMiddleName());
		passenger.setEmail(request.getPassengerEmail());
		passenger.setPhone(request.getPassengerPhone());

		Passenger savedPassenger = passengerRepository.save(passenger);

		Reservation reservation = new Reservation();
		reservation.setFlight(flight);
		reservation.setPassenger(savedPassenger);
		reservation.setCheckedIn(false);

		return reservationRepository.save(reservation);

	}

	@RequestMapping(value = "/reservations/{id}", method = RequestMethod.GET)
	public Reservation findReservation(@PathVariable("id") int id) {
		return reservationRepository.findById(id).get();

	}

	@RequestMapping(value = "/reservations", method = RequestMethod.PUT)
	public Reservation updateReservation(UpdateReservationRequest request) {

		Reservation reservation = reservationRepository.findById(request.getId()).get();

		reservation.setCheckedIn(request.isCheckIn());
		reservation.setNumberOfBags(request.getNumberOfBags());

		return reservationRepository.save(reservation);

	}
}
