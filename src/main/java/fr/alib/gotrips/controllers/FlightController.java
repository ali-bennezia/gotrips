package fr.alib.gotrips.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.alib.gotrips.model.dto.outbound.FlightDetailsDTO;
import fr.alib.gotrips.model.repository.FlightRepository;
import fr.alib.gotrips.service.FlightService;

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
	
}
