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
import com.hivemq.extension.sdk.api.annotations.NotNull;
import com.hivemq.extensions.kafka.api.transformers.mqtttokafka.MqttToKafkaInitInput;
import com.hivemq.extensions.kafka.api.transformers.mqtttokafka.MqttToKafkaInput;
import com.hivemq.extensions.kafka.api.transformers.mqtttokafka.MqttToKafkaOutput;
import com.hivemq.extensions.kafka.api.transformers.mqtttokafka.MqttToKafkaTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TopicMetricsMqttToKafkaTransformer implements MqttToKafkaTransformer {

    private MetricRegistry metricRegistry;

    private static final @NotNull Logger LOG = LoggerFactory.getLogger(TopicMetricsMqttToKafkaTransformer.class);

    @Override
    public void init(final @NotNull MqttToKafkaInitInput input) {
        this.metricRegistry = input.getMetricRegistry();
    }

    @Override
    public void transformMqttToKafka(@NotNull MqttToKafkaInput input, @NotNull MqttToKafkaOutput output) {

    }
}
