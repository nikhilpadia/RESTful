/**
 * Added By Nikhil On 19-Nov-2018 create an aws client
 */
package com.api.RESTful.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sqs.AmazonSQSClient;

@Configuration
public class SQSConfig {

	private static final String END_POINT = System.getProperty("QUEUE_URL");
	private static final String QUEUE_NAME = System.getProperty("QUEUE_NAME");

	// Create an AWS SQL Client
    @Bean
    public AmazonSQSClient createSQSClient() {
		// TODO: Implement when actual credential provided
		AmazonSQSClient amazonSQSClient = new AmazonSQSClient(new BasicAWSCredentials("x", "x"));
		amazonSQSClient.setEndpoint(END_POINT);
		amazonSQSClient.createQueue(QUEUE_NAME);

        return amazonSQSClient;
    }

}
