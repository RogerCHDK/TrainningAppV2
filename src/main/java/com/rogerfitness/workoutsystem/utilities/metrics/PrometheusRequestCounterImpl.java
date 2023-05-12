package com.rogerfitness.workoutsystem.utilities.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PrometheusRequestCounterImpl implements RequestCounter{
    @Autowired
    private MeterRegistry meterRegistry;
    public PrometheusRequestCounterImpl() {
    }

    @Override
    public void incrementCount(String name) {
        log.info("incrementing count for {}", name);
        Counter.builder(name).register(this.meterRegistry).increment();
    }
}
