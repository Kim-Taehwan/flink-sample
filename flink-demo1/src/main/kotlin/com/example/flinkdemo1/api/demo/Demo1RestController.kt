package com.example.flinkdemo1.api.demo

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class Demo1RestController {

    @GetMapping( "/api/v1/demo1")
    fun getDemo1(): ResponseEntity<String> {
        return ResponseEntity.ok("Hello Kotlin")
    }
}