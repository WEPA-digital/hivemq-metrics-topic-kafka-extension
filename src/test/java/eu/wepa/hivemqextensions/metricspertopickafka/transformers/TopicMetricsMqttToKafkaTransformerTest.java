/*
 * Copyright 2024-present WEPA Digital GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package eu.wepa.hivemqextensions.metricspertopickafka.transformers;

import com.codahale.metrics.MetricRegistry;
import com.hivemq.extension.sdk.api.packets.publish.PublishPacket;
import com.hivemq.extensions.kafka.api.transformers.mqtttokafka.MqttToKafkaInitInput;
import com.hivemq.extensions.kafka.api.transformers.mqtttokafka.MqttToKafkaInput;
import com.hivemq.extensions.kafka.api.transformers.mqtttokafka.MqttToKafkaOutput;
import eu.wepa.hivemqextensions.metricspertopickafka.TopicsUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TopicMetricsMqttToKafkaTransformerTest {

    // mock objects
    private MqttToKafkaInput input;
    private MqttToKafkaOutput output;
    private MqttToKafkaInitInput initInput;
    private PublishPacket publishPacket;

    // test object
    private TopicMetricsMqttToKafkaTransformer transformer;

    private MetricRegistry metricRegistry;

    @BeforeEach
    void setUp() {
        input = mock(MqttToKafkaInput.class);
        initInput = mock(MqttToKafkaInitInput.class);
        output = mock(MqttToKafkaOutput.class);
        publishPacket = mock(PublishPacket.class);

        when(input.getPublishPacket()).thenReturn(publishPacket);

        metricRegistry = new MetricRegistry();
        when(initInput.getMetricRegistry()).thenReturn(metricRegistry);

        transformer = new TopicMetricsMqttToKafkaTransformer();
        transformer.init(initInput);
    }

    @Test
    void transformMqttToKafka_missingValueIncCounter() {
        when(publishPacket.getTopic()).thenReturn("data/test/slow");
        String metricNameSlow = TopicsUtils.topicToValidMetricName("data/test/slow", TopicMetricsMqttToKafkaTransformer.METRIC_OUTBOUND_PREFIX);

        transformer.transformMqttToKafka(input, output);
        assertEquals(1, metricRegistry.counter(metricNameSlow).getCount());

        when(publishPacket.getTopic()).thenReturn("data/test/fast");
        String metricNameFast = TopicsUtils.topicToValidMetricName("data/test/fast", TopicMetricsMqttToKafkaTransformer.METRIC_OUTBOUND_PREFIX);

        transformer.transformMqttToKafka(input, output);
        assertEquals(1, metricRegistry.counter(metricNameFast).getCount());

        when(publishPacket.getTopic()).thenReturn("data/test/slow");
        transformer.transformMqttToKafka(input, output);
        assertEquals(2, metricRegistry.counter(metricNameSlow).getCount());
    }
}
