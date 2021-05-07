package com.demo.microservices.currencyexchangeservice;

import org.springframework.data.repository.CrudRepository;

public interface ExchangeValueRepository extends CrudRepository<ExchangeValue, Long>{
	public ExchangeValue findByFromAndTo(String from, String to);
}
