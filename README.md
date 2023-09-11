# Spring MVC Film CRUD

## Overview
This project allows a user to interact with an example database populated
with different films. 

Users can do searches, add new films, edit existing films, and delete films.

It presents a simple form model to obtain user information and
updates the database accordingly.

## Technologies Used

* Java
* Eclipse
* Git
* Apache Tomcat
* MySQL
* Spring
* JSPs

## Lessons Learned

In this project we learned to utilize two important design patterns.

The first was the DAO, or Database Access Object. In this pattern
we use a java interface to represent an API for our database access methods.
This allows us to have different implementations and even make "mock data" for 
testing functionality.

The second important design patter was the MVC, or Model View Controller.
This pattern allowed us make "controller" objects, which implemented the
logic behind our website's functionality. From our controller object, we
can update the model and have Spring and JSP's send that off to a view on 
the webpage. 

Both of these design patterns helped us keep different concerns in the app
in separate modules, and helped us maintain some order in our app.

