package fr.alib.gotrips.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.alib.gotrips.model.entity.reservation.FlightReservation;

@Repository
public interface FlightReservationRepository extends JpaRepository<FlightReservation, Long> {
}
