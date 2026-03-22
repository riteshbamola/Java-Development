package com.ritesh.stock_trading.repository;

import com.ritesh.stock_trading.entity.Stocks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepo extends JpaRepository<Stocks,Long> {
    Stocks findByStockSymbol(String stockSymbol);
}
