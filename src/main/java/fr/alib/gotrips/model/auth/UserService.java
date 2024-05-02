package fr.alib.gotrips.model.auth;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.alib.gotrips.model.dto.UserLoginDTO;
import fr.alib.gotrips.model.dto.UserRegisterDTO;
import fr.alib.gotrips.model.entity.company.ActivityCompany;
import fr.alib.gotrips.model.entity.company.FlightCompany;
import fr.alib.gotrips.model.entity.company.HotelCompany;
import fr.alib.gotrips.model.entity.user.User;
import fr.alib.gotrips.model.repository.UserRepository;
import fr.alib.gotrips.model.repository.company.ActivityCompanyRepository;
import fr.alib.gotrips.model.repository.company.FlightCompanyRepository;
import fr.alib.gotrips.model.repository.company.HotelCompanyRepository;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private FlightCompanyRepository fcRepo;
	@Autowired
	private HotelCompanyRepository hcRepo;
	@Autowired
	private ActivityCompanyRepository acRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> result = this.userRepo.findUserByUsernameOrEmail(username, null);
		if (result.isEmpty()) throw new UsernameNotFoundException("Couldn't find user with username '" + username + "'.");
		return new CustomUserDetails(result.get());
	}
	
	public String login(UserLoginDTO dto, PasswordEncoder pwdEncoder)
	{
		Optional<User> usr = this.userRepo.findUserByUsernameOrEmail(null, dto.getEmail());
		return (usr.isPresent() && pwdEncoder.matches(dto.getPassword(), usr.get().getPassword())) ? usr.get().getUsername() : null;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public UserDetails register(UserRegisterDTO dto, PasswordEncoder pwdEncoder)
	{
		if (userRepo.findUserByUsernameOrEmail(dto.getUsername(), dto.getEmail()).isPresent()) {
			return null;
		}
		
		User user = null;

		user = new User(dto, pwdEncoder, null, null, null, "USER");
		user = userRepo.save(user);
		
		FlightCompany fCompany = dto.getFlightCompany() != null ? new FlightCompany(dto.getFlightCompany(), user) : null;
		HotelCompany hCompany = dto.getHotelCompany() != null ? new HotelCompany(dto.getHotelCompany(), user) : null;
		ActivityCompany aCompany = dto.getActivityCompany() != null ? new ActivityCompany(dto.getActivityCompany(), user) : null;
		
		fCompany = fcRepo.save(fCompany);
		hCompany = hcRepo.save(hCompany);
		aCompany = acRepo.save(aCompany);
		
		user.setFlightCompany(fCompany);
		user.setHotelCompany(hCompany);
		user.setActivityCompany(aCompany);
		
		user = userRepo.save(user);

		return new CustomUserDetails(user);
	}
}
