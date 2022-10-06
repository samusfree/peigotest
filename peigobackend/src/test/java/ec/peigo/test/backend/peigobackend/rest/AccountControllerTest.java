package ec.peigo.test.backend.peigobackend.rest;

import ec.peigo.test.backend.peigobackend.config.TestSecurityConfig;
import ec.peigo.test.backend.peigobackend.dto.AccountDTO;
import ec.peigo.test.backend.peigobackend.service.AccountService;
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

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = AccountsController.class)
@Import(TestSecurityConfig.class)
public class AccountControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private AccountService accountService;
	private final String ACCOUNT_NUMBER = "22222";
	private final BigDecimal BALANCE_DEFAULT = BigDecimal.valueOf(10.00);
	AccountDTO mockAccountDTO = new AccountDTO(ACCOUNT_NUMBER, BALANCE_DEFAULT);
	String accountDTOJson = "{\"accountNumber\":\"" + ACCOUNT_NUMBER + "\",\"balance\":" + BALANCE_DEFAULT + "}";

	@Test
	public void retrieveAccount() throws Exception {
		Mockito.when(accountService.findAccount(Mockito.anyString())).thenReturn(mockAccountDTO);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/accounts/" + ACCOUNT_NUMBER)
				.accept(MediaType.APPLICATION_JSON)
				.header(HttpHeaders.AUTHORIZATION, "Bearer token");
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expected = "{\"success\":true,\"data\":{\"accountNumber\":\"" + ACCOUNT_NUMBER + "\",\"balance\":" + BALANCE_DEFAULT + "}}";
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}

	@Test
	public void saveAccount() throws Exception {
		AccountDTO account = new AccountDTO(ACCOUNT_NUMBER, BALANCE_DEFAULT);
		Mockito.when(accountService.saveAccount(account)).thenReturn(account);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/accounts/")
				.accept(MediaType.APPLICATION_JSON)
				.content(accountDTOJson)
				.contentType(MediaType.APPLICATION_JSON)
				.header(HttpHeaders.AUTHORIZATION, "Bearer token");
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		String expected = "{\"success\":true,\"data\":{\"accountNumber\":\"" + ACCOUNT_NUMBER + "\",\"balance\":" + BALANCE_DEFAULT + "}}";
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}

	@Test
	public void updateAccount() throws Exception {
		AccountDTO account = new AccountDTO(ACCOUNT_NUMBER, BALANCE_DEFAULT);
		Mockito.when(accountService.updateAccount(ACCOUNT_NUMBER, account)).thenReturn(account);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/accounts/" + ACCOUNT_NUMBER)
				.accept(MediaType.APPLICATION_JSON)
				.content(accountDTOJson)
				.contentType(MediaType.APPLICATION_JSON)
				.header(HttpHeaders.AUTHORIZATION, "Bearer token");
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		String expected = "{\"success\":true,\"data\":{\"accountNumber\":\"" + ACCOUNT_NUMBER + "\",\"balance\":" + BALANCE_DEFAULT + "}}";
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}
}
