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
import com.hivemq.extensions.kafka.api.model.KafkaRecord;
import com.hivemq.extensions.kafka.api.transformers.kafkatomqtt.KafkaToMqttInitInput;
import com.hivemq.extensions.kafka.api.transformers.kafkatomqtt.KafkaToMqttInput;
import com.hivemq.extensions.kafka.api.transformers.kafkatomqtt.KafkaToMqttOutput;
import com.hivemq.extensions.kafka.api.transformers.kafkatomqtt.KafkaToMqttTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class TopicMetricsKafkaToMqttTransformer implements KafkaToMqttTransformer {

    public static final String METRIC_INBOUND_PREFIX = "eu.wepa.hivemq.inbound.messages.count";

    private @NotNull HashMap<String, Counter> counters;

    private @NotNull MetricRegistry metricRegistry;

    private static final @NotNull Logger LOG = LoggerFactory.getLogger(TopicMetricsKafkaToMqttTransformer.class);
    
    @Override
    public void init(@NotNull KafkaToMqttInitInput input) {
        metricRegistry = input.getMetricRegistry();
        counters = new HashMap<>();
    }

    @Override
    public void transformKafkaToMqtt(@NotNull KafkaToMqttInput kafkaToMqttInput, @NotNull KafkaToMqttOutput kafkaToMqttOutput) {
        // Inbound messages
    }
}
