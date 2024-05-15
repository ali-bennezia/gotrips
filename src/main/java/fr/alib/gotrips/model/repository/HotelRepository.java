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
	@Query(value = "SELECT f.* FROM HOTEL f WHERE MATCH "
			+ "(f.city, f.country, f.street, f.zip_code)"
			+ " AGAINST (:text IN NATURAL LANGUAGE MODE) "
			+ "WHERE "
			+ "( :cntry IS NULL OR f.country = :cntry ) AND "
			+ "( :city IS NULL OR f.city = :city ) AND "
			+ "( :miprc IS NULL OR f.price_per_night >= :miprc ) AND "
			+ "( :mxprc IS NULL OR f.price_per_night <= :mxprc ) AND "
			+ "( :mieval IS NULL OR f.average_evaluation >= :mieval ) AND "
			+ "( :mxeval IS NULL OR f.average_evaluation <= :mxeval )"
			+ "", nativeQuery = true)
	Page<Hotel> findFullTextSearchAll(
			@Param("text") String text, 
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
