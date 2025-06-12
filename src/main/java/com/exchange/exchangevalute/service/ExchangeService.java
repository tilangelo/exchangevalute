package com.exchange.exchangevalute.service;

import com.exchange.exchangevalute.DTO.ExchangeRequest;
import com.exchange.exchangevalute.DTO.ExchangeResponse;
import com.exchange.exchangevalute.Entity.ExchangeRecord;
import com.exchange.exchangevalute.repository.ExchangeRecordRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Service
public class ExchangeService {
    private final ExchangeRecordRepository exchangeRecordRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    public ExchangeService(ExchangeRecordRepository exchangeRecordRepository) {
        this.exchangeRecordRepository = exchangeRecordRepository;
    }

    public ExchangeResponse convert(ExchangeRequest exchangeRequest) throws JsonProcessingException {
        ExchangeRecord exchangeRecordRequest = new ExchangeRecord();
        exchangeRecordRequest.setFromCurrency(exchangeRequest.getFrom());
        exchangeRecordRequest.setToCurrency(exchangeRequest.getTo());
        exchangeRecordRequest.setType("request");
        exchangeRecordRequest.setConvertedAmount(exchangeRequest.getAmount());
        exchangeRecordRepository.save(exchangeRecordRequest);

        //url запроса
        String url = "https://api.exchangerate.host/convert"
                + "?from=" + exchangeRequest.getFrom()
                + "&to=" + exchangeRequest.getTo()
                + "&amount=" + exchangeRequest.getAmount()
                + "&access_key=" + "0b38173d55c97c9e5bfbe5d4533fe3de";
        System.out.println("URL: " + url);
        //респонс в строку
        String response1 = restTemplate.getForObject(url, String.class);

        System.out.println("String response: " + response1);

        ObjectMapper objectMapper = new ObjectMapper();
        ExchangeResponse exchangeResponse = objectMapper.readValue(response1, ExchangeResponse.class);
        System.out.println("Parsed result from string: " + exchangeResponse.getResult());

        ExchangeRecord exchangeRecordResponse = new ExchangeRecord();
        exchangeRecordResponse.setFromCurrency(exchangeRequest.getFrom());
        exchangeRecordResponse.setToCurrency(exchangeRequest.getTo());
        exchangeRecordResponse.setConvertedAmount(BigDecimal.valueOf(exchangeResponse.getResult()));
        exchangeRecordResponse.setType("response");
        exchangeRecordRepository.save(exchangeRecordResponse);

        return exchangeResponse;
    }
}
