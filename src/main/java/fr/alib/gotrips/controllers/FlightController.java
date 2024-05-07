package fr.alib.gotrips.controllers;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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

import fr.alib.gotrips.exception.IdNotFoundException;
import fr.alib.gotrips.exception.OfferNotFoundException;
import fr.alib.gotrips.model.auth.CustomUserDetails;
import fr.alib.gotrips.model.dto.inbound.EvaluationDTO;
import fr.alib.gotrips.model.dto.inbound.FlightDTO;
import fr.alib.gotrips.model.dto.inbound.ReservationDTO;
import fr.alib.gotrips.model.dto.outbound.EvaluationDetailsDTO;
import fr.alib.gotrips.model.dto.outbound.FlightDetailsDTO;
import fr.alib.gotrips.model.dto.outbound.FlightReservationDetailsDTO;
import fr.alib.gotrips.model.entity.offers.Evaluation;
import fr.alib.gotrips.model.entity.offers.Flight;
import fr.alib.gotrips.model.entity.reservation.FlightReservation;
import fr.alib.gotrips.model.entity.user.FacturationData;
import fr.alib.gotrips.model.repository.EvaluationRepository;
import fr.alib.gotrips.model.repository.FacturationDataRepository;
import fr.alib.gotrips.model.repository.FlightRepository;
import fr.alib.gotrips.model.repository.reservation.FlightReservationRepository;
import fr.alib.gotrips.service.FlightService;
import fr.alib.gotrips.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/flight")
public class FlightController {

	@Autowired
	private FlightRepository fRepo;
	
	@Autowired
	private FlightReservationRepository fResRepo;
	
	@Autowired
	private FacturationDataRepository fDatRepo;
	
	@Autowired
	private EvaluationRepository eRepo;
	
	@Autowired
	private FlightService fService;
	
	@Autowired
	private UserService uService;
	
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
	public ResponseEntity<?> createReservation(
			@PathVariable("id") Long flightId, 
			@Valid @RequestBody ReservationDTO dto,
			Principal principal
			)
	{
		CustomUserDetails userDetails = (CustomUserDetails) principal;
		
		if (this.fRepo.existsById(flightId)) {
			Optional<FacturationData> fData = this.fDatRepo.findById(dto.getCardId().longValue());
			if (fData.isPresent() && fData.get().getUser().getId().equals(userDetails.getUser().getId())) {
				this.fService.createReservation(userDetails.getUser().getId(), flightId, dto);
				return ResponseEntity.ok().build();
			}else {
				return ResponseEntity.notFound().build();
			}
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping("/{id}/reservations/get/{reservationId}")
	public ResponseEntity<?> getReservation(
			@PathVariable("id") Long flightId,
			@PathVariable("reservationId") Long reservationId,
			Principal principal
			)
	{
		CustomUserDetails userDetails = (CustomUserDetails) principal;
		Optional<Flight> flightOptional = this.fRepo.findById(flightId);
		Optional<FlightReservation> flightResOptional = this.fResRepo.findById(reservationId);
		
		if ( flightOptional.isPresent() && flightResOptional.isPresent() ) {
			if ( flightResOptional.get().getUser().getId().equals(userDetails.getUser().getId()) ) {
				return ResponseEntity.ok( new FlightReservationDetailsDTO( this.fService.getReservation(reservationId) ) );
			}else {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
			}
		}else {
			return ResponseEntity.notFound().build();
		}	
	}
	
	@GetMapping("/{id}/reservations/getAll")
	public ResponseEntity<?> getAllReservations(
			@PathVariable("id") Long flightId,
			Principal principal,
			Map<String, String> params
			)
	{
		try {
			CustomUserDetails userDetails = (CustomUserDetails) principal;
			Integer page = params.get("page") != null ? Integer.valueOf(params.get("page")) : 0;
			
			if ( this.fRepo.existsById(flightId) ) {
				return ResponseEntity.ok(
						this.fService.getReservations(userDetails.getUser().getId(), page)
							.stream().map(fRes->{
								return new FlightReservationDetailsDTO(fRes);
							}).collect(Collectors.toList())
				);
			}else {
				return ResponseEntity.notFound().build();
			}	
		} catch (NumberFormatException e) {
			return ResponseEntity.badRequest().build();
		} catch (IdNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PostMapping("/{id}/evaluations/create")
	public ResponseEntity<?> createEvaluation(
			@PathVariable("id") Long flightId,
			@Valid @RequestBody EvaluationDTO dto,
			Principal principal
			)
	{
		try {
			CustomUserDetails userDetails = (CustomUserDetails) principal;
			Evaluation eval = this.fService.addEvaluation(userDetails.getUser().getId(), flightId, dto);
			return ResponseEntity.created(null).body( new EvaluationDetailsDTO(eval) );
		}catch (IdNotFoundException|OfferNotFoundException e) {
			return ResponseEntity.notFound().build();
		}catch (Exception e) {
			return ResponseEntity.internalServerError().build();
		}
	}
	
	@GetMapping("/{id}/evaluations/getAll")
	public ResponseEntity<?> getAllEvaluations(
			@PathVariable("id") Long flightId,
			Map<String, String> params
			)
	{
		try {
			return ResponseEntity.ok(this.fService.getEvaluations(flightId, params));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().build();
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build();
		}
	}
	
	@PutMapping("/{id}/evaluations/edit/{evaluationId}")
	public ResponseEntity<?> editEvaluation(
			@PathVariable("id") Long flightId,
			@PathVariable("evaluationId") Long evalId,
			@Valid @RequestBody EvaluationDTO dto,
			Principal principal,
			HttpServletRequest request
			)
	{
		try {
			CustomUserDetails userDetails = (CustomUserDetails) principal;
			Optional<Evaluation> evalOptional = this.eRepo.findById(evalId);
			if (evalOptional.isPresent()) {
				if (request.isUserInRole("ADMIN") || evalOptional.get().getUser().getId().equals(userDetails.getUser().getId()))
				{
					return ResponseEntity.ok(this.fService.editEvaluation(evalId, dto));
				}else {
					return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
				}
			}else {
				return ResponseEntity.notFound().build();
			}
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build();
		}
	}
	
	@DeleteMapping("/{id}/evaluations/delete/{evaluationId}")
	public ResponseEntity<?> deleteEvaluation(
			@PathVariable("id") Long flightId,
			@PathVariable("evaluationId") Long evalId,
			@Valid @RequestBody EvaluationDTO dto,
			Principal principal,
			HttpServletRequest request
			)
	{
		try {
			CustomUserDetails userDetails = (CustomUserDetails) principal;
			Optional<Evaluation> evalOptional = this.eRepo.findById(evalId);
			if (evalOptional.isPresent()) {
				if (request.isUserInRole("ADMIN") || evalOptional.get().getUser().getId().equals(userDetails.getUser().getId()))
				{
					this.fService.removeEvaluation(evalId);
					return ResponseEntity.noContent().build();
				}else {
					return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
				}
			}else {
				return ResponseEntity.notFound().build();
			}
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build();
		}
	}
	
}
