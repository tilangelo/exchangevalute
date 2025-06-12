package com.exchange.exchangevalute.controller;

import com.exchange.exchangevalute.DTO.ExchangeRequest;
import com.exchange.exchangevalute.DTO.ExchangeResponse;
import com.exchange.exchangevalute.service.ExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/currency")
public class ExchangeController {
    @Autowired
    private ExchangeService exchangeService;

    @PostMapping("/convert")
    public ResponseEntity<ExchangeResponse> convert(@RequestBody ExchangeRequest exchangeRequest) throws Exception {
        ExchangeResponse response = exchangeService.convert(exchangeRequest);

        System.out.println(response.getResult());
        System.out.println(response.getQuery().getFrom());
        /*if(response.isSuccess()){
            return response.getAmount();
        }else {
            throw new Exception("Response is negative");
        }*/
        return ResponseEntity.ok(response);
    }
}
