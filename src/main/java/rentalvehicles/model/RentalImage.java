package rentalvehicles.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "RENTAL_IMAGES")
public class RentalImage {
	@Id
	private Long id;
	private Long rentalId;
	private String url;	
}