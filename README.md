[![Build Status](https://travis-ci.org/jadelomeiri/cars-api.svg?branch=master)](https://travis-ci.org/jadelomeiri/cars-api)

# Cars Web Dev Test

# Cars API

Please create a new git repo (don't fork this one) and follow the instructions below.

Once you have finished, please email us the URL to your repo.

Please make commits as you go (> 1 and < 100) and provide instructions to run the application.

## The Story

"As a consumer of a RESTFul API, I would like to be able to add, retrieve, and remove *cars*."

### The Acceptance Criteria

* Priority order is:
    1. add
    2. retrieve 
    3. remove
* A *car* has the following attributes:
    * id*
    * make
    * model
    * colour
    * year
* We do not require persistent storage at this stage (an in-memory store is fine).

*generated by the application on entry

(If the story needs to be broken down to fit into the sprint, please do so.)

## Subsequent stories

These are stretch goals if you have time (you can do them in any order):

* "As a Developer, I want my code to be covered by tests, so I know if a change has broken something"
* "As a Consumer of the API, when reading the car model information I would like to see an additional field containing a string of a few words that sound like the model of the cars I have added" (using [http://www.datamuse.com/api/](http://www.datamuse.com/api/) as the source).
* "As a Consumer of the API, I would like to be able to update my existing cars"
* "As a Consumer of the API, I would like any cars I add through the API to persist between application restarts (persistent storage)"
* "As a Consumer of the API, I would like cars to be represented as two separate, hierarchically linked resources: 
    1. *Make*
    1. *Model*

## Limitations

You can use whatever online resources you want (aside from copy-pasting large chunks of code).

You are encouraged to use any frameworks or libraries you feel comfortable with.

You will be asked about the code you submit so you should be able to explain why every line is there.

<hr>

# Solution
## 1- Technologies
For this exercise I have chosen to use the [Grails](https://grails.org) framework.  
### What is Grails? Why Grails?  
Grails is a Groovy-based web framework inspired by Ruby on Rails.  
It is built on top of Spring Boot and is described as Spring Boot on steroids.  
It boosts incredibly <strong>developers' productivity & satisfaction</strong>.  
Grails developers benefit from:
- Accessing databases with [GORM](https://gorm.grails.org) (ORM framework built on top of Hibernate).  
- Writing beautiful & highly expressive tests with [Spock](https://spockframework.org).

### Versions used:
| Technology | Version   |  
| ---------- | --------- |  
| JDK        | 1.8.0_211 | 
| Groovy     | 2.4.10    |  
| Grails     | 3.3.9     |  
| Gradle     | 3.5       |  

## 2- Assumptions & Design Decisions
Throughout the project I tried to follow the <strong>KISS principle</strong> and tried to <strong>not over-engineer</strong> things.  
Also I completed the project as if there was a real client out there. I tried to simulate a real-world scenario where DDD would be followed and short feedback loops would be achieved.  

### Domain Modelling
Initially requirements were still not clear. With no context about the client and his needs it is hard to know exactly how the domain should be modelled.    
What is the purpose of the app? How is it going to be used?  
In real life, at this stage it is crucial to understand the domain. Decisions taken here could affect the whole project for a very long time.   

With no client around I started by assuming the simplest:
 - Purpose of app is just to gather data.
 - We're not worried about correctness of the data (for ex. a car could be of model AnyRandomModelName)
 - Model can be simple & no need for any normalization (at least in a first stage).
 - No unique restrictions on any of the data (for ex. the same car with the same properties can exist multiple times).
 - All car properties are required (for ex. a car can't exist without a colour)

So my initial model consisted of single Car domain containing: id, make, model, colour, year.

    class Car {
       String make
       String model
       String colour
       int year
   
       static constraints = {
           make nullable: false, blank: false
           model nullable: false, blank: false
           colour nullable: false, blank: false
           year nullable: false, blank: false
       }
       
####Note on encapsulation:
It might look like encapsulation hasn't been respected, but it was.  
This is one awesome way that Grails works.  
Under the hood, Grails injects setter & getter at runtime.  
When doing Car.make, the make property is not being accessed directly.  
Car.make is exactly the same as Car.getMake().  
As a matter of fact developers can user Car.getMake() & Car.setMake() without having the methods explicitly coded.  

#### Domain Model Refactoring
Fast forward towards the end of the project, I now started working on:  
>"As a Consumer of the API, I would like cars to be represented as two separate, hierarchically linked resources:
  i)  Make
  ii) Model

Now with the need of having resources linked hierarchically, it made sense to refactor the domain.  
My domain model now consisted of 3 separate domains: Make, Model, and Car.
New set of assumptions:
 - Make & Model have a 1-to-many relation. 1 Make can have multiple Models (ex. Kia can have Picanto & Cerato).  
 - The Model belongs to the Make which means deleting the Make would delete the Models
 - Model & Car have a 1-to-many relation. 1 Model can have multiple Cars (ex. Cerato can have a red car & a blue car).  
 - The car belongs to the Model. Deleting the Model would delete the Car.
 - Correctness of the data is now more important
 - A car is not directly linked to a Make to avoid bad linking (ex. Red 2019 Mercedes Cerato shouldn't exist).  
 - Make names are unique (ex. can have only 1 Mercedes)
 - Model names are unique (ex. can have only 1 Cerato)  
 - Cars are not unique. We have assumed our client is not using the app as an encyclopedia of cars. We'd need to double check directly with them.  
 
### REST API  
For my REST API, I tried to use best practices for URL names as well as proper use of HTTP methods.  

Initially, when my model was simple and consisted of only a Car domain, I had the following endpoints exposed:  
Add: /car/ [POST]  
Retrieve all cars: /car/ [GET]  
Retrieve specific car: /car/${id} [GET]  
Remove: /car/${id} [DELTE]  
Update: /car/${id} [PUT]  

####Note on Update:  
I worked on the Update functionality as part of the first story.  
Reason is it was so easy to have it from the beginning with Grails. It didn't require extra effort.  
Lucky for us, our client actually needed it.

#### Additional Endpoints
With the model refactoring and with the new requirement for linked resources, I had to expose my new resources.  
I now have Models & Makes exposed in a similar way to Car.  
I now also have:  
Add make: /make/ [POST]  
Retrieve all makes: /make/ [GET]  
Retrieve specific make: /make/${id} [GET]  
Remove make: /make/${id} [DELTE]  
Update make: /make/${id} [PUT]  
Add model: /model/ [POST]  
Retrieve all models: /model/ [GET]  
Retrieve specific make: /model/${id} [GET]  
Remove model: /model/${id} [DELTE]  
Update model: /model/${id} [PUT]  

Assumption here was that client would want to add/remove/update Makes and Models that are or aren't already in the database.  

##### HAL
To represent my linked resources I decided to follow the HAL standard.  
Navigation is through the API is now easier.


## 3- Testing:  
I have decided to write tests as soon as I was writing code.
I believe tests should be part of the DONE definition of stories rather than separate stories  
In my opinion, that should be the case for at least unit tests.  
But this would usually need to be discussed and agreed with the team.  

All domains and controllers have been tested.  

Types of testing done:  
- Unit testing
- Integration testing 
    

## 5- Persistence / db:  
I initially started with an in-memory H2 database.  
Later, when I got to the story about persistence, I added persistence to a Postres database hosted in the cloud.  

For your convenience I kept both datasources.  
H2 is used in the dev environment & Postgres is used in the test environment.  
  
## 6- Words sounding like model:  
Story specifies few words as being the requirement.   
Need to understand from client how many is considered a few?  
Assumption was to put a limit of 3 words   

## 8- Test coverage - clover:
Having finished my project on time, I decided to add Clover for test coverage.  
Not that test coverage is always the best indicator of good code.  
But getting a 97.4% coverage can be quite pleasing and can increase the confidence in the codebase.  


## 9 - How to test & run app:
Grails provides a great CLI with plenty of commands and tasks.  
But tasks are also available as Gradle tasks.  
Sor for common tasks (clean, compile, run, test, assemble) there's no need to install Grails. Gradle is sufficient.  
Even better, every Grails project comes with an embedded Gradle wrapper. There's no need to even install Gradle.  Java is all what we need!  

### Commands
For all commands, first open your OS's command line.  
then <strong>cd</strong> to the project directory.

### Run app
With Grails CLI:  

    grails run-app
    grails dev run-app //to run in dev environment
    grails test run-app //to run in test environment
    
Without Grails CLI:  

    gradlew bootRun
    gradlew bootRun -Dgrails.env=dev //to run in dev environment
    gradlew bootRun -Dgrails.env=test //to run in test environment    
    
#### Note on environments:
dev environment is configured to work with an in-memory H2 databse.  
test environment is configured to work with a Postgre databse hosted in the cloud.

### Test app
With Grails CLI:  

    grails test-app
    grails dev test-app //to test in dev environment
    grails test test-app //to test in test environment
    grails test-app --integration //to run integration tests only
    grails test-app --unit //to run unit tests only
    
Without Grails CLI:  

    gradlew test
    gradlew test -Dgrails.env=dev //to test in dev environment
    gradlew test -Dgrails.env=test //to test in test environment    
    gradlew integrationTest //to run integration tests only
    
On completion Grails provides beautiful Spock test reports.  

### Test coverage


    gradlew cloverGenerateReport
    
### Using the app
Once up and running, the API can be accessed through the browser (for GET methods only).  
The whole API can be used with CURL or any API testing application.  
I like using [Postman](https://www.getpostman.com/).  

The application will be running on port 8080 and can be accessed through:  

    localhost:8080
    localhost:8080/car/1 //for ex. to retrieve car with id=1. 

Please see [Additional Endpoints](#Additional Endpoints)  


# Many thanks!
