/**
 * Added By Nikhil On 19-Nov-2018 implement dao repo and implement the queue
 */
package com.api.RESTful.util;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import com.api.RESTful.dao.DAORepository;
import com.api.RESTful.dto.issues.Story;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author nikhil
 * @param <I>
 */
@Component
public class IssuesManager implements DAORepository<Story, Integer> {
	private static final ObjectMapper mapper = new ObjectMapper();
	static final Logger log = org.apache.log4j.LogManager.getLogger(IssuesManager.class);
	private static final String QUEUE_NAME = System.getProperty("QUEUE_NAME");

	@Autowired
	private JmsTemplate jmsTemplate;

	/*
	 * Create an queue and push
	 */
	@Override
	public void create(final Story story) {
		// Using spring jms and aws sqs lib push to server
		jmsTemplate.send(QUEUE_NAME, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				try {
					return session.createTextMessage(mapper.writeValueAsString(story));
				} catch (JsonProcessingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * @see com.api.RESTful.dao.DAORepository#update(java.lang.Object)
	 */
	@Override
	public void update(Story i) {
		// TODO Update the object

	}


	/*
	 * (non-Javadoc)
	 * @see com.api.RESTful.dao.DAORepository#get(int)
	 */
	@Override
	public Story get(int i) {
		// TODO Get messages from queue
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see com.api.RESTful.dao.DAORepository#get(java.lang.Object)
	 */
	@Override
	public Story get(Integer i) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see com.api.RESTful.dao.DAORepository#delete(java.lang.Object)
	 */
	@Override
	public void delete(Integer i) {
		// TODO Auto-generated method stub

	}

}
