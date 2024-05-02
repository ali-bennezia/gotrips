package fr.alib.gotrips.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.alib.gotrips.model.auth.UserService;
import fr.alib.gotrips.model.dto.UserLoginDTO;
import fr.alib.gotrips.model.dto.UserRegisterDTO;
import fr.alib.gotrips.utils.JWTUtils;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private PasswordEncoder pwdEncoder;
	
	@Autowired
	private JWTUtils jwtUtils;
	
	@Autowired
	private UserService uService;
	
	@PostMapping("/signin")
	public ResponseEntity<String> signin(@Valid @RequestBody UserLoginDTO dto) {
		String username = this.uService.login(dto, pwdEncoder);
		if (username != null) {
			return ResponseEntity.ok(this.jwtUtils.generateToken(username));
		}else {
			return ResponseEntity.status(HttpStatusCode.valueOf(403)).body("Forbidden");
		}
	}
	
	@PostMapping("/register")
	public ResponseEntity<String> register(@Valid @RequestBody UserRegisterDTO dto)
	{
		return ResponseEntity.ok("Hello, World!");
	}
	
}
