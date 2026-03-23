package com.ritesh.stock_trading_client.service;

import com.ritesh.grpc.stock_trading.*;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class StockClientService {

//    @GrpcClient("stockService")
//    private StockTradingServiceGrpc.StockTradingServiceBlockingStub serviceBlockingStub;


    @GrpcClient("stockServer")
    private StockTradingServiceGrpc.StockTradingServiceStub serviceStub;

// Unary Request
//    public StockResponse getStockPrice(String stockSymbol){
//        StockRequest stockRequest = StockRequest.newBuilder().setStockSymbol(stockSymbol).build();
//        return serviceBlockingStub.getStockPrice(stockRequest);
//    }

    //Server Streaming

    public void subscribeStockPrices(String stockSymbol){
        StockRequest stockRequest = StockRequest.newBuilder().setStockSymbol(stockSymbol).build();
         serviceStub.subscribeStockPrice(stockRequest , new StreamObserver<StockResponse>() {
             @Override
             public void onNext(StockResponse stockResponse) {
                 System.out.println("📥  Stock Price Update: " + stockResponse.getStockSymbol()
                        + "Prices : " + stockResponse.getPrice()
                        + "Time : " + stockResponse.getTimestamp()
                 );
             }

             @Override
             public void onError(Throwable throwable) {
                 System.out.println("❌ Error: " + throwable.getMessage());
             }

             @Override
             public void onCompleted() {
                 System.out.println("✅ Stream completed");
             }
         });
    }

    //Client Streaming
    public void placeBulkOrders(){
        StreamObserver<OrderSummary> responseObserver = new StreamObserver<OrderSummary>(){

            @Override
            public void onNext(OrderSummary orderSummary) {
                System.out.println("TotalOrders: "  + orderSummary.getTotalOrders()
                        + "TotalAmount: " + orderSummary.getAmount()
                        + "SuccesfullCounts: " + orderSummary.getSuccessCount()
                );
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("Error");
            }

            @Override
            public void onCompleted() {
                System.out.println("Stream Completed");

            }
        };
        StreamObserver<StockOrder> requestObserver = serviceStub.bulkStockOrder(responseObserver);
        try {

            // 1st Order
            requestObserver.onNext(StockOrder.newBuilder()
                    .setOrderId("1")
                    .setStockSymbol("AAPL")
                    .setOrderType("BUY")
                    .setPrice(150.5)
                    .setQuantity(10)
                    .build());

            // 2nd Order
            requestObserver.onNext(StockOrder.newBuilder()
                    .setOrderId("2")
                    .setStockSymbol("GOOGL")
                    .setOrderType("SELL")
                    .setPrice(2700.0)
                    .setQuantity(5)
                    .build());

            // 3rd Order
            requestObserver.onNext(StockOrder.newBuilder()
                    .setOrderId("3")
                    .setStockSymbol("MSFT")
                    .setOrderType("BUY")
                    .setPrice(320.0)
                    .setQuantity(8)
                    .build());

            // 4th Order
            requestObserver.onNext(StockOrder.newBuilder()
                    .setOrderId("4")
                    .setStockSymbol("TSLA")
                    .setOrderType("SELL")
                    .setPrice(750.0)
                    .setQuantity(3)
                    .build());

            // IMPORTANT → tell server you're done
            requestObserver.onCompleted();

        } catch (Exception e) {
            requestObserver.onError(e);
        }
    }

    // BiDirectional Streaming
    public void liveTrading(){
        StreamObserver<TradeStatus> responseObserver= new StreamObserver<TradeStatus>() {
            @Override
            public void onNext(TradeStatus tradeStatus) {
                System.out.println("Order Status Received");
                System.out.println("OrderId: "+ tradeStatus.getOrderId()
                        +  "Status: "+tradeStatus.getStatus()
                        + "Message: " + tradeStatus.getMessage()
                        +"TimeStamp: "+ tradeStatus.getTimestamp()
                );
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("Error");
            }

            @Override
            public void onCompleted() {
                System.out.println("Streaming Stopped Server");
            }
        };

        StreamObserver<StockOrder> requestObserver = serviceStub.liveTrading(responseObserver);


        try {

            // 1st Order
            requestObserver.onNext(StockOrder.newBuilder()
                    .setOrderId("1")
                    .setStockSymbol("AAPL")
                    .setOrderType("BUY")
                    .setPrice(150.5)
                    .setQuantity(10)
                    .build());

            // 2nd Order
            requestObserver.onNext(StockOrder.newBuilder()
                    .setOrderId("2")
                    .setStockSymbol("GOOGL")
                    .setOrderType("SELL")
                    .setPrice(2700.0)
                    .setQuantity(5)
                    .build());

            // 3rd Order
            requestObserver.onNext(StockOrder.newBuilder()
                    .setOrderId("3")
                    .setStockSymbol("MSFT")
                    .setOrderType("BUY")
                    .setPrice(320.0)
                    .setQuantity(8)
                    .build());

            // 4th Order
            requestObserver.onNext(StockOrder.newBuilder()
                    .setOrderId("4")
                    .setStockSymbol("TSLA")
                    .setOrderType("SELL")
                    .setPrice(750.0)
                    .setQuantity(3)
                    .build());

            // IMPORTANT → tell server you're done
            requestObserver.onCompleted();

        } catch (Exception e) {
            requestObserver.onError(e);
        }
    }


}
