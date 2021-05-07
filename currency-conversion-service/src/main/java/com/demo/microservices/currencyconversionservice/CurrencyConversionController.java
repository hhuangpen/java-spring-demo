package com.demo.microservices.currencyconversionservice;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CurrencyConversionController {
	
	@Autowired
	private CurrencyExchangeServiceProxy proxy;

	@GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean convertCurrency(@PathVariable String from, 
			@PathVariable String to, 
			@PathVariable BigDecimal quantity) {
		
		Map<String, String> uriVariables = new HashMap<>();
		uriVariables.put("from", from);
		uriVariables.put("to", to);
		
		/*
		 * Here this worked because both Service(Exchange-service) and Consumer(Conversion-service) 
		 * had a common field with same name as "conversionMultiple"
		 * There are other approach such as creating a separate model component - separate module - 
		 * for model and then both the microservices can share it!
		 */
		ResponseEntity<CurrencyConversionBean> responseEntity = new RestTemplate().getForEntity(
				"http://localhost:8000/currency-exchange/from/{from}/to/{to}",
				CurrencyConversionBean.class,
				uriVariables);
		
		// This is just extra step for converting back to object for easy manipulation
		CurrencyConversionBean response = responseEntity.getBody();
		
		// Will have the same id and port as exchange service
		return new CurrencyConversionBean(response.getId(), 
				from, to, response.getConversionMultiple(), 
				quantity, quantity.multiply(response.getConversionMultiple())
				,response.getPort());
	}
	
	@GetMapping("/currency-converter-feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean convertCurrencyFeign(@PathVariable String from, 
			@PathVariable String to, 
			@PathVariable BigDecimal quantity) {
		

		CurrencyConversionBean response = proxy.retrieveExchangeValue(from, to);
		
		// Will have the same id and port as exchange service
		return new CurrencyConversionBean(response.getId(), 
				from, to, response.getConversionMultiple(), 
				quantity, quantity.multiply(response.getConversionMultiple())
				,response.getPort());
	}
}
