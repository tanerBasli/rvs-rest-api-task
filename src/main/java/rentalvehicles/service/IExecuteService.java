package rentalvehicles.service;

import java.util.Map;

import rentalvehicles.model.ResponseModel;

public interface IExecuteService {
	ResponseModel executeByID(Long id) throws Exception;
	ResponseModel executeByMap(Map<String, String> requestParams) throws Exception;
}