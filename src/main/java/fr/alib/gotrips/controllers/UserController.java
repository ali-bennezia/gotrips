package fr.alib.gotrips.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.alib.gotrips.model.auth.UserService;
import fr.alib.gotrips.model.dto.inbound.UserLoginDTO;
import fr.alib.gotrips.model.dto.inbound.UserRegisterDTO;
import fr.alib.gotrips.model.dto.outbound.AuthenticationSessionDTO;
import fr.alib.gotrips.model.dto.outbound.UserDetailsDTO;
import fr.alib.gotrips.model.entity.user.User;
import fr.alib.gotrips.model.repository.UserRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private PasswordEncoder pwdEncoder;
	
	@Autowired
	private UserService uService;
	
	@Autowired
	private UserRepository uRepo;
	
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
	
	@GetMapping("/details/{id}")
	public ResponseEntity<UserDetailsDTO> details(@PathVariable("id") Long id)
	{
		Optional<User> user = this.uRepo.findById(id);
		if (user.isPresent()) {
			return ResponseEntity.ok(new UserDetailsDTO(user.get()));
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable("id") Long id)
	{
		return ResponseEntity.ok("");
	}
}
