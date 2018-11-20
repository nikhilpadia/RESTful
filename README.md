# RESTful

---------
| Note: |
---------
Project is under RESTful folder
Inorder to setup docker dependencies like sqs and mock data also included.
Test via running spring boot application.
Check the console for detailed logs.

// list all containers in docker
sudo docker ps
// Kill / start / stop the container
sudo docker container start 64614d434f0a


 - Java 1.8 is required 

Using maven build the code 
--------------------------------
mvn clean install

Environment Variable: (Replace actual url in live env)
-------------------------------------------------------
-DJIRA_BASE_URL=http://localhost:2525  
-DJIRA_API_URL=/imposters/4553?q=type=Bu
-DQUEUE_URL=http://localhost:9324
-DQUEUE_NAME=cst-test-queue

To RUN:
Navigate to RESTful folder and follow the below steps

java -jar -DJIRA_BASE_URL=http://localhost:2525 -DJIRA_API_URL=/imposters/4553 -DQUEUE_URL=
http://localhost:9324 -DQUEUE_NAME=cst-test-queue target/RESTful-0.0.1-SNAPSHOT.jar

Request URL (Using Postman have tested - you could use any rest client)
-----------------------------------------------------------------------
Method: GET
http://localhost:8080/api/issue/sum?query=sum&name=dsds

SAMPLE RESPONSE: (Status: 200 OK)
{
    "name": "dsds",
    "totalPoints": 9
}

- Starting spring boot without sql configuraton should throw error
- Wrong sqs url lead to resource not found 


-----------------------------------------------------
| Tested against different samples using mountebank |
-----------------------------------------------------

Inorder to run mountebank using docker (As given doc - files are attached with this project)
-------------------------------------------------------------------------------------------
docker run -v "$PWD/mountebank:/imposters" -p 2525:2525 -p 4553:4553 -d expert360/mountebank --configfile /imposters/jira-api.ejs --allowInjection

Inorder to get mount bank mock data 
------------------------------------

http://localhost:2525/imposters/4553
   
   OR

SAMPLE RESPONSE given in jira-api:

{
    "protocol": "http",
    "port": 4553,
    "name": "jira api",
    "defaultResponse": {
        "statusCode": 200,
        "headers": {
            "Content-Type": "application/json; charset=utf-8"
        },
        "body": [{
            "issueKey": "TEST-1",
            "fields": {
                "storyPoints": 1
            }
        },{
            "issueKey": "TEST-2",
            "fields": {
                "storyPoints": 2
            }
        }]
    },
    "stubs": [
        {
            "predicates": [
                {
                    "equals": {
                        "method": "GET",
                        "path": "/rest/api/2/search?q=type=Bug"
                    }
                }
            ],
            "responses": [
                {
                    "is": {
                        "statusCode": 200,
                        "body": [{
                            "issueKey": "TEST-1",
                            "fields": {
                                "storyPoints": 1
                            }
                        },{
                            "issueKey": "TEST-2",
                            "fields": {
                                "storyPoints": 5
                            }
                        },{
                            "issueKey": "TEST-3",
                            "fields": {
                                "storyPoints": 3
                            }
                        }]
                    }
                }
            ]
        }
    ]
}

-----------------------------------------------
| Use elasticMq in local machine using docker |
-----------------------------------------------

docker run -p 9324:9324 -v "$PWD/sqs/queue.conf:/queue.conf" graze/sqs-local

