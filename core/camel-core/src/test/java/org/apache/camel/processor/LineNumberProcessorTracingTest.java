/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.processor;

import org.apache.camel.CamelContext;
import org.apache.camel.ContextTestSupport;
import org.apache.camel.Exchange;
import org.apache.camel.LineNumberAware;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LineNumberProcessorTracingTest extends ContextTestSupport {

    @Test
    public void testLineNumber() throws Exception {
        CamelContext context = new DefaultCamelContext();
        context.setTracing(true);

        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() {
                from("direct:start")
                        .process(new LineNumberProcessorTracingTest.MyProcessor());
            }
        });

        context.start();

        ProducerTemplate template = context.createProducerTemplate();

        Object out = template.requestBody("direct:start", "Hello World");
        Assertions.assertEquals("LineNumberProcessorTracingTest.java:41", out);
    }

    private static class MyProcessor implements Processor, LineNumberAware {

        private int lineNumber;
        private String location;

        @Override
        public int getLineNumber() {
            return lineNumber;
        }

        @Override
        public void setLineNumber(int lineNumber) {
            this.lineNumber = lineNumber;
        }

        @Override
        public String getLocation() {
            return location;
        }

        @Override
        public void setLocation(String location) {
            this.location = location;
        }

        @Override
        public void process(Exchange exchange) {
            exchange.getMessage().setBody(location + ":" + lineNumber);
        }
    }
}
