package com.example.flink.fraud.sink;

import com.example.flink.fraud.entity.Alert;
import org.apache.flink.annotation.PublicEvolving;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@PublicEvolving
@SuppressWarnings("unused")
public class AlertSink implements SinkFunction<Alert> {

    private static final long serialVersionUID = 1L;

    private static final Logger LOG = LoggerFactory.getLogger(AlertSink.class);

    @Override
    public void invoke(Alert value, Context context) {
        Alert value1 = value;
        LOG.info(value.toString());
        System.out.println(value);

    }
}
