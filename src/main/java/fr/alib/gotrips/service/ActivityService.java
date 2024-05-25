package fr.alib.gotrips.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.alib.gotrips.exception.CompanyNotFoundException;
import fr.alib.gotrips.exception.IdNotFoundException;
import fr.alib.gotrips.exception.OfferNotFoundException;
import fr.alib.gotrips.model.auth.CustomUserDetails;
import fr.alib.gotrips.model.dto.inbound.EvaluationDTO;
import fr.alib.gotrips.model.dto.inbound.ActivityDTO;
import fr.alib.gotrips.model.dto.inbound.PeriodReservationDTO;
import fr.alib.gotrips.model.dto.outbound.CalendarPairUnitDTO;
import fr.alib.gotrips.model.dto.outbound.EvaluationDetailsDTO;
import fr.alib.gotrips.model.dto.outbound.HotelDetailsDTO;
import fr.alib.gotrips.model.dto.outbound.ActivityDetailsDTO;
import fr.alib.gotrips.model.entity.company.ActivityCompany;
import fr.alib.gotrips.model.entity.offers.Evaluation;
import fr.alib.gotrips.model.entity.offers.Activity;
import fr.alib.gotrips.model.entity.reservation.ActivityReservation;
import fr.alib.gotrips.model.entity.user.FacturationData;
import fr.alib.gotrips.model.entity.user.User;
import fr.alib.gotrips.model.repository.ActivityRepository;
import fr.alib.gotrips.model.repository.EvaluationRepository;
import fr.alib.gotrips.model.repository.FacturationDataRepository;
import fr.alib.gotrips.model.repository.UserRepository;
import fr.alib.gotrips.model.repository.reservation.ActivityReservationRepository;
import fr.alib.gotrips.utils.SanitationUtils;
import fr.alib.gotrips.utils.TimeUtils;
import io.jsonwebtoken.lang.Arrays;

@Service
@Transactional
public class ActivityService {
	@Autowired
	private ActivityRepository aRepo;
	
	@Autowired
	private UserRepository uRepo;
	
	@Autowired
	private ActivityReservationRepository aResRepo;
	
	@Autowired
	private FacturationDataRepository facRepo;
	
	@Autowired
	private EvaluationRepository evalRepo;
	
	@Autowired
	private UserService uService;
	
	private final String[] filterOptions = new String[] {
			"qry", "page", "cntry", "city", "miprc", "mxprc", "mieval", "mxeval", "srtby", "srtordr"
	};
	private final String[] sortingOptions = new String[] {
			"price_per_day", "average_evaluation", "spots", "city", "country", "zip_code", "title", "description"
	};
	
	private final String[] evaluationsFilterOptions = new String[] {
			"page"
	};
	
	private final List<String> filterOpts = Arrays.asList(filterOptions);
	private final List<String> sortOpts = Arrays.asList(sortingOptions);
	private final List<String> evaluationsFilterOpts = Arrays.asList(evaluationsFilterOptions);

	public long getActivityOccupiedRooms(Date date)
	{
		return this.aResRepo.countByBeginDateLessThanEqualAndEndDateGreaterThanEqual(date, date);
	}
	
	public boolean isReservationAvailable(Long activityId, Date beginDate, Date endDate)
	{
		List<CalendarPairUnitDTO> list = getCalendar(activityId, beginDate, endDate);
		return list.stream().allMatch(c -> {
			return c.getFound().equals(true);
		});
	}
	
	public List<CalendarPairUnitDTO> getCalendar(Long activityId, Date minDate, Date maxDate) throws IdNotFoundException
	{
		Optional<Activity> activityOptional = this.aRepo.findById(activityId);
		
		if (activityOptional.isEmpty()) {
			throw new IdNotFoundException("Activity with id '" + activityId + "' couldn't be found.");
		}
		
		LocalDate lMinDate = minDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		long days = TimeUtils.totalDaysWithinDates(minDate, maxDate);
		List<CalendarPairUnitDTO> list =  new ArrayList<CalendarPairUnitDTO>();
		
		for (long i = 0; i < days; ++i)
		{
			LocalDate current = lMinDate.plusDays(i);
			Date currentDate = Date.from(current.atStartOfDay(ZoneId.systemDefault()).toInstant());
			list.add( 
					new CalendarPairUnitDTO(
							currentDate.getTime(), 
							this.getActivityOccupiedRooms(currentDate) < activityOptional.get().getSpots()
							)
					);
		}
		
		return list;
	}
	
