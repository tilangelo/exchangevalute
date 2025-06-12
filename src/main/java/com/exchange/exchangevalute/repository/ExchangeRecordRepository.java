package com.exchange.exchangevalute.repository;

import com.exchange.exchangevalute.Entity.ExchangeRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExchangeRecordRepository extends JpaRepository<ExchangeRecord, Long> {
}
