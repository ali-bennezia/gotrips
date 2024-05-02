package fr.alib.gotrips.model.auth;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fr.alib.gotrips.model.entity.user.User;
import fr.alib.gotrips.model.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> result = this.userRepo.findUserByUsernameOrEmail(username, null);
		if (result.isEmpty()) throw new UsernameNotFoundException("Couldn't find user with username '" + username + "'.");
		return new CustomUserDetails(result.get());
	}

}
