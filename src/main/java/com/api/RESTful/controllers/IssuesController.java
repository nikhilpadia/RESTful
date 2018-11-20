/**
 * Added By Nikhil On 19-Nov-2018 for cotrollers / entry point
 */
package com.api.RESTful.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.RESTful.dto.issues.Story;
import com.api.RESTful.service.IssuesService;

@RestController
@RequestMapping("api/issue")
public class IssuesController {
	@Autowired
	private IssuesService issuesService;

	@RequestMapping("/sum")
	public Story pushStoryPoints(@RequestParam("query") String query, @RequestParam("name") String name) {
		return issuesService.pushStory(query, name);
	}
}
