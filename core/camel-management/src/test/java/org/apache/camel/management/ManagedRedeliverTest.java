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
package org.apache.camel.management;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.OS;

import static org.apache.camel.management.DefaultManagementObjectNameStrategy.TYPE_PROCESSOR;
import static org.apache.camel.management.DefaultManagementObjectNameStrategy.TYPE_ROUTE;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisabledOnOs(OS.AIX)
public class ManagedRedeliverTest extends ManagementTestSupport {

    @Test
    public void testRedeliver() throws Exception {
        MBeanServer mbeanServer = getMBeanServer();

        MockEndpoint mock = getMockEndpoint("mock:foo");
        mock.expectedMessageCount(1);

        Object out = template.requestBody("direct:start", "Hello World");
        assertEquals("Error", out);

        assertMockEndpointsSatisfied();

        ObjectName on = getCamelObjectName(TYPE_ROUTE, "route1");

        Long num = (Long) mbeanServer.getAttribute(on, "ExchangesCompleted");
        assertEquals(1, num.longValue());

        num = (Long) mbeanServer.getAttribute(on, "ExchangesFailed");
        assertEquals(0, num.longValue());

        num = (Long) mbeanServer.getAttribute(on, "FailuresHandled");
        assertEquals(1, num.longValue());

        on = getCamelObjectName(TYPE_PROCESSOR, "myprocessor");

        num = (Long) mbeanServer.getAttribute(on, "ExchangesCompleted");
        assertEquals(0, num.longValue());

        num = (Long) mbeanServer.getAttribute(on, "ExchangesTotal");
        assertEquals(5, num.longValue());

        // there should be 5 failed exchanges (1 first time, and 4 redelivery attempts)
        num = (Long) mbeanServer.getAttribute(on, "ExchangesFailed");
        assertEquals(5, num.longValue());

        // and we tried to redeliver the exchange 4 times, before it failed
        num = (Long) mbeanServer.getAttribute(on, "Redeliveries");
        assertEquals(4, num.longValue());

        String first = (String) mbeanServer.getAttribute(on, "FirstExchangeFailureExchangeId");
        assertEquals(mock.getReceivedExchanges().get(0).getExchangeId(), first);

        String last = (String) mbeanServer.getAttribute(on, "LastExchangeFailureExchangeId");
        assertEquals(mock.getReceivedExchanges().get(0).getExchangeId(), last);
    }

    @Override
    protected RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {
            @Override
            public void configure() {
                onException(Exception.class).handled(true)
                        .redeliveryDelay(0)
                        .maximumRedeliveries(4).logStackTrace(false)
                        .setBody().constant("Error");

                from("direct:start")
                        .to("mock:foo")
                        .process(exchange -> {
                            log.info("Invoking me");

                            throw new IllegalArgumentException("Damn");
                        }).id("myprocessor");
            }
        };
    }
}
