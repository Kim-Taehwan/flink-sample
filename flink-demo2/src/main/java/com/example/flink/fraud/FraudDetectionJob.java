package com.example.flink.fraud;

import com.example.flink.fraud.entity.Alert;
import com.example.flink.fraud.entity.Transaction;
import com.example.flink.fraud.operator.FraudDetector;
import com.example.flink.fraud.sink.AlertSink;
import com.example.flink.fraud.source.TransactionSource;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment;

public class FraudDetectionJob {

    public static void main(String[] args) throws Exception {
        System.out.println("Hello Flink");
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStream<Transaction> transactions = env
                .addSource(new TransactionSource())
                .name("transactions");

        DataStream<Alert> alerts = transactions
                .keyBy(Transaction::getAccountId)
                .process(new FraudDetector())
                .name("fraud-detector");

        alerts
                .addSink(new AlertSink())
                .name("send-alerts");

        env.execute("Fraud Detection");
    }
}
