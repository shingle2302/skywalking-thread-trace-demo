package com.shingle.tracing.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.skywalking.apm.toolkit.trace.CallableWrapper;
import org.apache.skywalking.apm.toolkit.trace.RunnableWrapper;
import org.apache.skywalking.apm.toolkit.trace.TraceContext;
import org.springframework.stereotype.Service;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

@Slf4j
@Service
public class TracingService {

    private final ExecutorService threadPoolExecutor = Executors.newFixedThreadPool(2);

    public void traceThreadPoolRunnable() {
        threadPoolExecutor.submit(() -> log.info("threadPoolRunnableTracing traceId {}", TraceContext.traceId()));
    }

    public void traceThreadPoolCallable() {
        threadPoolExecutor.submit(() -> {
            log.info("threadPoolCallableTracing traceId {}", TraceContext.traceId());
            return null;
        });
    }

    public void traceRunnable() {
        Runnable task = () -> log.info("runnableTracing traceId {}", TraceContext.traceId());
        new Thread(RunnableWrapper.of(task)).start();
    }

    public void traceCallable() {
        Callable<String> task = () -> {
            log.info("callableTracing traceId {}", TraceContext.traceId());
            return null;
        };
        FutureTask<String> futureTask = new FutureTask<>(CallableWrapper.of(task));
        new Thread(futureTask).start();
    }


    public void traceNoWrapperCallable() {
        FutureTask<String> futureTask = new FutureTask<>(() -> {
            log.info("callableNoWrapperTracing traceId {}", TraceContext.traceId());
            return null;
        });
        new Thread(futureTask).start();
    }

    public void traceNoWrapperRunnable() {
        Runnable runnable = () -> log.info("runnableNoWrapperTracing traceId {}", TraceContext.traceId());
        new Thread(runnable).start();
    }
}
