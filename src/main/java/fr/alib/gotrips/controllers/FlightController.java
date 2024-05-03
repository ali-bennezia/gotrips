package fr.alib.gotrips.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.alib.gotrips.model.repository.FlightRepository;

@RestController
@RequestMapping("/api/flight")
public class FlightController {

	/*@Autowired
	private FlightRepository fRepo;
	
	public ResponseEntity<String> search(@RequestParam Map<String, String> params)
	{
		int page = params.get("page") != null ? Integer.parseInt(params.get("page")) : 0;
		return ResponseEntity.ok().build();
	}*/
	
}
