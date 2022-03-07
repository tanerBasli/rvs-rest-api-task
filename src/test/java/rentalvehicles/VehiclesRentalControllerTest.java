package rentalvehicles;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import rentalvehicles.controller.VehiclesRentalController;
import rentalvehicles.model.Rental;
import rentalvehicles.model.ResponseModel;
import rentalvehicles.repository.impl.RentalImageRepository;
import rentalvehicles.repository.impl.RentalRepository;
import rentalvehicles.service.IExecuteService;
import rentalvehicles.service.IRentalService;

@ExtendWith(MockitoExtension.class)
public class VehiclesRentalControllerTest {
                                                 
    @InjectMocks
    VehiclesRentalController vehiclesRentalController;
    
    @Mock
	IExecuteService executeService;
    
    @Mock
	RentalRepository rentalRepository;
	
    @Mock
	RentalImageRepository rentalImageRepository;
    
    @Mock
    IRentalService rentalService;
//    
//    @Test
//    public void getRentalListTest() throws Exception {
//    	Map<String, String> requestParams =new HashMap<>();
//
//    	requestParams.put("sort", "model");
//    	
//    	ResponseEntity<ResponseModel>  responseEntityList = vehiclesRentalController.getRecreatinalVehiclesForRental(requestParams);
//    	List<Rental> listRental = responseEntityList.getBody().getRentalList();
//    	assertThat(listRental.size()>0);
//    }
//    
//    
//    @Test
//    public void getRentalByIdTest() throws Exception {
//    	Long id =4447L;
//    	ResponseEntity<ResponseModel>  responseEntityList = vehiclesRentalController.getRecreatinalVehiclesForRentalWithByID(id);
//    	List<Rental> listRental = responseEntityList.getBody().getRentalList();
//    	assertThat(listRental.size()>0);
//    }
}
