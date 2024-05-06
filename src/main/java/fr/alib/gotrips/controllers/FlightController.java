package fr.alib.gotrips.controllers;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.alib.gotrips.model.auth.CustomUserDetails;
import fr.alib.gotrips.model.dto.inbound.FlightDTO;
import fr.alib.gotrips.model.dto.inbound.ReservationDTO;
import fr.alib.gotrips.model.dto.outbound.FlightDetailsDTO;
import fr.alib.gotrips.model.entity.offers.Flight;
import fr.alib.gotrips.model.repository.FlightRepository;
import fr.alib.gotrips.service.FlightService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/flight")
public class FlightController {

	@Autowired
	private FlightRepository fRepo;
	
	@Autowired
	private FlightService fService;
	
	@GetMapping("/search")
	public ResponseEntity<List<FlightDetailsDTO>> search(@RequestParam Map<String, String> params)
	{
		try {
			return ResponseEntity.ok().body(this.fService.getFlights(params));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@PostMapping("/create")
	public ResponseEntity<?> create(@Valid @RequestBody FlightDTO dto, Principal principal)
	{
		CustomUserDetails userDetails = (CustomUserDetails) principal;
		Flight flight = this.fService.createFlight(userDetails.getUser().getId(), dto);
		return ResponseEntity.ok().body(flight);
	}
	
	@GetMapping("/{id}/details")
	public ResponseEntity<?> details(@PathVariable("id") Long id)
	{
		return ResponseEntity.ok().body( new FlightDetailsDTO( this.fService.getFlight(id) ) );
	}
	
	@PutMapping("/{id}/edit")
	public ResponseEntity<?> edit(@PathVariable("id") Long id, @Valid @RequestBody FlightDTO dto, Principal principal, HttpServletRequest request)
	{
		CustomUserDetails userDetails = (CustomUserDetails) principal;
		Long userId = userDetails.getUser().getId();
		Flight flight = this.fService.getFlight(id);
		if ( request.isUserInRole("ADMIN") || flight.getFlightCompany().getUser().getId().equals(userId)) {
			return ResponseEntity.ok().body( new FlightDetailsDTO( this.fService.updateFlight(id, dto) ) );
		}else {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
	}
	
	@DeleteMapping("/{id}/delete")
	public ResponseEntity<?> delete(@PathVariable("id") Long id, Principal principal, HttpServletRequest request)
	{
		CustomUserDetails userDetails = (CustomUserDetails) principal;
		Long userId = userDetails.getUser().getId();
		Flight flight = this.fService.getFlight(id);
		if ( request.isUserInRole("ADMIN") || flight.getFlightCompany().getUser().getId().equals(userId)) {
			this.fService.deleteFlight(id);
			return ResponseEntity.noContent().build();
		}else {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
	}
	
	@PostMapping("/{id}/reservations/create")
	public ResponseEntity<?> createReservation(@PathVariable("id") Long id, @Valid @RequestBody ReservationDTO dto)
	{
		return ResponseEntity.ok().build();
	}
	
}
