/**
 * Added By Nikhil On 19-Nov-2018 for initializing AWS credentials at startup
 */
package com.api.RESTful.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;

@Configuration
public class JMSSQSConfig {
	private static final String END_POINT = System.getProperty("QUEUE_URL");
	private static final String QUEUE_NAME = System.getProperty("QUEUE_NAME");

	@Autowired
	private SQSListener sqsListener;

    @Bean
    public DefaultMessageListenerContainer jmsListenerContainer() {
		// End point http://localhost:9324
		SQSConnectionFactory sqsConnectionFactory = SQSConnectionFactory.builder()
                .withAWSCredentialsProvider(new DefaultAWSCredentialsProviderChain())
				.withEndpoint(END_POINT)
                .withAWSCredentialsProvider(awsCredentialsProvider)
                .withNumberOfMessagesToPrefetch(10).build();

        DefaultMessageListenerContainer dmlc = new DefaultMessageListenerContainer();
        dmlc.setConnectionFactory(sqsConnectionFactory);
		dmlc.setDestinationName(QUEUE_NAME);

		dmlc.setMessageListener(sqsListener); // Disable if not wanted to listen/consume

        return dmlc;
    }

	// Create jms template on start of spring boot
    @Bean
    public JmsTemplate createJMSTemplate() {
		// End point http://localhost:9324
		SQSConnectionFactory sqsConnectionFactory = SQSConnectionFactory.builder()
                .withAWSCredentialsProvider(awsCredentialsProvider)
				.withEndpoint(END_POINT)
                .withNumberOfMessagesToPrefetch(10).build();

        JmsTemplate jmsTemplate = new JmsTemplate(sqsConnectionFactory);
		jmsTemplate.setDefaultDestinationName(QUEUE_NAME);
		jmsTemplate.setDeliveryPersistent(false);


        return jmsTemplate;
    }

    private final AWSCredentialsProvider awsCredentialsProvider = new AWSCredentialsProvider() {
        @Override
        public AWSCredentials getCredentials() {
			return new BasicAWSCredentials("x", "x"); // TODO: Implement when actual credential provided
        }

        @Override
        public void refresh() {

        }
    };

}
