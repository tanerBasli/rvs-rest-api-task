package rentalvehicles.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.sentry.Sentry;
import rentalvehicles.model.ResponseModel;
import rentalvehicles.service.IExecuteService;

@RestController
@RequestMapping("/rvs")
public class VehiclesRentalController {

	@Autowired
	IExecuteService executeService;
	
	@GetMapping
	public ResponseEntity<ResponseModel> getRecreatinalVehiclesForRental(@RequestParam Map<String, String> requestParams) {
		try {
			var responseContext = executeService.executeByMap(requestParams);
			return new ResponseEntity<>(responseContext, responseContext.getStatus());
		} catch (Exception e) {
			Sentry.captureException(e);
			return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<ResponseModel> getRecreatinalVehiclesForRentalWithByID(@PathVariable Long id) {
		try {
			var responseContext = executeService.executeByID(id);
			return new ResponseEntity<>(responseContext, responseContext.getStatus());
		} catch (Exception e) {
			Sentry.captureException(e);
			return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
		}
	}
}