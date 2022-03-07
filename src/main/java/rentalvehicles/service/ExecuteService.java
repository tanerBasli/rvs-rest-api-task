package rentalvehicles.service;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import rentalvehicles.model.ResponseModel;
import rentalvehicles.util.Consts;

@Service
@Lazy
@ConditionalOnWebApplication
public class ExecuteService implements IExecuteService {

	private final IRentalService rentalService;

	@Autowired
	public ExecuteService(IRentalService rentalService) {
		this.rentalService = rentalService;
	}

	@Override
	public ResponseModel executeByID(Long id) throws Exception {
		ResponseModel response = rentalService.getByRentalID(id);
		return response;
	}

	@Override
	public ResponseModel executeByMap(Map<String, String> requestParams) throws Exception {
		if (CollectionUtils.isEmpty(requestParams)) {
			return rentalService.getAll();
		}		
		
		if (isContainKey(requestParams, Consts.requestParamsIds)) {
			var idList = Arrays.stream(requestParams.get(Consts.requestParamsIds).split(",")).map(Long::valueOf)
					.collect(Collectors.toList());
			return rentalService.getByRentalIDList(idList);
		}

		if (isContainKey(requestParams, Consts.requestParamsPageLimit) || isContainKey(requestParams, Consts.requestParamsPageOffset)) {
			var limit = Long.parseLong(requestParams.getOrDefault(Consts.requestParamsPageLimit, String.valueOf(Long.MAX_VALUE)));
			var offset = Long.parseLong(requestParams.getOrDefault(Consts.requestParamsPageOffset, "0"));
			return rentalService.getBylimitOffset(limit, offset);
		}

		if ( isContainKey(requestParams, Consts.requestParamsPriceMin) || isContainKey(requestParams, Consts.requestParamsPriceMax) ) {
			var priceMin = new BigInteger(requestParams.getOrDefault(Consts.requestParamsPriceMin, "0"));
			var priceMax = new BigInteger(requestParams.getOrDefault(Consts.requestParamsPriceMax, "0"));
			return rentalService.getByPriceBetween(priceMin, priceMax);
		}

		if (isContainKey(requestParams, Consts.requestParamsNear)) {
			var paramLatitude = Float.parseFloat(StringUtils.substringBefore(requestParams.get(Consts.requestParamsNear), ","));
			var paramLongitude = Float.parseFloat(StringUtils.substringAfter(requestParams.get(Consts.requestParamsNear), ","));
			return rentalService.getByLatLngDistance(paramLatitude, paramLongitude);
		}

		if (isContainKey(requestParams, Consts.requestParamsSort)) {
			var response = rentalService.getOrderBy(requestParams.get(Consts.requestParamsSort));
			return response;
		}

		throw new Exception("BAD REQUEST!");
	}

	private boolean isContainKey(Map<String, String> requestParams, String key) {
		return requestParams.containsKey(key);
	}
}
