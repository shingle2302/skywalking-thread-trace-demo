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
        threadPoolExecutor.submit(new Runnable() {
            @Override
            public void run() {
                log.info("threadPoolRunnableTracing traceId {}", TraceContext.traceId());

            }
        });
    }

    public void traceThreadPoolCallable() {
        threadPoolExecutor.submit(new Callable<Object>() {
            @Override
            public String call() throws Exception {
                log.info("threadPoolCallableTracing traceId {}", TraceContext.traceId());
                return null;
            }
        });
    }

    public void traceRunnable() {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                log.info("runnableTracing traceId {}", TraceContext.traceId());

            }
        };
        threadPoolExecutor.submit(RunnableWrapper.of(task));

    }

    public void traceCallable() {
        Callable<Object> task = new Callable<>() {
            @Override
            public String call() throws Exception {
                log.info("callableTracing traceId {}", TraceContext.traceId());
                return null;
            }
        };
        threadPoolExecutor.submit(CallableWrapper.of(task));
    }


    public void traceNoWrapperCallable() {
        FutureTask<String> futureTask = new FutureTask<>(new Callable<>() {
            @Override
            public String call() throws Exception {
                log.info("callableNoWrapperTracing traceId {}", TraceContext.traceId());
                return null;
            }
        });
        new Thread(futureTask).start();
    }

    public void traceNoWrapperRunnable() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                log.info("runnableNoWrapperTracing traceId {}", TraceContext.traceId());
            }
        };
        new Thread(runnable).start();
    }
}
