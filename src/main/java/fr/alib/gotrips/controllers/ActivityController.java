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
import fr.alib.gotrips.model.dto.inbound.ActivityDTO;
import fr.alib.gotrips.model.dto.inbound.EvaluationDTO;
import fr.alib.gotrips.model.dto.inbound.PeriodReservationDTO;
import fr.alib.gotrips.model.dto.outbound.ActivityDetailsDTO;
import fr.alib.gotrips.model.dto.outbound.ActivityReservationDetailsDTO;
import fr.alib.gotrips.model.dto.outbound.EvaluationDetailsDTO;
import fr.alib.gotrips.model.entity.offers.Activity;
import fr.alib.gotrips.model.entity.offers.Evaluation;
import fr.alib.gotrips.model.entity.reservation.ActivityReservation;
import fr.alib.gotrips.model.entity.user.FacturationData;
import fr.alib.gotrips.model.repository.ActivityRepository;
import fr.alib.gotrips.model.repository.EvaluationRepository;
import fr.alib.gotrips.model.repository.FacturationDataRepository;
import fr.alib.gotrips.model.repository.reservation.ActivityReservationRepository;
import fr.alib.gotrips.service.ActivityService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/activity")
public class ActivityController {

	@Autowired
	private ActivityRepository aRepo;
	
	@Autowired
	private ActivityReservationRepository aResRepo;
	
	@Autowired
	private FacturationDataRepository fDatRepo;
	
	@Autowired
	private EvaluationRepository eRepo;
	
	@Autowired
	private ActivityService hService;
	
	@GetMapping("/search")
	public ResponseEntity<List<ActivityDetailsDTO>> search(@RequestParam Map<String, String> params)
	{
		try {
			return ResponseEntity.ok().body(this.hService.getActivitys(params));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().build();
		}
	}

	@GetMapping("/{id}/calendar/search/{begindate}/{enddate}")
	public ResponseEntity<?> calendar(
			@PathVariable("id") Long activityId,
			@PathVariable("begindate") Long beginTime,
			@PathVariable("enddate") Long endTime
			)
	{
		Date beginDate = new Date(beginTime);
		Date endDate = new Date(endTime);
		return ResponseEntity.ok( this.hService.getCalendar(activityId, beginDate, endDate) );
	}
	
	@PostMapping("/create")
	public ResponseEntity<?> create(@Valid @RequestBody ActivityDTO dto, Principal principal)
	{
		CustomUserDetails userDetails = (CustomUserDetails) principal;
		Activity activity = this.hService.createActivity(userDetails.getUser().getId(), dto);
		return ResponseEntity.created(null).body(activity);
	}
	
	@GetMapping("/{id}/details")
	public ResponseEntity<?> details(@PathVariable("id") Long id)
	{
		return ResponseEntity.ok().body( new ActivityDetailsDTO( this.hService.getActivity(id) ) );
	}
	
	@PutMapping("/{id}/edit")
	public ResponseEntity<?> edit(
			@PathVariable("id") Long id, 
			@Valid @RequestBody ActivityDTO dto, 
			Principal principal, 
			HttpServletRequest request
			)
	{
		CustomUserDetails userDetails = (CustomUserDetails) principal;
		Long userId = userDetails.getUser().getId();
		Activity activity = this.hService.getActivity(id);
		if ( request.isUserInRole("ADMIN") || activity.getActivityCompany().getUser().getId().equals(userId)) {
			return ResponseEntity.ok().body( new ActivityDetailsDTO( this.hService.updateActivity(id, dto) ) );
		}else {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
	}
	
	@DeleteMapping("/{id}/delete")
	public ResponseEntity<?> delete(@PathVariable("id") Long id, Principal principal, HttpServletRequest request)
	{
		CustomUserDetails userDetails = (CustomUserDetails) principal;
		Long userId = userDetails.getUser().getId();
		Activity activity = this.hService.getActivity(id);
		if ( request.isUserInRole("ADMIN") || activity.getActivityCompany().getUser().getId().equals(userId)) {
			this.hService.deleteActivity(id);
			return ResponseEntity.noContent().build();
		}else {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
	}
	
	@PostMapping("/{id}/reservations/create")
	public ResponseEntity<?> createReservation(
			@PathVariable("id") Long activityId, 
			@Valid @RequestBody PeriodReservationDTO dto,
			Principal principal
			)
	{
		try {
			
			CustomUserDetails userDetails = (CustomUserDetails) principal;
			
			if (this.aRepo.existsById(activityId)) {
				Optional<FacturationData> fData = this.fDatRepo.findById(dto.getCardId().longValue());
				if (fData.isPresent() && fData.get().getUser().getId().equals(userDetails.getUser().getId())) {
					Date beginDate = new Date( dto.getBeginTime() );
					Date endDate = new Date( dto.getEndTime() );
					if (this.hService.isReservationAvailable(activityId, beginDate, endDate)) {
						this.hService.createReservation(userDetails.getUser().getId(), activityId, dto);
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
	
	@GetMapping("/{id}/reservations/get/{reservationId}")
	public ResponseEntity<?> getReservation(
			@PathVariable("id") Long activityId,
			@PathVariable("reservationId") Long reservationId,
			Principal principal
			)
	{
		CustomUserDetails userDetails = (CustomUserDetails) principal;
		Optional<Activity> activityOptional = this.aRepo.findById(activityId);
		Optional<ActivityReservation> activityResOptional = this.aResRepo.findById(reservationId);
		
		if ( activityOptional.isPresent() && activityResOptional.isPresent() ) {
			if ( activityResOptional.get().getUser().getId().equals(userDetails.getUser().getId()) ) {
				return ResponseEntity.ok( new ActivityReservationDetailsDTO( this.hService.getReservation(reservationId) ) );
			}else {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
			}
		}else {
			return ResponseEntity.notFound().build();
		}	
	}
	
	@GetMapping("/{id}/reservations/getAll")
	public ResponseEntity<?> getAllReservations(
			@PathVariable("id") Long activityId,
			Principal principal,
			Map<String, String> params
			)
	{
		try {
			CustomUserDetails userDetails = (CustomUserDetails) principal;
			Integer page = params.get("page") != null ? Integer.valueOf(params.get("page")) : 0;
			
			if ( this.aRepo.existsById(activityId) ) {
				return ResponseEntity.ok(
						this.hService.getReservations(userDetails.getUser().getId(), page)
							.stream().map(fRes->{
								return new ActivityReservationDetailsDTO(fRes);
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
			@PathVariable("id") Long activityId,
			@Valid @RequestBody EvaluationDTO dto,
			Principal principal
			)
	{
		try {
			CustomUserDetails userDetails = (CustomUserDetails) principal;
			Evaluation eval = this.hService.addEvaluation(userDetails.getUser().getId(), activityId, dto);
			return ResponseEntity.created(null).body( new EvaluationDetailsDTO(eval) );
		}catch (IdNotFoundException|OfferNotFoundException e) {
			return ResponseEntity.notFound().build();
		}catch (Exception e) {
			return ResponseEntity.internalServerError().build();
		}
	}
	
	@GetMapping("/{id}/evaluations/getAll")
	public ResponseEntity<?> getAllEvaluations(
			@PathVariable("id") Long activityId,
			Map<String, String> params
			)
	{
		try {
			return ResponseEntity.ok(this.hService.getEvaluations(activityId, params));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().build();
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build();
		}
	}
	
	@PutMapping("/{id}/evaluations/edit/{evaluationId}")
	public ResponseEntity<?> editEvaluation(
			@PathVariable("id") Long activityId,
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
			@PathVariable("id") Long activityId,
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
	
}