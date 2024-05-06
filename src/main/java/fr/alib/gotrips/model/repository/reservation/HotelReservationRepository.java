package fr.alib.gotrips.model.repository.reservation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.alib.gotrips.model.entity.reservation.HotelReservation;

@Repository
public interface HotelReservationRepository extends JpaRepository<HotelReservation, Long> {
}
