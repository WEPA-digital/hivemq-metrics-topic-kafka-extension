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

import com.codahale.metrics.Counter;
import com.codahale.metrics.MetricRegistry;
import com.hivemq.extension.sdk.api.annotations.NotNull;
import com.hivemq.extension.sdk.api.packets.publish.PublishPacket;
import com.hivemq.extensions.kafka.api.transformers.mqtttokafka.MqttToKafkaInitInput;
import com.hivemq.extensions.kafka.api.transformers.mqtttokafka.MqttToKafkaInput;
import com.hivemq.extensions.kafka.api.transformers.mqtttokafka.MqttToKafkaOutput;
import com.hivemq.extensions.kafka.api.transformers.mqtttokafka.MqttToKafkaTransformer;
import eu.wepa.hivemqextensions.metricspertopickafka.TopicsUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class TopicMetricsMqttToKafkaTransformer implements MqttToKafkaTransformer {

    public static final String METRIC_OUTBOUND_PREFIX = "eu.wepa.hivemq.outbound.messages.count";

    private @NotNull HashMap<String, Counter> counters;

    private MetricRegistry metricRegistry;

    private static final @NotNull Logger LOG = LoggerFactory.getLogger(TopicMetricsMqttToKafkaTransformer.class);

    @Override
    public void init(final @NotNull MqttToKafkaInitInput input) {
        metricRegistry = input.getMetricRegistry();
        counters = new HashMap<>();
    }

    @Override
    public void transformMqttToKafka(@NotNull MqttToKafkaInput mqttToKafkaInput, @NotNull MqttToKafkaOutput mqttToKafkaOutput) {

        // Outbound messages
        final PublishPacket publishPacket = mqttToKafkaInput.getPublishPacket();
        final String topic = publishPacket.getTopic();

        String metricName = TopicsUtils.topicToValidMetricName(topic, METRIC_OUTBOUND_PREFIX);

        // if counter not exist than, add it to counters.
        if (!counters.containsKey(metricName)) {
            LOG.info("No Metric Found For Topic: {}", topic);
            LOG.info("Create new Metric {} For Topic: {}", metricName, topic);

            Counter counter = metricRegistry.counter(metricName);
            counters.put(metricName, counter);
        }

        counters.get(metricName).inc();
    }
}
