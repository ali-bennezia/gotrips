package fr.alib.gotrips.model.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fr.alib.gotrips.model.entity.offers.Flight;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
	@Query(value = "SELECT f.* FROM FLIGHT f "
			+ "WHERE "
			+ "( :text IS NULL OR "
			+ "( f.departure_airport LIKE '%:text%' AND "
			+ "f.arrival_airport LIKE '%:text%' AND "
			+ "f.arrival_city LIKE '%:text%' AND "
			+ "f.arrival_country LIKE '%:text%' AND "
			+ "f.arrival_street LIKE '%:text%' AND "
			+ "f.arrival_zip_code LIKE '%:text%' AND "
			+ "f.departure_city LIKE '%:text%' AND "
			+ "f.departure_country LIKE '%:text%' AND "
			+ "f.departure_street LIKE '%:text%' AND "
			+ "f.departure_zip_code LIKE '%:text%' ) ) AND "
			+ "( :ocntry IS NULL OR f.departure_country = :ocntry ) AND "
			+ "( :dcntry IS NULL OR f.arrival_country = :dcntry ) AND "
			+ "( :miprc IS NULL OR f.price >= :miprc ) AND "
			+ "( :mxprc IS NULL OR f.price <= :mxprc ) AND "
			+ "( :midate IS NULL OR f.departure_date = :midate ) AND "
			+ "( :mxdate IS NULL OR f.landing_date = :mxdate ) AND "
			+ "( :mieval IS NULL OR f.average_evaluation >= :mieval ) AND "
			+ "( :mxeval IS NULL OR f.average_evaluation <= :mxeval )"
			+ "", nativeQuery = true)
	Page<Flight> findFullTextSearchAll(
			@Param("text") String text, 
			@Param("ocntry") String originCountry,
			@Param("dcntry") String destinationCountry,
			@Param("miprc") Float minPrice,
			@Param("mxprc") Float maxPrice,
			@Param("midate") Date minDate,
			@Param("mxdate") Date maxDate,
			@Param("mieval") Float minEvaluation,
			@Param("mxeval") Float maxEvaluation,
			Pageable pageeable
	);
	
	List<Flight> findAllByDepartureDateBetween(Date minDate, Date maxDate);
	List<Flight> findAllByLandingDateBetween(Date minDate, Date maxDate);
	List<Flight> findAllByFlightCompanyId(Long flightCompanyId);
	
	@Query("select avg(e.note) from Evaluation e WHERE e.flight.id = :id")
	Double findAverageNoteById(@Param("id") Long id);
}
