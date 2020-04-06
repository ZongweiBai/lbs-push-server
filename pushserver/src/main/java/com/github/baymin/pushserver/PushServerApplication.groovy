package com.github.baymin.pushserver


import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

/**
 * 服务入口启动类
 * @author Zongwei* @date 2020/4/2 22:31
 */
@SpringBootApplication
class PushServerApplication {

    static void main(String[] args) {
        SpringApplication.run(PushServerApplication.class, args);
    }

}
