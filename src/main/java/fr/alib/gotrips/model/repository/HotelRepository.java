package fr.alib.gotrips.model.repository;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fr.alib.gotrips.model.entity.offers.Hotel;


@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
	@Query(value = "SELECT f.* FROM HOTEL f WHERE MATCH "
			+ "(f.departure_airport, f.arrival_airport, "
			+ "f.arrival_city, f.arrival_country, f.arrival_street, f.arrival_zip_code, "
			+ "f.departure_city, f.departure_country, f.departure_street, f.departure_zip_code)"
			+ " AGAINST (:text IN NATURAL LANGUAGE MODE) "
			+ "WHERE "
			+ "( :ocntry IS NULL OR f.departure_country = :ocntry ) AND "
			+ "", nativeQuery = true)
	Page<Hotel> findFullTextSearchAll(
			@Param("text") String text, 
			@Param("ocntry") String originCountry,
			Pageable pageeable
	);
	
	@Query("select avg(e.note) from Evaluation e WHERE e.hotel.id = :id")
	Double findAverageNoteById(@Param("id") Long id);
}
