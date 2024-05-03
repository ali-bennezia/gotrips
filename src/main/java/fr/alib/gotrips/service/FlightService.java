package fr.alib.gotrips.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.alib.gotrips.exception.CompanyNotFoundException;
import fr.alib.gotrips.exception.IdNotFoundException;
import fr.alib.gotrips.exception.OfferNotFoundException;
import fr.alib.gotrips.model.auth.CustomUserDetails;
import fr.alib.gotrips.model.dto.inbound.EvaluationDTO;
import fr.alib.gotrips.model.dto.inbound.FlightDTO;
import fr.alib.gotrips.model.entity.company.FlightCompany;
import fr.alib.gotrips.model.entity.offers.Evaluation;
import fr.alib.gotrips.model.entity.offers.Flight;
import fr.alib.gotrips.model.entity.user.User;
import fr.alib.gotrips.model.repository.EvaluationRepository;
import fr.alib.gotrips.model.repository.FlightRepository;

@Service
@Transactional
public class FlightService {

	@Autowired
	private FlightRepository fRepo;
	
	@Autowired
	private EvaluationRepository evalRepo;
	
	@Autowired
	private UserService uService;
	
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
			
			flight.setAverageEvaluation(new BigDecimal(Float.toString( fRepo.findAverageNoteById(flightId) )));
			fRepo.save(flight);
			
		}else {
			throw new OfferNotFoundException("Couldn't find flight with flight id '" + flightId + "'.");
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void removeEvaluation(Long evaluationId)
	{
		
	}
	
}
