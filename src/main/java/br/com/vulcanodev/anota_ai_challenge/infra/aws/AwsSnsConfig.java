package br.com.vulcanodev.anota_ai_challenge.infra.aws;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.Topic;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsSnsConfig {
    
    @Value("${aws.region}")
    private String region;
    
    @Value("${aws.sns.topic-arn}")
    private String topicArn;
    
    @Value("${aws.secret-access-key}")
    private String secretAccessKey;

    @Value("${aws.access-key-id}")
    private String accessKeyId;

    @Bean
    public AmazonSNS awsSnsBuilder() {
        BasicAWSCredentials credentials = new BasicAWSCredentials(accessKeyId, secretAccessKey);
        return AmazonSNSClientBuilder.standard()
            .withRegion(region)
            .withCredentials(new AWSStaticCredentialsProvider(credentials))
            .build();
    }

    @Bean(name = "topicArn")
    public Topic topicArn() {
        return new Topic().withTopicArn(topicArn);
    }
}
