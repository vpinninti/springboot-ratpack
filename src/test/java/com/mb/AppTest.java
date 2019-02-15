package com.mb;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mb.dto.AccountBalanceDTO;
import com.mb.dto.BalanceDTO;
import com.mb.dto.SpendRequestDTO;
import com.mb.dto.SpendResponseDTO;
import com.mb.dto.TransactionDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(App.class)
@WebIntegrationTest
public class AppTest {

	@Test
	public void testLoginAPI() throws JsonProcessingException, IOException, URISyntaxException {
		RestTemplate restTemplate = new TestRestTemplate();
		MultiValueMap<String, Object> parts = new LinkedMultiValueMap<String, Object>();
		String response = restTemplate.postForObject("http://localhost:5050/login", parts, String.class);

		ObjectMapper objectMapper = new ObjectMapper();
		BalanceDTO balObj = objectMapper.readValue(response, BalanceDTO.class);
		
		Assert.assertEquals(new Double(100.0), balObj.getBalance());
	}

	@Test
	public void testBalAPI() throws JsonProcessingException, IOException, URISyntaxException {
		RestTemplate restTemplate = new TestRestTemplate();

		MultiValueMap<String, Object> parts = new LinkedMultiValueMap<String, Object>();
		String response = restTemplate.postForObject("http://localhost:5050/login", parts, String.class);

		ObjectMapper objectMapper = new ObjectMapper();
		BalanceDTO balObj = objectMapper.readValue(response, BalanceDTO.class);

		Assert.assertEquals(new Double(100.0), balObj.getBalance());
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add("Content-Type", "application/json");
		headers.add("token", balObj.getToken());

		ResponseEntity<AccountBalanceDTO> entity = new TestRestTemplate().exchange("http://localhost:5050/balance",
				HttpMethod.GET, new HttpEntity<Object>(headers), AccountBalanceDTO.class);
		Assert.assertEquals(HttpStatus.OK, entity.getStatusCode());
		Assert.assertEquals(new Double(100.0), entity.getBody().getBalance());
		Assert.assertEquals("INR",entity.getBody().getCurrency());
	}
	
	@Test
	public void testSpendAPI() throws JsonProcessingException, IOException, URISyntaxException {
		RestTemplate restTemplate = new TestRestTemplate();
		
		MultiValueMap<String, Object> parts = new LinkedMultiValueMap<String, Object>();
		String response = restTemplate.postForObject("http://localhost:5050/login", parts, String.class);

		ObjectMapper objectMapper = new ObjectMapper();
		BalanceDTO balObj = objectMapper.readValue(response, BalanceDTO.class);

		Assert.assertEquals(new Double(100.0), balObj.getBalance());
		
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add("Content-Type", "application/json");
		headers.add("token", balObj.getToken());

	
 
		SpendRequestDTO spendRequest = new SpendRequestDTO();
		spendRequest.setAmount(5.0);
		spendRequest.setDescription("test");
		spendRequest.setCurrency("INR");
		spendRequest.setDate(new Date());
		
		
		ResponseEntity<SpendResponseDTO> entity = new TestRestTemplate().exchange("http://localhost:5050/spend", HttpMethod.POST, new HttpEntity<Object>(spendRequest,headers), SpendResponseDTO.class);
		Assert.assertEquals(HttpStatus.OK, entity.getStatusCode());
		Assert.assertEquals("success", entity.getBody().getStatus());
		
		
		
		MultiValueMap<String, String> headers2 = new LinkedMultiValueMap<String, String>();
		headers2.add("Content-Type", "application/json");
		headers2.add("token", balObj.getToken());

		ResponseEntity<AccountBalanceDTO> entity2 = new TestRestTemplate().exchange("http://localhost:5050/balance",
				HttpMethod.GET, new HttpEntity<Object>(headers2), AccountBalanceDTO.class);
		Assert.assertEquals(HttpStatus.OK, entity2.getStatusCode());
		Assert.assertEquals(new Double(95.0), entity2.getBody().getBalance());
		
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testTxnAPI() throws JsonProcessingException, IOException, URISyntaxException {
		RestTemplate restTemplate = new TestRestTemplate();

		MultiValueMap<String, Object> parts = new LinkedMultiValueMap<String, Object>();
		String response = restTemplate.postForObject("http://localhost:5050/login", parts, String.class);

		ObjectMapper objectMapper = new ObjectMapper();
		BalanceDTO balObj = objectMapper.readValue(response, BalanceDTO.class);

		Assert.assertEquals(new Double(100.0), balObj.getBalance());
		
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add("Content-Type", "application/json");
		headers.add("token", balObj.getToken());

	
 
		SpendRequestDTO spendRequest = new SpendRequestDTO();
		spendRequest.setAmount(5.0);
		spendRequest.setDescription("test");
		spendRequest.setCurrency("INR");
		spendRequest.setDate(new Date());
		
		
		ResponseEntity<SpendResponseDTO> entity = new TestRestTemplate().exchange("http://localhost:5050/spend", HttpMethod.POST, new HttpEntity<Object>(spendRequest,headers), SpendResponseDTO.class);
		Assert.assertEquals(HttpStatus.OK, entity.getStatusCode());
		Assert.assertEquals("success", entity.getBody().getStatus());
		
		
		
		
		MultiValueMap<String, String> headers2 = new LinkedMultiValueMap<String, String>();
		headers2.add("Content-Type", "application/json");
		headers2.add("token", balObj.getToken());

		ResponseEntity<String> entity2 = new TestRestTemplate().exchange("http://localhost:5050/transactions",
				HttpMethod.GET, new HttpEntity<Object>(headers), String.class);
		
		Assert.assertEquals(HttpStatus.OK, entity2.getStatusCode());
		
		
		ObjectMapper objectMapper2 = new ObjectMapper();
		
		TransactionDTO[] objList = objectMapper2.readValue(entity2.getBody(), TransactionDTO[].class);
		Assert.assertEquals(1, objList.length);
		Assert.assertEquals(new Double(5.0),objList[0].getAmount());
		Assert.assertEquals("INR",objList[0].getCurrency());
		Assert.assertEquals("test",objList[0].getDescription());
		
		
	}

}

