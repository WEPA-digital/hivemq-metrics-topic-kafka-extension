## Hivemq-metrics-topic-kafka-extension

### Purpose

This extension uses a Mqtt-to-Kafka transformer which Get MQTT PUBLISHES messages and expose `Prometheus` metrics tracking the count of those outbound messages per topic. Its implementation adheres closely to the HiveMQ Kafka Extension Customization development [documentation](https://docs.hivemq.com/hivemq-kafka-extension/latest/customization.html).

### Features
- The extension exposes `Prometheus` metrics to track the count of outbound PUBLISH messages per topic.

### Installation
To install the extension, follow these steps:
1. Download the `Jar` from the release section.
2. Copy the `JAR` file to the directory: "HIVEMQ_HOME/extensions/hivemq-kafka-extension/customizations"
3. Copy the [kafka-configuration.xml](src/main/resources/kafka-configuration.xml) to "HIVEMQ_HOME/extensions/hivemq-kafka-extension" and adapt it to your environment.

### Metrics
Metrics for outbound messages follow this format: `<outbound-metric-prefix>_<topic>`.
- *Outbound Metric Prefix*: `eu.wepa.hivemq.messages.outbound.count`.

The prefix for outbound metrics is hard coded in  [TopicMetricsMqttToKafkaTransformer`](src/main/java/eu/wepa/hivemqextensions/metricspertopickafka/transformers/TopicMetricsMqttToKafkaTransformer.java) file.

> **Note:**
> The topic path separation is replaced by `_` instead of `/` as well as the `.` in the prefixes.

### Configuration
The configuration includes the following settings.

Example [`kafka-configuration.xml`](src/main/resources/kafka-configuration.xml):
```xml
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<kafka-configuration xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                     xsi:noNamespaceSchemaLocation="kafka-extension.xsd">

    <kafka-clusters>
        <kafka-cluster>
            <id>cluster01</id>
            <bootstrap-servers>127.0.0.1:9092</bootstrap-servers>
        </kafka-cluster>
    </kafka-clusters>

    <mqtt-to-kafka-transformers>
        <mqtt-to-kafka-transformer>
            <id>mqtt-topic-metric-mqtt-to-kafka-transformer</id>
            <cluster-id>cluster01</cluster-id>
            <mqtt-topic-filters>
                <mqtt-topic-filter>connect/#</mqtt-topic-filter>
                <mqtt-topic-filter>disconnect/#</mqtt-topic-filter>
                <mqtt-topic-filter>topic/#</mqtt-topic-filter>
            </mqtt-topic-filters>
            <transformer>eu.wepa.hivemqextensions.metricspertopickafka.transformers.TopicMetricsMqttToKafkaTransformer</transformer>
        </mqtt-to-kafka-transformer>
    </mqtt-to-kafka-transformers>

    <kafka-to-mqtt-transformers>
        <kafka-to-mqtt-transformer>
            <id>mqtt-topic-metric-kafka-to-mqtt-transformer</id>
            <cluster-id>cluster01</cluster-id>
            <kafka-topics>
                <kafka-topic>eu.wepa.topic</kafka-topic>
            </kafka-topics>
            <transformer>eu.wepa.hivemqextensions.metricspertopickafka.transformers.TopicMetricsMqttToKafkaTransformerTest</transformer>
        </kafka-to-mqtt-transformer>
    </kafka-to-mqtt-transformers>
</kafka-configuration>
```

### Development

#### Unit tests
This extension includes unit tests to cover certain logic. You can find them in the `test` folder.

#### GitHub Workflows
This repository utilizes the following GitHub Actions Workflows:

- **check**: This workflow runs on each push to a feature branch or for merge requests. It runs the unit tests.
- **releaseExtension**: This workflow runs on each GitHub release of the form `major.minor.patch`. It builds the extension code base, generates a `Jar` archive, and attaches that archive to the release.

> **Note:**
> The GitHub release tag should have the same version as the specified one in [gradle.properties](gradle.properties).