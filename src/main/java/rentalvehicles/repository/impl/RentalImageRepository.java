package rentalvehicles.repository.impl;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import rentalvehicles.model.RentalImage;

public interface RentalImageRepository extends JpaRepository<RentalImage, Long> {	
	List<RentalImage> findRentalImageByRentalId(Long rentalID);
	List<RentalImage> findByRentalIdIn(List<Long> rentalIdList);
}