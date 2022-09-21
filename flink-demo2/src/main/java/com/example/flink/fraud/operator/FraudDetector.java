package com.example.flink.fraud.operator;


import com.example.flink.fraud.entity.Alert;
import com.example.flink.fraud.entity.Transaction;
import com.example.flink.fraud.utils.DateUtil;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.util.Collector;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

public class FraudDetector extends KeyedProcessFunction<Long, Transaction, Alert> {

    private static final long serialVersionUID = 1L;

    private static final double SMALL_AMOUNT = 1.00;
    private static final double LARGE_AMOUNT = 500.00;
    private static final long ONE_MINUTE = 60 * 1000;

    private transient ValueState<Boolean> flagState;
    private transient ValueState<Long> timerState;


    //데이터 처리 시작하기 전 상태 등록
    @Override
    public void open(Configuration parameters) {
        ValueStateDescriptor<Boolean> flagDescriptor = new ValueStateDescriptor<>(
                "flag",
                Types.BOOLEAN);
        flagState = getRuntimeContext().getState(flagDescriptor);

        ValueStateDescriptor<Long> timerDescriptor = new ValueStateDescriptor<>(
                "timer-state",
                Types.LONG);
        timerState = getRuntimeContext().getState(timerDescriptor);
    }

    @Override
    public void processElement(
            Transaction transaction,
            Context context,
            Collector<Alert> collector) throws Exception {
        //사기 감지기는 작은 거래를 한 계정에 즉시 큰 거래가 이어지는 모든 계정에 대한 경고를 출력

        // Get the current state for the current key
        Boolean lastTransactionWasSmall = flagState.value();

        // Check if the flag is set
        if (lastTransactionWasSmall != null) {
            if (transaction.getAmount() > LARGE_AMOUNT) {
                //Output an alert downstream
                Alert alert = new Alert();
                alert.setId(transaction.getAccountId());
                alert.setAmount(transaction.getAmount());
                collector.collect(alert);
                // Clean up our state
                cleanUp(context);
            }

        }

        /**
         * 1달러 보다 적은 금액일 경우
         */
        if (transaction.getAmount() < SMALL_AMOUNT) {
            // set the flag to true
            flagState.update(true);

            long timer = context.timerService().currentProcessingTime() + ONE_MINUTE;
            //date time
            System.out.println("setTimer : "+ DateUtil.longToDate(timer));
            //플래그가 로 설정될 때마다 true 미래의 1분 타이머도 설정
            context.timerService().registerProcessingTimeTimer(timer);

            timerState.update(timer);
        }

    }

    //타이머가 실행되면 호출함
    @Override
    public void onTimer(long timestamp, OnTimerContext ctx, Collector<Alert> out) {
        // remove flag after 1 minute

        System.out.println("onTimer : "+ctx.getCurrentKey());
        timerState.clear();
        flagState.clear();
    }

    private void cleanUp(Context ctx) throws Exception {
        // delete timer
        Long timer = timerState.value();
        ctx.timerService().deleteProcessingTimeTimer(timer);
        System.out.println("deleteTimer : "+DateUtil.longToDate(timer));
        // clean up all state
        timerState.clear();
        flagState.clear();
    }
}