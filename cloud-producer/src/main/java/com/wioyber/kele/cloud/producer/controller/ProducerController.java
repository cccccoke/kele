package com.wioyber.kele.cloud.producer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cjg
 * @since 2023/2/17
 */
@RestController
@RequestMapping("/producer")
public class ProducerController {

    @GetMapping("/test")
    String getTest(){
        return "producer";
    }
}
