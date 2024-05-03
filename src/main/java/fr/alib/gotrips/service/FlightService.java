package fr.alib.gotrips.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
import fr.alib.gotrips.model.dto.outbound.FlightDetailsDTO;
import fr.alib.gotrips.model.entity.company.FlightCompany;
import fr.alib.gotrips.model.entity.offers.Evaluation;
import fr.alib.gotrips.model.entity.offers.Flight;
import fr.alib.gotrips.model.entity.user.User;
import fr.alib.gotrips.model.repository.EvaluationRepository;
import fr.alib.gotrips.model.repository.FlightRepository;
import io.jsonwebtoken.lang.Arrays;

@Service
@Transactional
public class FlightService {

	@Autowired
	private FlightRepository fRepo;
	
	@Autowired
	private EvaluationRepository evalRepo;
	
	@Autowired
	private UserService uService;
	
	private final String[] filterOptions = new String[] {
			"qry", "page", "ocntry", "dcntry", "miprc", "mxprc", "midate", "mxdate", "mieval", "mxeval", "srtby", "srtordr", "dates"
	};
	private final String[] sortingOptions = new String[] {
			"price", "average_evaluation", "seats", "arrival_city", "arrival_country", "departure_city", "departure_country"
	};
	
	private final List<String> filterOpts = Arrays.asList(filterOptions);
	private final List<String> sortOpts = Arrays.asList(sortingOptions);
	
	public List<FlightDetailsDTO> getFlights(Map<String, String> params) throws IllegalArgumentException
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
			
			Page<Flight> result = this.fRepo.findFullTextSearchAll(
					params.get("qry"), 
					params.get("ocntry"), 
					params.get("dcntry"), 
					Float.valueOf( params.get("miprc") ), 
					Float.valueOf( params.get("mxprc") ), 
					new Date( Long.valueOf(params.get("midate")) ), 
					new Date( Long.valueOf(params.get("mxdate")) ), 
					Float.valueOf( params.get("mieval") ), 
					Float.valueOf( params.get("mxeval") ), 
					pageable
					);
			
			List<FlightDetailsDTO> flightDetails = result.toList().stream().map((f)->{
				return new FlightDetailsDTO(f);
			}).collect(Collectors.toList());
			
			return flightDetails;
		} catch (Exception e) {
			return new ArrayList<FlightDetailsDTO>();
		}
		
	}
	
	@Transactional(rollbackFor = Exception.class)
	public Flight createFlight(Long userId, FlightDTO dto) throws IdNotFoundException, CompanyNotFoundException
	{
		CustomUserDetails userDetails = (CustomUserDetails) this.uService.loadUserById(userId);
		User user = userDetails.getUser();
		FlightCompany company = user.getFlightCompany();
		if (company == null) throw new CompanyNotFoundException("Couldn't find company for user id '" + userId + "'.");
		Flight flight = new Flight(dto);
		flight.setFlightCompany(company);
		flight = this.fRepo.save(flight);
		return flight;
	}
	
	public Flight getFlight(Long flightId) throws OfferNotFoundException
	{
		Optional<Flight> result = this.fRepo.findById(flightId);
		
		if (result.isPresent()) {
			return result.get();
		}else {
			throw new OfferNotFoundException("Couldn't find flight with id '" + flightId + "'.");
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public Flight updateFlight(Long flightId, FlightDTO dto)  throws OfferNotFoundException
	{
		Optional<Flight> result = this.fRepo.findById(flightId);
		
		if (result.isPresent()) {
			Flight flight = result.get();
			flight.applyDTO(dto);
			return this.fRepo.save(flight);
		}else {
			throw new OfferNotFoundException("Couldn't find flight with id '" + flightId + "'.");
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void deleteFlight(Long flightId)
	{
		this.fRepo.deleteById(flightId);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public Flight updateFlightAverageEvaluation(Long flightId) throws OfferNotFoundException
	{
		Optional<Flight> flightResult = this.fRepo.findById(flightId);
		if (flightResult.isPresent()) {
			Flight flight = flightResult.get();
			flight.setAverageEvaluation(new BigDecimal(Double.toString( fRepo.findAverageNoteById(flightId) )));
			return this.fRepo.save(flight);
		}else {
			throw new OfferNotFoundException("Couldn't find flight with id '" + flightId + "'.");
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void addEvaluation(Long userId, Long flightId, EvaluationDTO dto) throws IdNotFoundException, OfferNotFoundException
	{
		CustomUserDetails userDetails = (CustomUserDetails) this.uService.loadUserById(userId);
		Optional<Flight> flightResult = this.fRepo.findById(flightId);
		if (flightResult.isPresent()) {
			Flight flight = flightResult.get();
			Evaluation eval = new Evaluation(dto);
			eval.setUser(userDetails.getUser());
			eval.setFlight(flightResult.get());
			flight.getEvaluations().add(eval);
			flight = this.fRepo.save(flight);
			fRepo.save(flight);
			updateFlightAverageEvaluation(flight.getId());
		}else {
			throw new OfferNotFoundException("Couldn't find flight with id '" + flightId + "'.");
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void removeEvaluation(Long evaluationId) throws OfferNotFoundException
	{
		Optional<Evaluation> ev = this.evalRepo.findById(evaluationId);
		if (ev.isPresent()) {
			Long flightId = ev.get().getFlight().getId();
			this.evalRepo.deleteById(evaluationId);
			updateFlightAverageEvaluation(flightId);
		}else {
			throw new OfferNotFoundException("Couldn't find evaluation with id '" + evaluationId + "'.");
		}
	}
	
}
