package fr.alib.gotrips.model.repository.reservation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityReservationRepository extends JpaRepository<ActivityReservationRepository, Long> {

}
