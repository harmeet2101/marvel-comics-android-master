# Marvel Comic App

Marvel Comic App

## Libraries
 * Google AppCompat Support Libraries
 * Google Material Design Support Libraries
 * Retrofit - for Networking
 * Gson - for parsing the JSON responses
 * Glide - for image loading
 * Android dataBinding
 * Dagger - for dependency injection
 * RxJava/RxAndroid - for reactive programming
 * JUnit - for testing
 * Mockito-kotlin - for mocking & stubbing objects
 * MockWebServer - for creating a "mock" server to run tests
 * Realm for data persistence
 * Checkstyle - for static code analysis
 
 ## Building and running the project
 * Android Studio 2.3+
 * Android Gradle Plugin `2.3.2`
 * Create a `secrets.properties` file - this is where you place your public and private keys.
 * Run this command to create it with the template in `secrets-sample.properties` file.
 ```
 mv secrets-sample.properties secrets.properties
 ```
 * Paste your private and public keys in the respective places.
 
 
 ## Architecture
 This application uses the Mode-View-Presenter (popularly known as MVP) architecture. The philosophy behind
 this architecture is to enable separation of concerns and ultimately a very modular application. 
 
 This architecture was chosen because it provides the opportunity to separate concerns and implement
 "SOLID" principles which makes the application more robust and the code base scalable and maintainable.
 
 This due separation of moving parts makes the app more testable with unit tests.
 
 ### Model Layer
 This layer of the application includes everything that has to do with data fetching. It makes use of the
 *repository pattern* to abstract the details of fetching the data via an interface and that interface is supplied
 to the views via the presenters. 
   
 Here, the repositories implement the logic of fetching data and expose the data as RxJava observable streams.
 These observables are then subscribed to in the presenter layer, which then communicates with the
 passive views as necessary.
 
 ## View Layer
 The View Layer consists of the passive views which are basically interfaces that the presenter interacts
 with. The actual Android Views then implement these passive view interfaces, but are only responsible for
 actual implementation of what the passive view is supposed to do e.g Show the progress screen. All business logic has been delegated
 to the presenter layer.
 
 ## Presenter Layer
 Here is where all the logic of making requests, doing calculation and every business logic lies. This is also the layer that
 acts as a middle man between the model and view layers. The presenters instruct the passive views
 what to do based on the data available.
   
 ## Offline Functionality
 This application works offline. It does so by making use of a strategy such that the data is fetched
  from multiple sources: from memory (if the data was recently fetched), from the realm database (if we have 
  recently fetched the data) and from the API if we want to refresh the data source, or when we are not 
  able to load from memory or database.
  
  This strategy is implemented at the repository level and the method of combining observables
  using RxJava was used.
 
 ## Solving the Budget Problem
 The Budget problem seems like a typical dynamic programming problem - where one is trying to optimize
 a certain parameter based on some constraints. Specifically, this problem is a type of Knapsack problem - **0 or 1 knapsack problem.**
 
 The solution uses dynamic programming approach - where we have overlapping sub-problems (as we have in recurssion) 
 and optimal substructures. We emply memoization by building a table to store the progress as we work our way bottom-up.
 
 The implementation of this is in the `BudgetCalculator` class.
 
 ## Testing
 This projects has local unit tests powered by JUnit and mockito for mocking objects.
 
 ## Code Quality
 This project uses Checkstyle for static code analysis - it uses the Google Java Style guideline rules
 to ensure that the code always follows the guide. Typically, in an environment where we have continuous 
 integration, these checks will run against every pull request raised, so once can fix issues before it 
 gets merged with the rest of the codebase.
 
 ## Possible Improvements
 
 * Adapting the current architecture into "Clean" architecture - by having usecases or interactors
 to further break down the units of tasks in the project. 
 * Splitting some packages into modules. This helps in many ways including enforcing the separation of
 Android APIs from pure java modules. Splitting into modules also helps with build speeds by leveraging
 Gradle's incremental build feature.
 * Continuous Integration - to continuously and automatically run the tests and other tasks as we introduce new code to the repository
 * More and more tests (especially UI tests)
 
 ## Additional Notes
 * It is noteworthy that the comic items returned from the Marvel API sometimes have weird data. Some have no description,
 some have no price, some have no page count and so on.