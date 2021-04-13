
![logo](src/Image/logo.png)

## Introduction
In this project I have built the backend API for a news site for developers where users can create articles with topics and comments.  It doesn't require a GUI, so it's enough to be able to make requests and get plain json text responses via curl/Postman.

## Learning Objectives
* Understand the basic structure of a Spring application.
* Practice building, testing and consuming rest APIs.
* Learn about data modelling for real world applications.
* Learn how to interact with a relational database using an ORM tool implementing Spring JPA (Hibernate).

## Technologies used

- Spring Web
- Spring JPA
- PostgreSQL Driver
- Postman
- Docker

## Setup
You will need to configure the following dependencies in your `build.gradle`:
* Spring Web
* Spring JPA
* PostgreSQL Driver

#####Dependencies configuration 
____


`src/main/resources/application.properties` should also be properly configured:
```properties
spring.jpa.database=POSTGRESQL
spring.jpa.show-sql=true

spring.datasource.url=jdbc:postgresql://localhost:5431/demo
spring.datasource.username=demo_user
spring.datasource.password=demo_pass

spring.jpa.generate-ddl=true
spring.jpa.hibernate.ddl-auto=create
```

#####Run the project
____

- Clone and open in Intellij Idea IDE
- Change database connection config in `src/main/resources/application.properties`
- Install gradle dependencies
- Run the app using ``./gradlew bootRun``
- Browse ``http//localhost:8080/``

API Doc & Sample
----------------
### Articles
Article is the core entity in our project. It represents a news article with a unique **id**, **title**, **body** (article text content) and the
**authorName**.

Example JSON response when requesting an article:

```json
{
    "id": 1,
    "title": "10 reasons to learn Spring",
    "body": "In this article I'll be listing 10 reasons why you should learn spring and use it in your next project...",
    "authorName": "John Smith"
}
```

These are the endpoints for the article API that should exist:
| HTTP Method | HTTP Path | Action |
| ------------|-----------|--------|
| `GET`    | `/articles`      | return all articles. |
| `GET`    | `/articles/{id}` | return a specific article based on the provided id.|
| `POST`   | `/articles`      | create a new article.|
| `PUT`    | `/articles/{id}` | update the given article.|
| `DELETE` | `/articles/{id}` | delete the given article.|

### Comments
We want our visitors to be able to comment the different articles with a unique **id**, **body**, **authorName** (for the comment), and **article**
on which the comment was posted. Each article can have zero or more comments.

Example JSON response when requesting a comment:

```json
{
    "id": 1,
    "body": "This article is very well written",
    "authorName": "John Smith",
    "article": {
        "id": 1,
        "title": "10 reasons to learn Spring",
        "body": "In this article I'll be listing 10 reasons why you should learn spring and use it in your next project...",
        "authorName": "John Smith"
    }
}

```
With the following endpoints:

| HTTP Method | HTTP Path | Action |
| ------------|-----------|--------|
| `GET`    | `/articles/{articleId}/comments`    | return all comments on article given by `articleId`. |
| `GET`    | `/comments?authorName={authorName}` | return all comments made by author given by `authorName`. |
| `POST`   | `/articles/{articleId}/comments`    | create a new comment on article given by `articleId`. |
| `PUT`    | `/comments/{id}`                    | update the given comment. |
| `DELETE` | `/comments/{id}`                    | delete the given comment. |


### Topics
We want to categorize our articles by topics. Each topic can be applied to zero or many articles and each article can have zero or many topics.

Example JSON response when requesting an article should now be:

```json
{
    "id": 1,
    "title": "10 reasons to learn Spring",
    "body": "In this article I'll be listing 10 reasons why you should learn spring and use it in your next project...",
    "authorName": "John Smith",
    "topics": [
        {
            "id": 1,
            "name": "backend"
        },
        {
            "id": 2,
            "name": "java"
        },
        {
            "id": 3,
            "name": "spring"
        }
    ]
}
```
Endpoints:

| HTTP Method | HTTP Path | Action |
| ------------|-----------|--------|
| `GET`    | `/topics` | return all topics. |
| `GET`    | `/articles/{articleId}/topics` | return all topics associated with article given by `articleId`. |
| `POST`   | `/articles/{articleId}/topics` | associate the topic with the article given by `articleId`. If topic does not already exist, it is created. |
| `POST`   | `/topics` | create a new topic. |
| `PUT`    | `/topics/{id}` | update the given topic. |
| `DELETE` | `/topics/{id}` | delete the given topic. |
| `DELETE` | `/articles/{articleId}/topics/{topicId}` | delete the association of a topic for the given article. The topic & article themselves remain. |
| `GET`    | `/topics/{topicId}/articles` | return all articles associated with the topic given by `topicId`. |


Note
-----

You can test the performance of these endpoints in Postman

## About

I am a junior programmer and this is my first project and this project was done during the backend module for the KTH Software Development Academy.

[![Linkedin](https://i.stack.imgur.com/gVE0j.png) LinkedIn](https://www.linkedin.com/in/marta-louridob/?locale=en_US/)
&nbsp;

[![GitHub](https://i.stack.imgur.com/tskMh.png) GitHub](https://github.com/MartaLourido)


Copyright (c) [2021] [Marta Lourido]