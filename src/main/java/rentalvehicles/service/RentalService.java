package rentalvehicles.service;

import java.math.BigInteger;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.sentry.Sentry;
import rentalvehicles.model.Rental;
import rentalvehicles.model.ResponseModel;
import rentalvehicles.repository.impl.RentalImageRepository;
import rentalvehicles.repository.impl.RentalRepository;
import rentalvehicles.util.Consts;
import rentalvehicles.util.OffsetBasedPageRequest;

@Service
@Transactional
public class RentalService implements IRentalService, InitializingBean {

	private Map<String, String> mapperParamToColumn;
	
	@Autowired
	private RentalRepository rentalRepository;
	
	@Autowired
	private RentalImageRepository rentalImageRepository;

	@Retryable(value = { SQLException.class }, maxAttempts = 2, backoff = @Backoff(delay = 10000))
	@Override
	public ResponseModel getAll() {
		var rentalList = rentalRepository.findAll();
		completeRentalImageList(rentalList);
		var response = new ResponseModel(rentalList, HttpStatus.OK);
		return response;
	}
	

	@Override
	public ResponseModel getByRentalID(Long rentalID) {
		Rental rental = rentalRepository.findById(rentalID).get();
		rental.setRentalImageList(rentalImageRepository.findRentalImageByRentalId(rentalID));
		var response = new ResponseModel(Arrays.asList(rental), HttpStatus.OK);
		return response;
	}

	@Override
	public ResponseModel getByRentalIDList(List<Long> rentalIdList) {
		var rentalList = rentalRepository.findAllById(rentalIdList);
		completeRentalImageList(rentalList);
		var response = new ResponseModel(rentalList, HttpStatus.OK);
		return response;
	}

	@Override
	public ResponseModel getByPriceBetween(BigInteger priceStart, BigInteger priceEnd) {
		var rentalList = rentalRepository.findByPricePerDayBetween(priceStart.longValue(), priceEnd.longValue());
		completeRentalImageList(rentalList);
		var response = new ResponseModel(rentalList, HttpStatus.OK);
		return response;
	}

	@Override
	public ResponseModel getBylimitOffset(Long limit, Long offset) {
		Pageable pageable = new OffsetBasedPageRequest(offset.intValue(), limit.intValue());
		var rentalList =rentalRepository.findAll(pageable).toList();
		completeRentalImageList(rentalList);
		var response = new ResponseModel(rentalList, HttpStatus.OK);
		return response;
	}

	@Override
	public ResponseModel getByLatLngDistance(Float lat, Float lng) {		
		var rentalList = rentalRepository.findRentalsWithInDistance(Double.valueOf(lat.toString()), Double.valueOf(lng.toString()), Consts.distanceWithInKM);
		completeRentalImageList(rentalList);
		var response = new ResponseModel(rentalList, HttpStatus.OK);
		return response;
	}

	@Retryable(value = { SQLTimeoutException.class }, maxAttempts = 2, backoff = @Backoff(delay = 5000))
	@Override
	public ResponseModel getOrderBy(String columnName) {
		
		var rentalList = rentalRepository.findAll(Sort.by(mapperParamToColumn.getOrDefault(columnName, columnName)));
		completeRentalImageList(rentalList);
		var response = new ResponseModel(rentalList, HttpStatus.OK);
		return response;
	}

	private void completeRentalImageList(List<Rental> rentalList) {
		
		var rentalIdList = rentalList.stream().map(rental -> rental.getId()).collect(Collectors.toList());		
		var rentalImageList = rentalImageRepository.findByRentalIdIn(rentalIdList);

		rentalList.forEach(rental -> {
			var collectedRentalImageList = rentalImageList.stream()
					.filter(rentalImage -> rentalImage.getRentalId() == rental.getId()).collect(Collectors.toList());
			rental.setRentalImageList(collectedRentalImageList);
		});
	}

	@Recover
	public ResponseModel recover(SQLTimeoutException e) {
		Sentry.captureException(e);
		return new ResponseModel(e.getMessage(), HttpStatus.REQUEST_TIMEOUT);
	}

	@Recover
	public ResponseModel recover(SQLException e) {
		Sentry.captureException(e);
		return new ResponseModel(e.getMessage(), HttpStatus.NO_CONTENT);
	}

	@Override
	public void afterPropertiesSet() throws Exception {		
		mapperParamToColumn = new HashMap<String, String>();
		mapperParamToColumn.put("price", "pricePerDay");
		mapperParamToColumn.put("city", "homeCity");
		mapperParamToColumn.put("state", "homeState");
		mapperParamToColumn.put("zip", "homeZip");
		mapperParamToColumn.put("county", "homeCounty");
		mapperParamToColumn.put("country", "homeCountry");
		mapperParamToColumn.put("make", "vehicleMake");
		mapperParamToColumn.put("model", "vehicleModel");
		mapperParamToColumn.put("year", "vehicleYear");
		mapperParamToColumn.put("length", "vehicleLength");		 
	}
}