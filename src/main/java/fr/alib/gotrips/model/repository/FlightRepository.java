package fr.alib.gotrips.model.repository;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fr.alib.gotrips.model.entity.offers.Flight;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
	@Query(value = "SELECT f.* FROM FLIGHT f WHERE MATCH "
			+ "(f.departure_airport, f.arrival_airport, "
			+ "f.arrival_city, f.arrival_country, f.arrival_street, f.arrival_zip_code, "
			+ "f.departure_city, f.departure_country, f.departure_street, f.departure_zip_code)"
			+ " AGAINST (:text IN NATURAL LANGUAGE MODE) "
			+ "WHERE "
			+ "( :ocntry IS NULL OR f.departure_country = :ocntry ) AND "
			+ "( :dcntry IS NULL OR f.arrival_country = :dcntry ) AND "
			+ "( :miprc IS NULL OR f.price >= :miprc ) AND "
			+ "( :mxprc IS NULL OR f.price <= :mxprc ) AND "
			+ "( :midate IS NULL OR f.departure_date = :midate ) AND "
			+ "( :mxdate IS NULL OR f.landing_date = :mxdate ) AND "
			+ "( :mieval IS NULL OR f.average_evalution >= :mieval ) AND "
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
	
	@Query("select avg(e.note) from Evaluation e WHERE e.flight.id = :id")
	Double findAverageNoteById(@Param("id") Long id);
}
