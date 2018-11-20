/**
 * Added By Nikhil On 19-Nov-2018 recieve message on create via listener
 */
package com.api.RESTful.util;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SQSListener implements MessageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(SQSListener.class);

    @Override
	public void onMessage(Message message) {

        TextMessage textMessage = (TextMessage) message;

        try {
            LOGGER.info("Received message "+ textMessage.getText());
        } catch (JMSException e) {
            LOGGER.error("Error processing message ",e);
        }
    }
}
