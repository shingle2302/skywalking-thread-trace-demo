package com.shingle.tracing.controller;


import com.shingle.tracing.service.TracingService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.skywalking.apm.toolkit.trace.TraceContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/tracing")
@RestController
public class TracingController {


    @Resource
    private TracingService tracingService;

    @GetMapping("/pool/callable")
    public void threadPoolCallableTracing() {
        log.info("threadPoolCallableTracing traceId {}", TraceContext.traceId());
        tracingService.traceThreadPoolCallable();
    }

    @GetMapping("/pool/runnable")
    public void threadPoolRunnableTracing() {
        log.info("threadPoolRunnableTracing traceId {}", TraceContext.traceId());
        tracingService.traceThreadPoolRunnable();
    }

    @GetMapping("/wrapper/runnable")
    public void runnableTracing() {
        log.info("runnableTracing traceId {}", TraceContext.traceId());
        tracingService.traceRunnable();
    }

    @GetMapping("/wrapper/callable")
    public void callableWrapperTracing() {
        log.info("callableWrapperTracing traceId {}", TraceContext.traceId());
        tracingService.traceCallable();
    }


    @GetMapping("/no-wrapper/runnable")
    public void runnableNoWrapperTracing() {
        log.info("runnableNoWrapperTracing traceId {}", TraceContext.traceId());
        tracingService.traceNoWrapperRunnable();
    }

    @GetMapping("/no-wrapper/callable")
    public void callableTracing() {
        log.info("callableNoWrapperTracing traceId {}", TraceContext.traceId());
        tracingService.traceNoWrapperCallable();
    }


}
