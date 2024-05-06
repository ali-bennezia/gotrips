package fr.alib.gotrips.model.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.alib.gotrips.model.entity.offers.Evaluation;

@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Long>{
	List<Evaluation> findAllByFlightId(Long flightId, Pageable pageable);
	List<Evaluation> findAllByActivityId(Long activityId, Pageable pageable);
	List<Evaluation> findAllByHotelId(Long hotelId, Pageable pageable);
}
