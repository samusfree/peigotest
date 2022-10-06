package ec.peigo.test.backend.peigobackend.rest;

import ec.peigo.test.backend.peigobackend.config.TestSecurityConfig;
import ec.peigo.test.backend.peigobackend.dto.TransferDTO;
import ec.peigo.test.backend.peigobackend.dto.TransferResponseDTO;
import ec.peigo.test.backend.peigobackend.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = TransactionController.class)
@Import(TestSecurityConfig.class)
public class TransactionControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private TransactionService transactionService;
	private final String ORIGIN_ACCOUNT_NUMBER = "22222";
	private final String DESTINATION_ACCOUNT_NUMBER = "55555";
	private final BigDecimal BALANCE_ORIGIN = BigDecimal.valueOf(20000);
	private final BigDecimal BALANCE_DESTINATION = BigDecimal.valueOf(30000);
	private final BigDecimal AMOUNT_TEST = BigDecimal.valueOf(314);
	private final String USER_NAME = "Test";
	private final Long TRANSACTION_ID = 1L;
	TransferDTO transferDTO = new TransferDTO(ORIGIN_ACCOUNT_NUMBER, DESTINATION_ACCOUNT_NUMBER, AMOUNT_TEST);
	String transferDTOJSON = "{\"originAccountNumber\":\"" + ORIGIN_ACCOUNT_NUMBER + "\", \"destinationAccountNumber\":\"" + DESTINATION_ACCOUNT_NUMBER + "\",\"amount\":" + AMOUNT_TEST + "}";

	@Test
	public void transfer() throws Exception {
		Mockito.when(transactionService.tranfer(any(), any()))
				.thenReturn(new TransferResponseDTO(TRANSACTION_ID, ORIGIN_ACCOUNT_NUMBER, DESTINATION_ACCOUNT_NUMBER,
						BALANCE_ORIGIN, BALANCE_DESTINATION));
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/transfer/")
				.accept(MediaType.APPLICATION_JSON)
				.content(transferDTOJSON)
				.contentType(MediaType.APPLICATION_JSON)
				.header(HttpHeaders.AUTHORIZATION, "Bearer token");
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		String expected = "{\"success\":true,\"data\":{\"operationNumber\":" + TRANSACTION_ID + ",\"originAccountNumber\":\"" + ORIGIN_ACCOUNT_NUMBER + "\",\"destinationAccountNumber\":\"" + DESTINATION_ACCOUNT_NUMBER + "\",\"balanceOriginAccount\":" + BALANCE_ORIGIN + ", \"balanceDestinationAccount\":" + BALANCE_DESTINATION + "}}";
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}
}
