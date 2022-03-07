package rentalvehicles.service;

import java.math.BigInteger;
import java.util.List;

import rentalvehicles.model.ResponseModel;

public interface IRentalService {
	ResponseModel getAll();
	ResponseModel getByRentalID(Long rentalID);
	ResponseModel getByRentalIDList(List<Long> rentalIdList);
	ResponseModel getByPriceBetween(BigInteger price1, BigInteger price2);
	ResponseModel getBylimitOffset(Long limit, Long offset);
	ResponseModel getByLatLngDistance(Float lat, Float lng);
	ResponseModel getOrderBy(String columnName);
}