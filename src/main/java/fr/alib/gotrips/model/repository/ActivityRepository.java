package fr.alib.gotrips.model.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fr.alib.gotrips.model.entity.offers.Activity;
import fr.alib.gotrips.model.entity.offers.Flight;
import fr.alib.gotrips.model.entity.offers.Hotel;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {

	@Query(value = "SELECT * FROM ACTIVITY a WHERE "
			+ "( :query IS NULL OR "
			+ "("
			+ " ( a.city LIKE CONCAT('%', :query, '%') ) OR "
			+ " ( a.country LIKE CONCAT('%', :query, '%') ) OR "
			+ " ( a.street LIKE CONCAT('%', :query, '%') ) OR "
			+ " ( a.zip_code LIKE CONCAT('%', :query, '%') ) OR "
			+ " ( a.description LIKE CONCAT('%', :query, '%') ) OR "
			+ " ( a.title LIKE CONCAT('%', :query, '%') ) "
			+ ")"
			+ ") AND "
			+ "("
			+ "( :cntry IS NULL OR a.country LIKE CONCAT('%', :cntry, '%') ) AND "
			+ "( :city IS NULL OR a.city LIKE CONCAT('%', :city, '%') ) AND "
			+ "( :miprc IS NULL OR a.price_per_day >= :miprc ) AND "
			+ "( :mxprc IS NULL OR a.price_per_day <= :mxprc ) AND "
			+ "( :mieval IS NULL OR a.average_evaluation >= :mieval ) AND "
			+ "( :mxeval IS NULL OR a.average_evaluation <= :mxeval )"
			+ ")"
			+ "", nativeQuery = true)
	Page<Activity> search(
			@Param("query") String query, 
			@Param("cntry") String country,
			@Param("city") String city,
			@Param("miprc") Float minPrice,
			@Param("mxprc") Float maxPrice,
			@Param("mieval") Float minEvaluation,
			@Param("mxeval") Float maxEvaluation,
			Pageable pageeable
	);
	
	List<Activity> findAllByActivityCompanyId(Long activityCompanyId);
	
	@Query("select avg(e.note) from Evaluation e WHERE e.activity.id = :id")
	Double findAverageNoteById(@Param("id") Long id);
}
