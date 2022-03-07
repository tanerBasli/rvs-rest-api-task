package rentalvehicles.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "RENTALS")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Rental {	
	@Id
	private Long id;    
	private String name;
	private String type;    
	private String description;    
	private int sleeps;    
	private long pricePerDay;    
	private String homeCity;	
	private String homeState;
	private String homeZip;	
	private String homeCounty;	
	private String homeCountry;	
	private String vehicleMake;	
	private String vehicleModel;	
	private int vehicleYear;	
	private BigDecimal vehicleLength;	
	private Timestamp created;	
	private Timestamp updated;
	private float lat;	
	private float lng;	
	private String primaryImageUrl;	
	private String ownerName;	
	private String ownerAvatarUrl;
	 
	@Transient
	private List<RentalImage> rentalImageList;	
}