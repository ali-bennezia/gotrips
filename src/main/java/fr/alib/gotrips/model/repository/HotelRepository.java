package fr.alib.gotrips.model.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fr.alib.gotrips.model.entity.offers.Flight;
import fr.alib.gotrips.model.entity.offers.Hotel;


@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {

	@Query(value = "SELECT * FROM HOTEL h WHERE "
			+ "( :query IS NULL OR "
			+ "("
			+ " ( h.city LIKE CONCAT('%', :query, '%') ) OR "
			+ " ( h.country LIKE CONCAT('%', :query, '%') ) OR "
			+ " ( h.street LIKE CONCAT('%', :query, '%') ) OR "
			+ " ( h.zip_code LIKE CONCAT('%', :query, '%') ) "
			+ ")"
			+ ") AND "
			+ "("
			+ "( :cntry IS NULL OR h.country LIKE CONCAT('%', :cntry, '%') ) AND "
			+ "( :city IS NULL OR h.city LIKE CONCAT('%', :city, '%') ) AND "
			+ "( :miprc IS NULL OR h.price_per_night >= :miprc ) AND "
			+ "( :mxprc IS NULL OR h.price_per_night <= :mxprc ) AND "
			+ "( :mieval IS NULL OR h.average_evaluation >= :mieval ) AND "
			+ "( :mxeval IS NULL OR h.average_evaluation <= :mxeval )"
			+ ")"
			+ "", nativeQuery = true)
	Page<Hotel> search(
			@Param("query") String query, 
			@Param("cntry") String country,
			@Param("city") String city,
			@Param("miprc") Float minPrice,
			@Param("mxprc") Float maxPrice,
			@Param("mieval") Float minEval,
			@Param("mxeval") Float maxEval,
			Pageable pageeable
	);
	
	List<Hotel> findAllByHotelCompanyId(Long hotelCompanyId);
	
	@Query("select avg(e.note) from Evaluation e WHERE e.hotel.id = :id")
	Double findAverageNoteById(@Param("id") Long id);
}
