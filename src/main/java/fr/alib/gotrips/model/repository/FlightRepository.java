package fr.alib.gotrips.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fr.alib.gotrips.model.entity.offers.Flight;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
	//@Query("select avg(e.note) from EVALUATION e where ")
	//float getAverageNoteById(Long id);
	
	@Query(value = "SELECT f.* FROM FLIGHT f WHERE MATCH "
			+ "(f.departure_airport, f.arrival_airport, "
			+ "f.arrival_city, f.arrival_country, f.arrival_street, f.arrival_zip_code, "
			+ "f.departure_city, f.departure_country, f.departure_street, f.departure_zip_code)"
			+ " AGAINST (:text IN NATURAL LANGUAGE MODE)", nativeQuery = true)
	List<Flight> findFullTextSearchAll(@Param("text") String text);
	
	@Query("select avg(e.note) from EVALUATION e WHERE e.flight_id = :id")
	float findAverageNoteById(@Param("id") Long id);
}
