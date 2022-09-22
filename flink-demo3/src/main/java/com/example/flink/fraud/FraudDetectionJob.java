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
        /**
         * Source 는 Kafka, RabbitMQ 등 외부 시스템에서 전송 받습니다.
          */
        DataStream<Transaction> transactions = env
                .addSource(new TransactionsSource())
                .name("transactions");

        /**
         * 1. 많은 유저들의 transaction data 는 다수의 fraud detection task 를 생성해서 병렬로 처리해야 한다.
         * 2. DataStream#keyBy 로 Transaction 을 분할(partition) 할 수 있다.
         * 3. operator 는 보통 keyBy 뒤에 오고, context 내에서 실행됩니다.
         */
        DataStream<Alert> alerts = transactions
                .keyBy(Transaction::getAccountId)
                .process(new FraudDetector())
                .name("fraud-detector");

        /**
         * Sink 는 DataStream 을 Kafka, Cassandra, Kinesis 등 외부 시스템에 쓸 수 있습니다.
         */
        alerts.addSink(new AlertSink())
                .name("send-alert");

        env.execute("Fraud Detection");
    }
}