	public List<ActivityDetailsDTO> getCompanyActivities(Long companyId) throws IllegalArgumentException, IdNotFoundException
	{
		if (companyId == null) throw new IllegalArgumentException("Given id is null.");
		return this.aRepo.findAllByActivityCompanyId(companyId)
				.stream()
				.map(f -> {
					return new ActivityDetailsDTO(f);
				})
				.collect(Collectors.toList());
	}
	
	public List<ActivityDetailsDTO> getActivities(Map<String, String> params) throws IllegalArgumentException
	{
		for (String key : params.keySet()) {
			if (!filterOpts.contains(key)) {
				throw new IllegalArgumentException();
			}
		}
		
		String sortOption = params.get("srtby");
		if (sortOption != null && !sortOpts.contains(sortOption)) {
			throw new IllegalArgumentException();
		}
		
		
		try {
			Integer order = params.get("srtordr") == null ? 1 : -1;
			Pageable pageable = PageRequest.of(
					params.get("page") != null ? Integer.parseInt(params.get("page")) : 0, 
					10,
					params.get("srtby") == null ? 
							Sort.unsorted() : 
							order == 1 ? Sort.by(params.get("srtby")).ascending() : Sort.by(params.get("srtby")).descending()
			);
			
			Page<Activity> result = this.aRepo.search(
					SanitationUtils.stringGetNullIfEmpty(params.get("qry")), 
					SanitationUtils.stringGetNullIfEmpty(params.get("cntry")), 
					SanitationUtils.stringGetNullIfEmpty(params.get("city")), 
					SanitationUtils.floatGetNullIfEmpty(params.get("miprc")),
					SanitationUtils.floatGetNullIfEmpty(params.get("mxprc")),
					SanitationUtils.floatGetNullIfEmpty(params.get("mieval")),
					SanitationUtils.floatGetNullIfEmpty(params.get("mxeval")),
					pageable
					);
			
			List<ActivityDetailsDTO> activityDetails = result.toList().stream().map((h)->{
				return new ActivityDetailsDTO(h);
			}).collect(Collectors.toList());
			
			return activityDetails;
		} catch (Exception e) {
			return new ArrayList<ActivityDetailsDTO>();
		}
		
	}
	
	@Transactional(rollbackFor = Exception.class)
	public Activity createActivity(Long userId, ActivityDTO dto) throws IdNotFoundException, CompanyNotFoundException
	{
		CustomUserDetails userDetails = (CustomUserDetails) this.uService.loadUserById(userId);
		User user = userDetails.getUser();
		ActivityCompany company = user.getActivityCompany();
		if (company == null) throw new CompanyNotFoundException("Couldn't find company for user id '" + userId + "'.");
		Activity activity = new Activity(dto);
		activity.setActivityCompany(company);
		activity = this.aRepo.save(activity);
		return activity;
	}
	
