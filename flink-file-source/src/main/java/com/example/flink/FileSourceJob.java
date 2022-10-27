package com.example.flink;

import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.serialization.SimpleStringEncoder;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.filesystem.StreamingFileSink;
import org.apache.flink.streaming.api.functions.sink.filesystem.rollingpolicies.DefaultRollingPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

import static org.apache.flink.streaming.api.functions.sink.filesystem.rollingpolicies.OnCheckpointRollingPolicy.build;

//public class FileSourceJob {
//    private static Logger log = LoggerFactory.getLogger(FileSourceJob.class);
//
//    public static void main(String[] args) throws Exception {
//        log.info("Start FileSourceJob");
//        log.debug("debug");
//        log.warn("warn");
//        log.trace("trace");
//        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
//        env.setParallelism(1);
//
//        DataStream<String> input = env.readTextFile("file:///Users/kkomamini/dev/git/flink-sample/flink-file-source/src/main/resources/log.txt");
//        log.info("input data: {}", input.print());
//
////        input.filter(new FilterFunction<String>() {
////            @Override
////            public boolean filter(String value) throws Exception {
////                log.info("data value: {}", value);
////                return value.startsWith("http://");
////            }
////        }).writeAsText("file:///Users/kkomamini/dev/git/flink-sample/flink-file-source/src/main/resources/output.txt");
//        Path path = Paths.get("resources", "log.txt");
//        log.info("path: {}", path.toFile().getName());
//
//        StreamingFileSink<String> sink = StreamingFileSink
//                .forRowFormat(
//                        new org.apache.flink.core.fs.Path(path.toUri()),
//                        new SimpleStringEncoder<String>("UTF-8"))
//                .withRollingPolicy(
//                        DefaultRollingPolicy.builder()
//                                .withRolloverInterval(TimeUnit.SECONDS.toMillis(15))
//                                .withInactivityInterval(TimeUnit.SECONDS.toMillis(5))
//                                .withMaxPartSize(1024)
//                                .build())
//                .build();
//
//        input.addSink(sink);
//
//        env.execute("Read File");
//    }
//}
