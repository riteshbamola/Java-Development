package com.ritesh.stock_trading.service;


import com.ritesh.grpc.stock_trading.StockRequest;
import com.ritesh.grpc.stock_trading.StockResponse;
import com.ritesh.grpc.stock_trading.StockResponseOrBuilder;
import com.ritesh.grpc.stock_trading.StockTradingServiceGrpc;
import com.ritesh.stock_trading.entity.Stocks;
import com.ritesh.stock_trading.repository.StockRepo;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.grpc.server.service.GrpcService;

@GrpcService
public class StockTradingServiceImpl extends StockTradingServiceGrpc.StockTradingServiceImplBase {


    @Autowired
    private StockRepo stockRepo;


    @Override
    public void getStockPrice(StockRequest request, StreamObserver<StockResponse> responseObserver) {
        //stockSymbol -> DB  -> map response  -> return

        String stockSymbol = request.getStockSymbol();
        Stocks entity = stockRepo.findByStockSymbol(stockSymbol);


        StockResponse stockResponse = StockResponse.newBuilder()
                .setStockSymbol(entity.getStockSymbol())
                .setPrice(entity.getPrice())
                .setTimestamp(entity.getLastUpdated().toString())
                .build();

        responseObserver.onNext(stockResponse);
        responseObserver.onCompleted();
    }
}
