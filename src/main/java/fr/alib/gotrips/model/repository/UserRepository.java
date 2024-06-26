package fr.alib.gotrips.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.alib.gotrips.model.entity.user.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findUserByUsernameOrEmail(String username, String email);
}
