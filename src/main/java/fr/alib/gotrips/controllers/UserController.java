package fr.alib.gotrips.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.alib.gotrips.model.auth.UserService;
import fr.alib.gotrips.model.dto.inbound.UserLoginDTO;
import fr.alib.gotrips.model.dto.inbound.UserRegisterDTO;
import fr.alib.gotrips.model.dto.outbound.AuthenticationSessionDTO;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private PasswordEncoder pwdEncoder;
	
	@Autowired
	private UserService uService;
	
	@PostMapping("/signin")
	public ResponseEntity<AuthenticationSessionDTO> signin(@Valid @RequestBody UserLoginDTO dto) {
		AuthenticationSessionDTO sess = this.uService.login(dto, pwdEncoder);
		if (sess != null) {
			return ResponseEntity.ok(sess);
		}else {
			return ResponseEntity.status(HttpStatusCode.valueOf(403)).body(null);
		}
	}
	
	// TODO: Change created location
	@PostMapping("/register")
	public ResponseEntity<String> register(@Valid @RequestBody UserRegisterDTO dto)
	{
		UserDetails user = uService.register(dto, pwdEncoder);
		if (user != null) {
			return ResponseEntity.created(null).body("Created");
		}else {
			return ResponseEntity.status(HttpStatusCode.valueOf(409)).body("Conflict");
		}
	}
	
}
