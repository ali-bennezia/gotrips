package fr.alib.gotrips.model.repository.company;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.alib.gotrips.model.entity.company.FlightCompany;

@Repository
public interface FlightCompanyRepository extends JpaRepository<FlightCompany, Long> {
}
