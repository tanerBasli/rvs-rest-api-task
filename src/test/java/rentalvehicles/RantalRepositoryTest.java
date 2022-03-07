package rentalvehicles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.net.http.HttpResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import rentalvehicles.controller.VehiclesRentalController;
import rentalvehicles.model.Rental;
import rentalvehicles.model.ResponseModel;
import rentalvehicles.repository.impl.RentalRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class RantalRepositoryTest {
	
	
	private MockMvc mvc;

    @Mock
    private RentalRepository rentalRepository;

    @InjectMocks
    private VehiclesRentalController vehiclesRentalController;

	@LocalServerPort
	int port;

	@Autowired
	TestRestTemplate restTemplate;
	
	
	 // This object will be magically initialized by the initFields method below.
    private JacksonTester<Rental> jsonRental;

    @BeforeEach
    public void setup() {
        // We would need this line if we would not use the MockitoExtension
        // MockitoAnnotations.initMocks(this);
        // Here we can't use @AutoConfigureJsonTesters because there isn't a Spring context
        JacksonTester.initFields(this, new ObjectMapper());
        // MockMvc standalone approach
        mvc = MockMvcBuilders.standaloneSetup(vehiclesRentalController)
                .setControllerAdvice(null)
                .addFilters()
                .build();
    }

	@Test
	public void notNullControllerTest() throws Exception {
		assertNotNull(vehiclesRentalController);
	}

	@Test
	public void testGetAll() throws Exception {
		String url = String.format("http://localhost:%s/rvs", port);
		ResponseModel response = restTemplate.getForObject(url, ResponseModel.class);

		assertEquals(HttpStatus.OK, response.getStatus());
		assertFalse(CollectionUtils.isEmpty(response.getRentalList()));
	}
	
	
//	
//	 @Test
//	    public void canRetrieveByIdWhenExists() throws Exception {
//	        // given
//	        given(rentalRepository.get)
//	                .willReturn(new Rental());
//
//	        // when
//	        MockHttpServletResponse response = mvc.perform(
//	                get("/superheroes/2")
//	                        .accept(MediaType.APPLICATION_JSON))
//	                .andReturn().getResponse();
//
//	        // then
//	        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
//	        assertThat(response.getContentAsString()).isEqualTo(
//	        		jsonRental.write(new Rental()).getJson()
//	        );
//	    }
//	

	
}
