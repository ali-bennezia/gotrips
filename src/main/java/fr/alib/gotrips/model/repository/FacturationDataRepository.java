package fr.alib.gotrips.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fr.alib.gotrips.model.entity.user.FacturationData;

@Repository
public interface FacturationDataRepository extends JpaRepository<FacturationData, Long> {
	List<FacturationData> findAllByUserId(Long id);
}
