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
import org.apache.camel.builder.RouteBuilder;
import org.junit.jupiter.api.Test;

public class LineNumberProcessorTest extends ContextTestSupport {

    @Override
    protected CamelContext createCamelContext() throws Exception {
        CamelContext context = super.createCamelContext();
        context.setSourceLocationEnabled(true);
        return context;
    }

    @Test
    public void testLineNumber() throws Exception {
        getMockEndpoint("mock:result").expectedBodiesReceived("org.apache.camel.processor.LineNumberProcessorTest$1:51");

        template.sendBody("direct:start", "Hello World");

        assertMockEndpointsSatisfied();
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("direct:start")
                        .process(new MyProcessor())
                        .to("mock:result");
            }
        };
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
        public void process(Exchange exchange) throws Exception {
            exchange.getMessage().setBody(location + ":" + lineNumber);
        }
    }
}