	public Activity getActivity(Long activityId) throws OfferNotFoundException
	{
		Optional<Activity> result = this.aRepo.findById(activityId);
		
		if (result.isPresent()) {
			return result.get();
		}else {
			throw new OfferNotFoundException("Couldn't find activity with id '" + activityId + "'.");
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public Activity updateActivity(Long activityId, ActivityDTO dto)  throws OfferNotFoundException
	{
		Optional<Activity> result = this.aRepo.findById(activityId);
		
		if (result.isPresent()) {
			Activity activity = result.get();
			activity.applyDTO(dto);
			return this.aRepo.save(activity);
		}else {
			throw new OfferNotFoundException("Couldn't find activity with id '" + activityId + "'.");
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void deleteActivity(Long activityId)
	{
		this.aRepo.deleteById(activityId);
	}
	
	// Evaluations
	
	@Transactional(rollbackFor = Exception.class)
	public Activity updateActivityAverageEvaluation(Long activityId) throws OfferNotFoundException
	{
		Optional<Activity> activityResult = this.aRepo.findById(activityId);
		if (activityResult.isPresent()) {
			Activity activity = activityResult.get();
			activity.setAverageEvaluation(new BigDecimal(Double.toString( aRepo.findAverageNoteById(activityId) )));
			return this.aRepo.save(activity);
		}else {
			throw new OfferNotFoundException("Couldn't find activity with id '" + activityId + "'.");
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public Evaluation addEvaluation(Long userId, Long activityId, EvaluationDTO dto) throws IdNotFoundException, OfferNotFoundException
	{
		CustomUserDetails userDetails = (CustomUserDetails) this.uService.loadUserById(userId);
		Optional<Activity> activityResult = this.aRepo.findById(activityId);
		if (activityResult.isPresent()) {
			Activity activity = activityResult.get();
			Evaluation eval = new Evaluation(dto);
			eval.setUser(userDetails.getUser());
			eval.setActivity(activityResult.get());
			activity.getEvaluations().add(eval);
			activity = this.aRepo.save(activity);
			aRepo.save(activity);
			updateActivityAverageEvaluation(activity.getId());
			return eval;
		}else {
			throw new OfferNotFoundException("Couldn't find activity with id '" + activityId + "'.");
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public EvaluationDetailsDTO editEvaluation(Long evaluationId, EvaluationDTO dto) throws OfferNotFoundException
	{
		if (!this.evalRepo.existsById(evaluationId)) throw new OfferNotFoundException("Couldn't find evaluation with id '" + evaluationId + "'.");
		Optional<Evaluation> eval = this.evalRepo.findById(evaluationId);
		if (eval.isPresent()) {
			Evaluation data = eval.get();
			data.applyDTO(dto);
			data = this.evalRepo.save(data);
			updateActivityAverageEvaluation(data.getActivity().getId());
			return new EvaluationDetailsDTO(data);
		}else {
			throw new OfferNotFoundException("Couldn't find evaluation with id '" + evaluationId + "'.");
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void removeEvaluation(Long evaluationId) throws OfferNotFoundException
	{
		Optional<Evaluation> ev = this.evalRepo.findById(evaluationId);
		if (ev.isPresent()) {
			Long activityId = ev.get().getActivity().getId();
			this.evalRepo.deleteById(evaluationId);
			updateActivityAverageEvaluation(activityId);
		}else {
			throw new OfferNotFoundException("Couldn't find evaluation with id '" + evaluationId + "'.");
		}
	}
	
	public List<EvaluationDetailsDTO> getEvaluations(Long activityId, Map<String, String> params) throws IllegalArgumentException
	{
		if (!this.aRepo.existsById(activityId)) throw new OfferNotFoundException("Couldn't find activity with id '" + activityId + "'.");
		
		for (String key : params.keySet()) {
			if (!evaluationsFilterOpts.contains(key)) {
				throw new IllegalArgumentException();
			}
		}
		
		final Integer page = params.get("page") != null ? Integer.valueOf(params.get("page")) : 0;
		Pageable pageable = PageRequest.of(page, 10);
		return this.evalRepo.findAllByFlightId(activityId, pageable).stream().map(e -> {
			return new EvaluationDetailsDTO(e);
		}).collect(Collectors.toList());
	}
	
	// Reservations
	
	public ActivityReservation createReservation(Long userId, Long activityId, PeriodReservationDTO dto) throws IdNotFoundException
	{	
		Optional<User> userOptional = this.uRepo.findById(userId);
		Optional<Activity> activityOptional = this.aRepo.findById(activityId);
		
		if (userOptional.isPresent() && activityOptional.isPresent()) {
			User user = userOptional.get();
			Activity activity = activityOptional.get();
			Optional<FacturationData> fDataOptional = this.facRepo.findById( dto.getCardId().longValue() );
			if (fDataOptional.isPresent()) {
				FacturationData fData = fDataOptional.get();
				ActivityReservation reservation = new ActivityReservation(
						user, 
						activity, 
						fData.getPaymentData(), 
						new Date( dto.getBeginTime() ),
						new Date( dto.getEndTime() )
						);
				reservation = this.aResRepo.save(reservation);
				return reservation;
			}else {
				throw new IdNotFoundException("Card with id '" + dto.getCardId() + "' couldn't be found.");
			}
			
		}else {
			 throw new IdNotFoundException("User or activity with given id couldn't be found.");
		}
	}
	
	public ActivityReservation getReservation(Long reservationId) throws IdNotFoundException
	{	
		Optional<ActivityReservation> hResData = this.aResRepo.findById(reservationId);
		if (hResData.isPresent()) {
			return hResData.get();
		}else {
			 throw new IdNotFoundException("Reservation with given id '" + reservationId + "' couldn't be found.");
		}
	}
	
	public List<ActivityReservation> getReservations(Long userId, Integer page) throws IdNotFoundException
	{	
		if (this.uRepo.existsById(userId)) {
			Pageable pageable = PageRequest.of(page, 10);
			return this.aResRepo.findAllByUserId(userId, pageable);
		}else {
			 throw new IdNotFoundException("User with given id '" + userId + "' couldn't be found.");
		}
	}
	
}
