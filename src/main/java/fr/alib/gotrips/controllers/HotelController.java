package fr.alib.gotrips.controllers;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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
import fr.alib.gotrips.model.dto.inbound.HotelDTO;
import fr.alib.gotrips.model.dto.inbound.PeriodReservationDTO;
import fr.alib.gotrips.model.dto.outbound.EvaluationDetailsDTO;
import fr.alib.gotrips.model.dto.outbound.FlightDetailsDTO;
import fr.alib.gotrips.model.dto.outbound.HotelDetailsDTO;
import fr.alib.gotrips.model.dto.outbound.HotelReservationDetailsDTO;
import fr.alib.gotrips.model.entity.company.FlightCompany;
import fr.alib.gotrips.model.entity.company.HotelCompany;
import fr.alib.gotrips.model.entity.offers.Evaluation;
import fr.alib.gotrips.model.entity.offers.Hotel;
import fr.alib.gotrips.model.entity.reservation.HotelReservation;
import fr.alib.gotrips.model.entity.user.FacturationData;
import fr.alib.gotrips.model.repository.EvaluationRepository;
import fr.alib.gotrips.model.repository.FacturationDataRepository;
import fr.alib.gotrips.model.repository.HotelRepository;
import fr.alib.gotrips.model.repository.company.FlightCompanyRepository;
import fr.alib.gotrips.model.repository.company.HotelCompanyRepository;
import fr.alib.gotrips.model.repository.reservation.HotelReservationRepository;
import fr.alib.gotrips.service.HotelService;
import fr.alib.gotrips.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/hotel")
public class HotelController {

	@Autowired
	private HotelRepository hRepo;
	
	@Autowired
	private HotelReservationRepository hResRepo;
	
	@Autowired
	private HotelCompanyRepository hComp;
	
	@Autowired
	private FacturationDataRepository fDatRepo;
	
	@Autowired
	private EvaluationRepository eRepo;
	
	@Autowired
	private HotelService hService;
	
	@GetMapping("/search")
	public ResponseEntity<List<HotelDetailsDTO>> search(@RequestParam Map<String, String> params)
	{
		try {
			return ResponseEntity.ok().body(this.hService.getHotels(params));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().build();
		}
	}

	@GetMapping("/{id}/calendar/search/{begindate}/{enddate}")
	public ResponseEntity<?> calendar(
			@PathVariable("id") Long hotelId,
			@PathVariable("begindate") Long beginTime,
			@PathVariable("enddate") Long endTime
			)
	{
		Date beginDate = new Date(beginTime);
		Date endDate = new Date(endTime);
		return ResponseEntity.ok( this.hService.getCalendar(hotelId, beginDate, endDate) );
	}
	
	@PostMapping("/create")
	public ResponseEntity<HotelDetailsDTO> create(@Valid @RequestBody HotelDTO dto)
	{
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Hotel hotel = this.hService.createHotel(userDetails.getUser().getId(), dto);
		return ResponseEntity.created(null).body(new HotelDetailsDTO(hotel));
	}
	
	@GetMapping("/{id}/details")
	public ResponseEntity<?> details(@PathVariable("id") Long id)
	{
		return ResponseEntity.ok().body( new HotelDetailsDTO( this.hService.getHotel(id) ) );
	}
	
