package fr.alib.gotrips.model.repository.reservation;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.alib.gotrips.model.entity.reservation.HotelReservation;

@Repository
public interface HotelReservationRepository extends JpaRepository<HotelReservation, Long> {
	List<HotelReservation> findAllByUserId(Long id, Pageable pageable);
	long countByBeginDateLessThanEqualAndEndDateGreaterThanEqual(Date beginDate, Date endDate);
}
