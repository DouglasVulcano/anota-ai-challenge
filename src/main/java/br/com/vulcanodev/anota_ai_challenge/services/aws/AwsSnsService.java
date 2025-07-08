package br.com.vulcanodev.anota_ai_challenge.services.aws;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.Topic;

@Service
public class AwsSnsService {
 
    AmazonSNS snsClient;
    Topic catalogTopic;

    public AwsSnsService(AmazonSNS snsClient, @Qualifier("catalogEventsTopicArn") Topic catalogTopic) {
        this.snsClient = snsClient;
        this.catalogTopic = catalogTopic;
    }

    public void publish(String message) {
        this.snsClient.publish(this.catalogTopic.getTopicArn(), message);
    }
}
