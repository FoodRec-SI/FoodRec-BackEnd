package com.foodrec.backend.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories
public class ElasticsearchConfig extends ElasticsearchConfiguration {
    @Value("${SPRING_ELASTICSEARCH_HOSTNAME}")
    private String elasticsearchHostname;

    @Override
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
                .connectedTo(elasticsearchHostname + ":9200")
                .build();
    }
}
