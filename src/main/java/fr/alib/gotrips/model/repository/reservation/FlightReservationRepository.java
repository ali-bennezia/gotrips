package fr.alib.gotrips.model.repository.reservation;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.alib.gotrips.model.entity.reservation.FlightReservation;

@Repository
public interface FlightReservationRepository extends JpaRepository<FlightReservation, Long> {
	List<FlightReservation> findAllByFlightId(Long id, Pageable pageable);
}
