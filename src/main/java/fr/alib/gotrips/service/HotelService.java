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
import fr.alib.gotrips.model.dto.inbound.FlightDTO;
import fr.alib.gotrips.model.dto.inbound.FlightReservationDTO;
import fr.alib.gotrips.model.dto.inbound.HotelDTO;
import fr.alib.gotrips.model.dto.inbound.PeriodReservationDTO;
import fr.alib.gotrips.model.dto.outbound.CalendarPairUnitDTO;
import fr.alib.gotrips.model.dto.outbound.EvaluationDetailsDTO;
import fr.alib.gotrips.model.dto.outbound.FlightDetailsDTO;
import fr.alib.gotrips.model.dto.outbound.HotelDetailsDTO;
import fr.alib.gotrips.model.entity.company.FlightCompany;
import fr.alib.gotrips.model.entity.company.HotelCompany;
import fr.alib.gotrips.model.entity.offers.Evaluation;
import fr.alib.gotrips.model.entity.offers.Flight;
import fr.alib.gotrips.model.entity.offers.Hotel;
import fr.alib.gotrips.model.entity.reservation.FlightReservation;
import fr.alib.gotrips.model.entity.reservation.HotelReservation;
import fr.alib.gotrips.model.entity.user.FacturationData;
import fr.alib.gotrips.model.entity.user.User;
import fr.alib.gotrips.model.repository.EvaluationRepository;
import fr.alib.gotrips.model.repository.FacturationDataRepository;
import fr.alib.gotrips.model.repository.FlightRepository;
import fr.alib.gotrips.model.repository.HotelRepository;
import fr.alib.gotrips.model.repository.UserRepository;
import fr.alib.gotrips.model.repository.reservation.FlightReservationRepository;
import fr.alib.gotrips.model.repository.reservation.HotelReservationRepository;
import fr.alib.gotrips.utils.SanitationUtils;
import io.jsonwebtoken.lang.Arrays;

@Service
@Transactional
public class HotelService {
	@Autowired
	private HotelRepository hRepo;
	
	@Autowired
	private UserRepository uRepo;
	
