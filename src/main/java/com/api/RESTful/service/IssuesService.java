/**
 * Added By Nikhil On 19-Nov-2018 for
 */
package com.api.RESTful.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.api.RESTful.dao.IssuesRepository;
import com.api.RESTful.dto.issues.Story;
import com.api.RESTful.util.HttpUtility;
import com.api.RESTful.util.IssuesManager;

@Service
public class IssuesService implements IssuesRepository<Integer> {

	@Autowired
	private IssuesManager im;

	// Push data to sqs as json object
	@SuppressWarnings({ "unchecked" })
	@Override
	public Story pushStory(String query, String name) {
		Map<String, Object> list = HttpUtility.get(query);
		Assert.notNull(list, "JIRA API is not responding"); // Throw error if jira not responding
		Story s = new Story();
		if (list != null) {
			s.setTotalPoints(0);
			if (list.containsKey("stubs") && list.get("stubs") instanceof ArrayList) {
				List<Map<String, Object>> stubs = (List<Map<String, Object>>) list.get("stubs");
				for (Map<String, Object> data : stubs) {
					List<Map<String, Object>> responses = (List<Map<String, Object>>) data.get("responses");
					for (Map<String, Object> resp : responses) {
						Map<String, Object> is = (HashMap<String, Object>) resp.get("is");
						List<Map<String, Object>> body = (List<Map<String, Object>>) is.get("body");
						if (body.size() > 0) {
							for (Map<String, Object> bodyData : body) {
								Map<String, Object> fields = (HashMap<String, Object>) bodyData.get("fields");
								s.setName(name);
								s.setTotalPoints(s.getTotalPoints() + Integer.valueOf(fields.get("storyPoints").toString()));
							}
						} else {
							// Throw if empty result
							throw new EmptyResultDataAccessException("Search results is empty", 1);
						}
					}
				}
			}
		}
		// Push the result to queue
		im.create(s);
		return s;
	}

	/*
	 * (non-Javadoc)
	 * @see com.api.RESTful.dao.DAORepository#create(java.lang.Object)
	 */
	@Override
	public void create(Story i) {
		// TODO Auto-generated method stub


	}

	/*
	 * (non-Javadoc)
	 * @see com.api.RESTful.dao.DAORepository#get(java.lang.Object)
	 */
	@Override
	public Story get(int i) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see com.api.RESTful.dao.DAORepository#update(java.lang.Object)
	 */
	@Override
	public void update(Story i) {
		// TODO Auto-generated method stub

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
