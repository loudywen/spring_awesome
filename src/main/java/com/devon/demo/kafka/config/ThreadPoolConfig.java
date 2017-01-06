package com.devon.demo.kafka.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
public class ThreadPoolConfig extends AsyncConfigurerSupport{
    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor tpt = new ThreadPoolTaskExecutor();
        tpt.setCorePoolSize(30);
        return tpt;
    }
}
