package rentalvehicles.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResponseModel {

	private HttpStatus status = HttpStatus.OK;
	private List<Rental> rentalList;
	private String message;

	public ResponseModel(String message, HttpStatus status) {
		this.message = message;
		this.status = status;
		this.rentalList = new ArrayList<Rental>();
	}

	public ResponseModel(List<Rental> rentalList, HttpStatus status) {
		this.rentalList = rentalList;
		this.status = status;
	}
}