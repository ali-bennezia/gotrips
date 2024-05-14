package fr.alib.gotrips.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.alib.gotrips.exception.IdNotFoundException;
import fr.alib.gotrips.exception.OfferNotFoundException;
import fr.alib.gotrips.model.auth.CustomUserDetails;
import fr.alib.gotrips.model.dto.inbound.PaymentDataDTO;
import fr.alib.gotrips.model.dto.inbound.UserLoginDTO;
import fr.alib.gotrips.model.dto.inbound.UserRegisterDTO;
import fr.alib.gotrips.model.dto.outbound.AuthenticationSessionDTO;
import fr.alib.gotrips.model.dto.outbound.UserDetailsDTO;
import fr.alib.gotrips.model.entity.user.User;
import fr.alib.gotrips.model.repository.UserRepository;
import fr.alib.gotrips.service.UserService;
import fr.alib.gotrips.utils.JWTUtils;
import jakarta.servlet.http.HttpServletRequest;
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
	
	@Autowired
	private JWTUtils jwtUtils;
	
	@GetMapping("/authentify")
	public ResponseEntity<?> authentify(
			HttpServletRequest request
			)
	{
		String token = request.getHeader("Authorization");
		if (token != null && !token.isBlank()) {
			token = token.replace("Bearer ", "");
			String username = jwtUtils.extractUsername(token);
			if (username != null) {
				try {
					UserDetails usr = this.uService.loadUserByUsername(username);
					if (usr.isEnabled() && usr.isCredentialsNonExpired() && usr.isAccountNonLocked() && usr.isAccountNonExpired()) {
						return ResponseEntity.ok().build();
					}
				} catch (UsernameNotFoundException ex) {
				}
			}
		}	
		return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	}
	
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
		UserDetails user = uService.register(dto, pwdEncoder, false);
		if (user != null) {
			return ResponseEntity.created(null).body("Created");
		}else {
			return ResponseEntity.status(HttpStatusCode.valueOf(409)).body("Conflict");
		}
	}
	
	@GetMapping("/{id}/details")
	public ResponseEntity<UserDetailsDTO> details(@PathVariable("id") Long id)
	{
		Optional<User> user = this.uRepo.findById(id);
		if (user.isPresent()) {
			return ResponseEntity.ok(new UserDetailsDTO(user.get()));
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping("/{id}/delete")
	public ResponseEntity<String> delete(HttpServletRequest request, @PathVariable("id") Long id)
	{
		String token = request.getHeader("Authorization");
		if (token != null && !token.isBlank()) {
			token = token.replace("Bearer ", "");
			String currentUsername = this.jwtUtils.extractUsername(token);
			try {
				CustomUserDetails targetUser = (CustomUserDetails) this.uService.loadUserById(id);
				if ( request.isUserInRole("ROLE_ADMIN") || targetUser.getUsername().equals(currentUsername) ) {
					this.uService.disableUser(id);
					return ResponseEntity.status(HttpStatusCode.valueOf(204)).body("No Content");
				}else {
					return ResponseEntity.status(HttpStatusCode.valueOf(403)).body("Forbidden");
				}
			}catch(IdNotFoundException ex) {
				return ResponseEntity.status(HttpStatusCode.valueOf(400)).body("Bad Request");
			}
		}else {
			return ResponseEntity.status(HttpStatusCode.valueOf(401)).body("Unauthorized");
		}
	}
	
	@PostMapping("/{id}/card/add")
	public ResponseEntity<?> addCard(@PathVariable("id") Long id, @Valid @RequestBody PaymentDataDTO dto, HttpServletRequest request)
	{
		String token = request.getHeader("Authorization");
		if (token != null && !token.isBlank()) {
			token = token.replace("Bearer ", "");
			String currentUsername = this.jwtUtils.extractUsername(token);
			try {
				CustomUserDetails targetUser = (CustomUserDetails) this.uService.loadUserById(id);
				if ( targetUser.getUsername().equals(currentUsername) ) {
					this.uService.addFacturationData(id, dto);
					return ResponseEntity.created(null).build();
				}else {
					return ResponseEntity.status(HttpStatusCode.valueOf(403)).body("Forbidden");
				}
			}catch(IdNotFoundException ex) {
				return ResponseEntity.status(HttpStatusCode.valueOf(400)).body("Bad Request");
			}
		}else {
			return ResponseEntity.status(HttpStatusCode.valueOf(401)).body("Unauthorized");
		}
	}
	
	@GetMapping("/{id}/card/getAll")
	public ResponseEntity<?> getAllCards(@PathVariable("id") Long id, HttpServletRequest request)
	{

		String token = request.getHeader("Authorization");
		if (token != null && !token.isBlank()) {
			token = token.replace("Bearer ", "");
			String currentUsername = this.jwtUtils.extractUsername(token);
			try {
				CustomUserDetails targetUser = (CustomUserDetails) this.uService.loadUserById(id);
				if ( targetUser.getUsername().equals(currentUsername) ) {
					return ResponseEntity.ok(this.uService.getFacturationDataList(id));
				}else {
					return ResponseEntity.status(HttpStatusCode.valueOf(403)).body("Forbidden");
				}
			}catch(IdNotFoundException ex) {
				return ResponseEntity.status(HttpStatusCode.valueOf(400)).body("Bad Request");
			}
		}else {
			return ResponseEntity.status(HttpStatusCode.valueOf(401)).body("Unauthorized");
		}


	}
	
	@PutMapping("/{id}/card/{cardId}/edit")
	public ResponseEntity<?> editCard(
			@PathVariable("id") Long id, 
			@PathVariable("cardId") Long cardId,
			@Valid @RequestBody PaymentDataDTO dto, 
			HttpServletRequest request
			)
	{
		String token = request.getHeader("Authorization");
		if (token != null && !token.isBlank()) {
			token = token.replace("Bearer ", "");
			String currentUsername = this.jwtUtils.extractUsername(token);
			try {
				CustomUserDetails targetUser = (CustomUserDetails) this.uService.loadUserById(id);
				if ( targetUser.getUsername().equals(currentUsername) ) {
					this.uService.editFacturationData(cardId, dto);
					return ResponseEntity.ok().build();
				}else {
					return ResponseEntity.status(HttpStatusCode.valueOf(403)).body("Forbidden");
				}
			}catch(IdNotFoundException|OfferNotFoundException ex) {
				return ResponseEntity.status(HttpStatusCode.valueOf(400)).body("Bad Request");
			}
		}else {
			return ResponseEntity.status(HttpStatusCode.valueOf(401)).body("Unauthorized");
		}
	}
	
	@DeleteMapping("/{id}/card/{cardId}/delete")
	public ResponseEntity<?> deleteCard(
			@PathVariable("id") Long id,
			@PathVariable("cardId") Long cardId,
			HttpServletRequest request
			)
	{
		String token = request.getHeader("Authorization");
		if (token != null && !token.isBlank()) {
			token = token.replace("Bearer ", "");
			String currentUsername = this.jwtUtils.extractUsername(token);
			try {
				CustomUserDetails targetUser = (CustomUserDetails) this.uService.loadUserById(id);
				if ( targetUser.getUsername().equals(currentUsername) ) {
					this.uService.deletePaymentData(cardId);
					return ResponseEntity.noContent().build();
				}else {
					return ResponseEntity.status(HttpStatusCode.valueOf(403)).body("Forbidden");
				}
			}catch(IdNotFoundException|OfferNotFoundException ex) {
				return ResponseEntity.status(HttpStatusCode.valueOf(400)).body("Bad Request");
			}
		}else {
			return ResponseEntity.status(HttpStatusCode.valueOf(401)).body("Unauthorized");
		}	}
}
