/**
 * Added By Nikhil On 19-Nov-2018 for
 */
package com.api.RESTful.dao;

import com.api.RESTful.dto.issues.Story;

public interface IssuesRepository<I> extends DAORepository<Story, I> {
	public Story pushStory(String query, String name);
}
