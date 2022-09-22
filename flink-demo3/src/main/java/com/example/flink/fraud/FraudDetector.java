package com.example.flink.fraud;

import com.example.flink.fraud.entity.Alert;
import com.example.flink.fraud.entity.Transaction;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.util.Collector;

/**
 * 1. FraudDetector 는 KeyedProcessFunction 을 구현합니다.
 * 2. processElement 는 모든 트랜잭션 이벤트마다 불려집니다.
 * 3. 첫 번째 버전에서는 모든 트랜잭션이 alert 을 생성합니다.
 * 4. 다음 스텝에서는 좀 더 비즈니스에 의미있게 확장됩니다.
 */
public class FraudDetector extends KeyedProcessFunction<Long, Transaction, Alert> {
    private static final long serialVersionUID = 1L;

    private static final double SMALL_AMOUNT = 1.00;
    private static final double LARGE_AMOUNT = 500.00;
    private static final long ONE_MINUTE = 60 * 1000;

    @Override
    public void processElement (
            Transaction transaction,
            Context context,
            Collector<Alert> collector) throws Exception {

        Alert alert = new Alert();
        alert.setId(transaction.getAccountId());

        collector.collect(alert);
    }
}
