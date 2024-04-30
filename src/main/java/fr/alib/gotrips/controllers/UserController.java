package fr.alib.gotrips.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.alib.gotrips.model.dto.UserLoginDTO;

@RestController
@RequestMapping("/user")
public class UserController {

	@PostMapping("/signin")
	public ResponseEntity<String> signin(@RequestBody UserLoginDTO request) {
		return ResponseEntity.ok("");
	}
	
}