	@Autowired
	private HotelReservationRepository hResRepo;
	
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
			"price_per_night", "average_evaluation", "rooms", "city", "country", "zip_code", "name", "description"
	};
	
	private final String[] evaluationsFilterOptions = new String[] {
			"page"
	};
	
	private final List<String> filterOpts = Arrays.asList(filterOptions);
	private final List<String> sortOpts = Arrays.asList(sortingOptions);
	private final List<String> evaluationsFilterOpts = Arrays.asList(evaluationsFilterOptions);

	public long getHotelOccupiedRooms(Date date)
	{
		return this.hResRepo.countByBeginDateLessThanEqualAndEndDateGreaterThanEqual(date, date);
	}
	
	public boolean isReservationAvailable(Long hotelId, Date beginDate, Date endDate)
	{
		List<CalendarPairUnitDTO> list = getCalendar(hotelId, beginDate, endDate);
		return list.stream().allMatch(c -> {
			return c.getFound().equals(true);
		});
	}
	
	public List<CalendarPairUnitDTO> getCalendar(Long hotelId, Date minDate, Date maxDate) throws IdNotFoundException
	{
		Optional<Hotel> hotelOptional = this.hRepo.findById(hotelId);
		
		if (hotelOptional.isEmpty()) {
			throw new IdNotFoundException("Hotel with id '" + hotelId + "' couldn't be found.");
		}
		
		LocalDate lMinDate = minDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate lMaxDate = maxDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		long days = ChronoUnit.DAYS.between(lMinDate, lMaxDate) + 1;
		List<CalendarPairUnitDTO> list =  new ArrayList<CalendarPairUnitDTO>();
		
		for (long i = 0; i < days; ++i)
		{
			LocalDate current = lMinDate.plusDays(i);
			Date currentDate = Date.from(current.atStartOfDay(ZoneId.systemDefault()).toInstant());
			list.add( 
					new CalendarPairUnitDTO(
							currentDate.getTime(), 
							this.getHotelOccupiedRooms(currentDate) < hotelOptional.get().getRooms()
							)
					);
		}
		
		return list;
	}
	
	public List<HotelDetailsDTO> getCompanyHotels(Long companyId) throws IllegalArgumentException, IdNotFoundException
	{
		if (companyId == null) throw new IllegalArgumentException("Given id is null.");
		return this.hRepo.findAllByHotelCompanyId(companyId)
				.stream()
				.map(f -> {
					return new HotelDetailsDTO(f);
				})
				.collect(Collectors.toList());
	}
	
	public List<HotelDetailsDTO> getHotels(Map<String, String> params) throws IllegalArgumentException
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
			
			Page<Hotel> result = this.hRepo.search(
					SanitationUtils.stringGetNullIfEmpty(params.get("qry")), 
					SanitationUtils.stringGetNullIfEmpty(params.get("cntry")), 
					SanitationUtils.stringGetNullIfEmpty(params.get("city")), 
					SanitationUtils.floatGetNullIfEmpty(params.get("miprc")), 
					SanitationUtils.floatGetNullIfEmpty(params.get("mxprc")), 
					SanitationUtils.floatGetNullIfEmpty(params.get("mieval")), 
					SanitationUtils.floatGetNullIfEmpty(params.get("mxeval")), 
					pageable
					);
			
			List<HotelDetailsDTO> hotelDetails = result.toList().stream().map((h)->{
				return new HotelDetailsDTO(h);
			}).collect(Collectors.toList());
			
			return hotelDetails;
		} catch (Exception e) {
			return new ArrayList<HotelDetailsDTO>();
		}
		
	}
	
	@Transactional(rollbackFor = Exception.class)
	public Hotel createHotel(Long userId, HotelDTO dto) throws IdNotFoundException, CompanyNotFoundException
	{
		CustomUserDetails userDetails = (CustomUserDetails) this.uService.loadUserById(userId);
		User user = userDetails.getUser();
		HotelCompany company = user.getHotelCompany();
		if (company == null) throw new CompanyNotFoundException("Couldn't find company for user id '" + userId + "'.");
		Hotel hotel = new Hotel(dto);
		hotel.setHotelCompany(company);
		hotel = this.hRepo.save(hotel);
		return hotel;
	}
	
	public Hotel getHotel(Long hotelId) throws OfferNotFoundException
	{
		Optional<Hotel> result = this.hRepo.findById(hotelId);
		
		if (result.isPresent()) {
			return result.get();
		}else {
			throw new OfferNotFoundException("Couldn't find hotel with id '" + hotelId + "'.");
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public Hotel updateHotel(Long hotelId, HotelDTO dto)  throws OfferNotFoundException
	{
		Optional<Hotel> result = this.hRepo.findById(hotelId);
		
		if (result.isPresent()) {
			Hotel hotel = result.get();
			hotel.applyDTO(dto);
			return this.hRepo.save(hotel);
		}else {
			throw new OfferNotFoundException("Couldn't find hotel with id '" + hotelId + "'.");
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void deleteHotel(Long hotelId)
	{
		this.hRepo.deleteById(hotelId);
	}
	
	// Evaluations
	
	@Transactional(rollbackFor = Exception.class)
	public Hotel updateHotelAverageEvaluation(Long hotelId) throws OfferNotFoundException
	{
		Optional<Hotel> hotelResult = this.hRepo.findById(hotelId);
		if (hotelResult.isPresent()) {
			Hotel hotel = hotelResult.get();
			hotel.setAverageEvaluation(new BigDecimal(Double.toString( hRepo.findAverageNoteById(hotelId) )));
			return this.hRepo.save(hotel);
		}else {
			throw new OfferNotFoundException("Couldn't find hotel with id '" + hotelId + "'.");
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public Evaluation addEvaluation(Long userId, Long hotelId, EvaluationDTO dto) throws IdNotFoundException, OfferNotFoundException
	{
		CustomUserDetails userDetails = (CustomUserDetails) this.uService.loadUserById(userId);
		Optional<Hotel> hotelResult = this.hRepo.findById(hotelId);
		if (hotelResult.isPresent()) {
			Hotel hotel = hotelResult.get();
			Evaluation eval = new Evaluation(dto);
			eval.setUser(userDetails.getUser());
			eval.setHotel(hotelResult.get());
			hotel.getEvaluations().add(eval);
			hotel = this.hRepo.save(hotel);
			hRepo.save(hotel);
			updateHotelAverageEvaluation(hotel.getId());
			return eval;
		}else {
			throw new OfferNotFoundException("Couldn't find hotel with id '" + hotelId + "'.");
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
			updateHotelAverageEvaluation(data.getHotel().getId());
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
			Long hotelId = ev.get().getHotel().getId();
			this.evalRepo.deleteById(evaluationId);
			updateHotelAverageEvaluation(hotelId);
		}else {
			throw new OfferNotFoundException("Couldn't find evaluation with id '" + evaluationId + "'.");
		}
	}
	
	public List<EvaluationDetailsDTO> getEvaluations(Long hotelId, Map<String, String> params) throws IllegalArgumentException
	{
		if (!this.hRepo.existsById(hotelId)) throw new OfferNotFoundException("Couldn't find hotel with id '" + hotelId + "'.");
		
		for (String key : params.keySet()) {
			if (!evaluationsFilterOpts.contains(key)) {
				throw new IllegalArgumentException();
			}
		}
		
		final Integer page = params.get("page") != null ? Integer.valueOf(params.get("page")) : 0;
		Pageable pageable = PageRequest.of(page, 10);
		return this.evalRepo.findAllByHotelId(hotelId, pageable).stream().map(e -> {
			return new EvaluationDetailsDTO(e);
		}).collect(Collectors.toList());
	}
	
	// Reservations
	
	public HotelReservation createReservation(Long userId, Long hotelId, PeriodReservationDTO dto) throws IdNotFoundException
	{	
		Optional<User> userOptional = this.uRepo.findById(userId);
		Optional<Hotel> hotelOptional = this.hRepo.findById(hotelId);
		
		if (userOptional.isPresent() && hotelOptional.isPresent()) {
			User user = userOptional.get();
			Hotel hotel = hotelOptional.get();
			Optional<FacturationData> fDataOptional = this.facRepo.findById( dto.getCardId().longValue() );
			if (fDataOptional.isPresent()) {
				FacturationData fData = fDataOptional.get();
				HotelReservation reservation = new HotelReservation(
						user, 
						hotel, 
						fData.getPaymentData(), 
						new Date( dto.getBeginTime() ),
						new Date( dto.getEndTime() )
						);
				reservation = this.hResRepo.save(reservation);
				return reservation;
			}else {
				throw new IdNotFoundException("Card with id '" + dto.getCardId() + "' couldn't be found.");
			}
			
		}else {
			 throw new IdNotFoundException("User or hotel with given id couldn't be found.");
		}
	}
	
	public HotelReservation getReservation(Long reservationId) throws IdNotFoundException
	{	
		Optional<HotelReservation> hResData = this.hResRepo.findById(reservationId);
		if (hResData.isPresent()) {
			return hResData.get();
		}else {
			 throw new IdNotFoundException("Reservation with given id '" + reservationId + "' couldn't be found.");
		}
	}
	
	public List<HotelReservation> getReservations(Long userId, Integer page) throws IdNotFoundException
	{	
		if (this.uRepo.existsById(userId)) {
			Pageable pageable = PageRequest.of(page, 10);
			return this.hResRepo.findAllByUserId(userId, pageable);
		}else {
			 throw new IdNotFoundException("User with given id '" + userId + "' couldn't be found.");
		}
	}
	
}
