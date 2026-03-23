package com.ritesh.stock_trading_client.service;

import com.ritesh.grpc.stock_trading.StockRequest;
import com.ritesh.grpc.stock_trading.StockResponse;
import com.ritesh.grpc.stock_trading.StockTradingServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class StockClientService {

    @GrpcClient("stockService")
    private StockTradingServiceGrpc.StockTradingServiceBlockingStub serviceBlockingStub;


    public StockResponse getStockPrice(String stockSymbol){
        StockRequest stockRequest = StockRequest.newBuilder().setStockSymbol(stockSymbol).build();
        return serviceBlockingStub.getStockPrice(stockRequest);
    }
}
