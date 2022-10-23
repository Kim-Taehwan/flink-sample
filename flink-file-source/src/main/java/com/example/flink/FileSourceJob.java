package com.example.flink;

import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileSourceJob {
    private static Logger log = LoggerFactory.getLogger(FileSourceJob.class);

    public static void main(String[] args) throws Exception {
        log.info("Start FileSourceJob");
        log.debug("debug");
        log.warn("warn");
        log.trace("trace");
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        DataStream<String> data = env.readTextFile("file:///Users/kkomamini/dev/git/flink-sample/flink-file-source/src/main/resources/log.txt");
        log.info("input data: {}", data.print());

        data.filter(new FilterFunction<String>() {
            @Override
            public boolean filter(String value) throws Exception {
                log.info("data value: {}", value);
                return value.startsWith("http://");
            }
        }).writeAsText("file:///Users/kkomamini/dev/git/flink-sample/flink-file-source/src/main/resources/output.txt");

        env.execute("Read File");
    }
}
