package com.example.flink.fraud;

import com.example.flink.fraud.entity.Alert;
import com.example.flink.fraud.entity.Transaction;
import com.example.flink.fraud.sink.AlertSink;
import com.example.flink.fraud.source.TransactionsSource;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class FraudDetectionJob {

    public static void main(String[] args) throws Exception {
        System.out.println("Hello Flink Demo3");
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // Creating a Source
        DataStream<Transaction> transactions = env
                .addSource(new TransactionsSource())
                .name("transactions");

        DataStream<Alert> alerts = transactions
                .keyBy(Transaction::getAccountId)
                .process(new FraudDetector())
                .name("fraud-detector");

        alerts.addSink(new AlertSink())
                .name("send-alert");

        env.execute("Fraud Detection");
    }
}
