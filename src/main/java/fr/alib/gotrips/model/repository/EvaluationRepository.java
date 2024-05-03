package fr.alib.gotrips.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.alib.gotrips.model.entity.offers.Evaluation;

@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Long>{
}
