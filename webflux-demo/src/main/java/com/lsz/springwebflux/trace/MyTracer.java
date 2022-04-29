package com.lsz.springwebflux.trace;

import lombok.Data;

@Data
public class MyTracer {

    private String traceId;
    private String spanId;
    private String parentSpanId;

}
