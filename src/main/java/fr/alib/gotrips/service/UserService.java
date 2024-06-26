package fr.alib.gotrips.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.alib.gotrips.exception.IdNotFoundException;
import fr.alib.gotrips.model.auth.CustomUserDetails;
import fr.alib.gotrips.model.dto.inbound.PaymentDataDTO;
import fr.alib.gotrips.model.dto.inbound.UserLoginDTO;
import fr.alib.gotrips.model.dto.inbound.UserRegisterDTO;
import fr.alib.gotrips.model.dto.outbound.AuthenticationSessionDTO;
import fr.alib.gotrips.model.dto.outbound.CardDetailsDTO;
import fr.alib.gotrips.model.dto.outbound.CompanyDetailsDTO;
import fr.alib.gotrips.model.entity.PaymentData;
import fr.alib.gotrips.model.entity.company.ActivityCompany;
import fr.alib.gotrips.model.entity.company.FlightCompany;
import fr.alib.gotrips.model.entity.company.HotelCompany;
import fr.alib.gotrips.model.entity.user.FacturationData;
import fr.alib.gotrips.model.entity.user.User;
import fr.alib.gotrips.model.repository.FacturationDataRepository;
import fr.alib.gotrips.model.repository.UserRepository;
import fr.alib.gotrips.model.repository.company.ActivityCompanyRepository;
import fr.alib.gotrips.model.repository.company.FlightCompanyRepository;
import fr.alib.gotrips.model.repository.company.HotelCompanyRepository;
import fr.alib.gotrips.utils.JWTUtils;
import io.jsonwebtoken.lang.Arrays;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private FlightCompanyRepository fcRepo;
	@Autowired
	private HotelCompanyRepository hcRepo;
	@Autowired
	private ActivityCompanyRepository acRepo;
	@Autowired
	private FacturationDataRepository factRepo;
	
	@Autowired
	private TextEncryptor encryptor;
	
	
	@Autowired
	private JWTUtils jwtUtils;
	
	@Autowired
	private UserRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> result = this.userRepo.findUserByUsernameOrEmail(username, null);
		if (result.isEmpty()) throw new UsernameNotFoundException("Couldn't find user with username '" + username + "'.");
		return new CustomUserDetails(result.get());
	}
	
	public UserDetails loadUserById(Long id) throws IdNotFoundException
	{
		Optional<User> result = this.userRepo.findById(id);
		if (result.isPresent()) {
			return new CustomUserDetails( result.get() );
		}else {
			throw new IdNotFoundException("Couldn't find user with id '" + id + "'.");
		}
	}
	
	/**
	 * Checks user credentials.
	 * @return An AuthenticationSessionDTO if successful, null otherwise.
	 */
	public AuthenticationSessionDTO login(UserLoginDTO dto, PasswordEncoder pwdEncoder)
	{
		Optional<User> usr = this.userRepo.findUserByUsernameOrEmail(null, dto.getEmail());
		return (
				usr.isPresent() 
				&& pwdEncoder.matches(dto.getPassword(), usr.get().getPassword()) 
				&& usr.get().isEnabled()
				) ? 
				new AuthenticationSessionDTO(
						this.jwtUtils.generateToken(usr.get().getUsername()),
						usr.get().getUsername(),
						usr.get().getId().toString(),
						usr.get().getEmail(),
						Arrays.asList(usr.get().getRoles().split(", ")),
						usr.get().getFlightCompany() != null ? new CompanyDetailsDTO(usr.get().getFlightCompany()) : null,
						usr.get().getHotelCompany() != null ? new CompanyDetailsDTO(usr.get().getHotelCompany()) : null,	
						usr.get().getActivityCompany() != null ? new CompanyDetailsDTO(usr.get().getActivityCompany()) : null
				) : null;
	}
	
	/**
	 * Registers a new user.
	 * @return A UserDetails if successful, null otherwise.
	 */
	@Transactional(rollbackFor = Exception.class)
	public UserDetails register(UserRegisterDTO dto, PasswordEncoder pwdEncoder, boolean isAdmin)
	{
		if (userRepo.findUserByUsernameOrEmail(dto.getUsername(), dto.getEmail()).isPresent()) {
			return null;
		}
		
		User user = null;
		List<String> roles = new ArrayList<String>();
		roles.add("ROLE_USER");
		if (isAdmin) roles.add("ROLE_ADMIN");

		user = new User(dto, pwdEncoder, null, null, null, String.join(", ", roles));
		user = userRepo.save(user);
		
		FlightCompany fCompany = dto.getFlightCompany() != null ? new FlightCompany(dto.getFlightCompany(), user) : null;
		HotelCompany hCompany = dto.getHotelCompany() != null ? new HotelCompany(dto.getHotelCompany(), user) : null;
		ActivityCompany aCompany = dto.getActivityCompany() != null ? new ActivityCompany(dto.getActivityCompany(), user) : null;
		
		if (fCompany != null){
			fCompany = fcRepo.save(fCompany);
			user.setFlightCompany(fCompany);
			roles.add("ROLE_FLIGHT_COMPANY");
		}
		if (hCompany != null) { 
			hCompany = hcRepo.save(hCompany);
			user.setHotelCompany(hCompany);
			roles.add("ROLE_HOTEL_COMPANY");
		}
		if (aCompany != null) { 
			aCompany = acRepo.save(aCompany);
			user.setActivityCompany(aCompany);
			roles.add("ROLE_ACTIVITY_COMPANY");
		}
		
		user.setRoles(String.join(", ", roles));
		user = userRepo.save(user);

		return new CustomUserDetails(user);
	}
	
	/**
	 * Disables a user.
	 * @return true if the operation was successful, false otherwise.
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean disableUser(Long id)
	{
		Optional<User> userResult = this.userRepo.findById(id);
		if (userResult.isPresent()) {
			User user = userResult.get();
			user.setEnabled(false);
			userRepo.save(user);
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * Adds a payment method.
	 * @returns The newly created entity.
	 */
	@Transactional(rollbackFor = Exception.class)
	public FacturationData addFacturationData(Long userId, PaymentDataDTO dto) throws IdNotFoundException
	{
		Optional<User> userResult = this.userRepo.findById(userId);
		if (userResult.isPresent()) {
			FacturationData data = new FacturationData( dto, encryptor );
			data.setUser(userResult.get());
			User user = userResult.get();
			user.getCards().add(data);
			user = this.userRepo.save(user);
			return data;
		}else {
			throw new IdNotFoundException("Couldn't find user with id '" + userId + "'.");
		}
	}
	
	/**
	 * Fetches payment data details.
	 * @returns The details DTO.
	 */
	public CardDetailsDTO getFacturationData(Long facturationDataId) throws IdNotFoundException
	{
		Optional<FacturationData> fDataOptional = this.factRepo.findById(facturationDataId);
		if (fDataOptional.isPresent()) {
			return new CardDetailsDTO( fDataOptional.get(), encryptor );
		}else {
			throw new IdNotFoundException("Couldn't find facturation data with id '" + facturationDataId + "'.");
		}
	}
	
	/**
	 * Fetches a user's payment data.
	 * @returns A list of the user's payment data.
	 */
	public List<CardDetailsDTO> getFacturationDataList(Long userId) throws IdNotFoundException
	{
		Optional<User> userResult = this.userRepo.findById(userId);
		if (userResult.isPresent()) {
			User user = userResult.get();
			return this.factRepo.findAllByUserId(userId).stream().map(f -> {
				return new CardDetailsDTO(f, encryptor);
			}).collect(Collectors.toList());
		}else {
			throw new IdNotFoundException("Couldn't find user with id '" + userId + "'.");
		}
	}
	
	/**
	 * Edits a payment method.
	 * @return the entity if successful, null otherwise.
	 */
	@Transactional(rollbackFor = Exception.class)
	public FacturationData editFacturationData(Long facturationDataId, PaymentDataDTO dto)
	{
		if (this.factRepo.existsById(facturationDataId)) {
			Optional<FacturationData> data = this.factRepo.findById(facturationDataId);
			if (data.isPresent()) {
				FacturationData fData = data.get();
				fData.applyDTO(dto, encryptor);
				return this.factRepo.save(fData);
			}
		}
		return null;
	}
	
	/**
	 * Deletes a payment method.
	 * @return true if the operation was successful, false otherwise.
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean deletePaymentData(Long facturationDataId)
	{
		if (this.factRepo.existsById(facturationDataId)) {
			this.factRepo.deleteById(facturationDataId);
			return true;
		}
		return false;
	}
	
}