	@PutMapping("/{id}/edit")
	public ResponseEntity<?> edit(
			@PathVariable("id") Long id, 
			@Valid @RequestBody HotelDTO dto, 
			HttpServletRequest request
			)
	{
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long userId = userDetails.getUser().getId();
		Hotel hotel = this.hService.getHotel(id);
		if ( request.isUserInRole("ADMIN") || hotel.getHotelCompany().getUser().getId().equals(userId)) {
			return ResponseEntity.ok().body( new HotelDetailsDTO( this.hService.updateHotel(id, dto) ) );
		}else {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
	}
	
	@DeleteMapping("/{id}/delete")
	public ResponseEntity<?> delete(@PathVariable("id") Long id, HttpServletRequest request)
	{
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long userId = userDetails.getUser().getId();
		Hotel hotel = this.hService.getHotel(id);
		if ( request.isUserInRole("ADMIN") || hotel.getHotelCompany().getUser().getId().equals(userId)) {
			this.hService.deleteHotel(id);
			return ResponseEntity.noContent().build();
		}else {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
	}
	
	@PostMapping("/{id}/reservations/create")
	public ResponseEntity<?> createReservation(
			@PathVariable("id") Long hotelId, 
			@Valid @RequestBody PeriodReservationDTO dto
			)
	{
		try {
			
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			if (this.hRepo.existsById(hotelId)) {
				Optional<FacturationData> fData = this.fDatRepo.findById(dto.getCardId().longValue());
				if (fData.isPresent() && fData.get().getUser().getId().equals(userDetails.getUser().getId())) {
					Date beginDate = new Date( dto.getBeginTime() );
					Date endDate = new Date( dto.getEndTime() );
					if (this.hService.isReservationAvailable(hotelId, beginDate, endDate)) {
						this.hService.createReservation(userDetails.getUser().getId(), hotelId, dto);
						return ResponseEntity.ok().build();
					}else {
						return ResponseEntity.status(HttpStatus.CONFLICT).build();
					}
				}else {
					return ResponseEntity.notFound().build();
				}
			}else {
				return ResponseEntity.notFound().build();
			}
			
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build();
		}

	}
	
	@GetMapping("/reservations/get/{reservationId}")
	public ResponseEntity<?> getReservation(
			@PathVariable("reservationId") Long reservationId
			)
	{
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Optional<HotelReservation> hotelResOptional = this.hResRepo.findById(reservationId);
		
		if ( hotelResOptional.isPresent() ) {
			if ( hotelResOptional.get().getUser().getId().equals(userDetails.getUser().getId()) ) {
				return ResponseEntity.ok( new HotelReservationDetailsDTO( this.hService.getReservation(reservationId) ) );
			}else {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
			}
		}else {
			return ResponseEntity.notFound().build();
		}	
	}
	
	@GetMapping("/{id}/reservations/getAll")
	public ResponseEntity<?> getAllReservations(
			@RequestParam Map<String, String> params
			)
	{
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Integer page = params.get("page") != null ? Integer.valueOf(params.get("page")) : 0;
			
			return ResponseEntity.ok(
					this.hService.getReservations(userDetails.getUser().getId(), page)
						.stream().map(fRes->{
							return new HotelReservationDetailsDTO(fRes);
						}).collect(Collectors.toList())
			);

		} catch (NumberFormatException e) {
			return ResponseEntity.badRequest().build();
		} catch (IdNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PostMapping("/{id}/evaluations/create")
	public ResponseEntity<?> createEvaluation(
			@PathVariable("id") Long hotelId,
			@Valid @RequestBody EvaluationDTO dto
			)
	{
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Evaluation eval = this.hService.addEvaluation(userDetails.getUser().getId(), hotelId, dto);
			return ResponseEntity.created(null).body( new EvaluationDetailsDTO(eval) );
		}catch (IdNotFoundException|OfferNotFoundException e) {
			return ResponseEntity.notFound().build();
		}catch (Exception e) {
			return ResponseEntity.internalServerError().build();
		}
	}
	
	@GetMapping("/{id}/evaluations/getAll")
	public ResponseEntity<?> getAllEvaluations(
			@PathVariable("id") Long hotelId,
			@RequestParam Map<String, String> params
			)
	{
		try {
			return ResponseEntity.ok(this.hService.getEvaluations(hotelId, params));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().build();
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build();
		}
	}
	
	@PutMapping("/{id}/evaluations/edit/{evaluationId}")
	public ResponseEntity<?> editEvaluation(
			@PathVariable("id") Long hotelId,
			@PathVariable("evaluationId") Long evalId,
			@Valid @RequestBody EvaluationDTO dto,
			HttpServletRequest request
			)
	{
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Optional<Evaluation> evalOptional = this.eRepo.findById(evalId);
			if (evalOptional.isPresent()) {
				if (request.isUserInRole("ADMIN") || evalOptional.get().getUser().getId().equals(userDetails.getUser().getId()))
				{
					return ResponseEntity.ok(this.hService.editEvaluation(evalId, dto));
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
			@PathVariable("id") Long hotelId,
			@PathVariable("evaluationId") Long evalId,
			HttpServletRequest request
			)
	{
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Optional<Evaluation> evalOptional = this.eRepo.findById(evalId);
			if (evalOptional.isPresent()) {
				if (request.isUserInRole("ADMIN") || evalOptional.get().getUser().getId().equals(userDetails.getUser().getId()))
				{
					this.hService.removeEvaluation(evalId);
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
	
	@GetMapping("/company/{companyId}/hotels/getAll")
	public ResponseEntity<List<HotelDetailsDTO>> getCompanyHotels(
			@PathVariable("companyId") Long companyId,
			HttpServletRequest request
			)
	{
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Optional<HotelCompany> company = this.hComp.findById(companyId);
			boolean isOwner = company.isPresent() && company.get().getUser().getId().equals(userDetails.getUser().getId());
			if (isOwner || request.isUserInRole("ADMIN")) {
				return ResponseEntity.ok().body(this.hService.getCompanyHotels(companyId));
			}else {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
			}
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	
}
