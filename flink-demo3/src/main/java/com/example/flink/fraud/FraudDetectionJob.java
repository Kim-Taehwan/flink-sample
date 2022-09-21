package com.example.flink.fraud;

import org.apache.flink.shaded.zookeeper3.org.apache.zookeeper.Transaction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class FraudDetectionJob {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // Creating a Source
        DataStream<Transaction> transactions = env
                .addSource(new TransactionsSource())
                .name("transactions");
    }
}
