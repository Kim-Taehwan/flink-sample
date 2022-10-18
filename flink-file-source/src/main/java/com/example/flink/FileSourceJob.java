package com.example.flink;

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

        DataStream dataStream = env
                .readTextFile()
    }
}
