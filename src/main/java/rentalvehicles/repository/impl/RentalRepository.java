package rentalvehicles.repository.impl;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import rentalvehicles.model.Rental;

public interface RentalRepository extends JpaRepository<Rental, Long> {	
	public List<Rental> findByPricePerDayBetween(long priceStart, long priceEnd);
	
	String DISTANCE_FORMULA = "(6371 * acos(cos(radians(:lat)) * cos(radians(r.lat)) *" +
	        " cos(radians(r.lng) - radians(:lng)) + sin(radians(:lat)) * sin(radians(r.lat))))";
	
	@Query("SELECT r from Rental r WHERE " + DISTANCE_FORMULA + " < :distance ORDER BY "+ DISTANCE_FORMULA + " DESC")
	List<Rental> findRentalsWithInDistance(@Param("lat") double latitude, @Param("lng") double longitude, @Param("distance") double distanceWithInKM);	
}