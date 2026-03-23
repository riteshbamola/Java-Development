package com.ritesh.stock_trading.service;


import com.ritesh.grpc.stock_trading.*;
import com.ritesh.stock_trading.entity.Stocks;
import com.ritesh.stock_trading.repository.StockRepo;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.grpc.server.service.GrpcService;

import java.time.Instant;
import java.util.Random;
import java.util.concurrent.TimeUnit;

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

    @Override
    public void subscribeStockPrice(StockRequest request, StreamObserver<StockResponse> responseObserver) {
        System.out.println("Hit Subscribe by Postman");
        String symbol = request.getStockSymbol();
        try {
            for (int i = 0; i < 10; i++) {
                StockResponse stockResponse = StockResponse.newBuilder()
                        .setStockSymbol(symbol)
                        .setPrice(new Random().nextDouble(200))
                        .setTimestamp(Instant.now().toString())
                        .build();

                responseObserver.onNext(stockResponse);
                System.out.println("📤 Sending price: " );
                TimeUnit.SECONDS.sleep(1);
            }
            responseObserver.onCompleted();
            System.out.println("Hit Completed");
        }catch (Exception ex){
            responseObserver.onError(ex);
        }

    }

    @Override
    public StreamObserver<StockOrder> bulkStockOrder(StreamObserver<OrderSummary> responseObserver) {
        return new StreamObserver<StockOrder>() {

            private int totalOrders=0;
            private double totalAmount=0;
            private int successCount=0;
            @Override
            public void onNext(StockOrder stockOrder) {
                    totalOrders++;
                    totalAmount+=stockOrder.getQuantity() * stockOrder.getPrice();
                    successCount++;
                System.out.println("Received Order: "+ stockOrder);
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("Error");
            }

            @Override
            public void onCompleted() {
                OrderSummary response = OrderSummary.newBuilder().setTotalOrders(totalOrders)
                        .setAmount(totalAmount)
                        .setSuccessCount(successCount)
                        .build();

                responseObserver.onNext(response);
                responseObserver.onCompleted();
            }
        };
    }

    @Override
    public StreamObserver<StockOrder> liveTrading(StreamObserver<TradeStatus> responseObserver) {
        return new StreamObserver<StockOrder>() {
            @Override
            public void onNext(StockOrder stockOrder) {
                System.out.println("Received Order: "+ stockOrder);
                String message = "Order Placed Succesfully";
                String status = "EXECUTED";
                if(stockOrder.getQuantity()<=0){
                    status="FAILED";
                    message="Order Failed";
                }
                TradeStatus tradeStatus = TradeStatus.newBuilder()
                        .setOrderId(stockOrder.getOrderId())
                        .setMessage(message)
                        .setStatus(status)
                        .setTimestamp(Instant.now().toString())
                        .build();

                responseObserver.onNext(tradeStatus);

            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("Error");
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }
}
