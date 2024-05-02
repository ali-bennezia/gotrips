package fr.alib.gotrips.model.repository.company;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.alib.gotrips.model.entity.company.HotelCompany;

@Repository
public interface HotelCompanyRepository extends JpaRepository<HotelCompany, Long> {
}
