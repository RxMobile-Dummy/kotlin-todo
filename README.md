# kotlin-todo

# Android-mobile-app

RemindMe :
- Created a Kotlin based in android with jetpack compose and hilt injection for dependency injection

<img src="https://github.com/mm-itservices/Android-mobile-app/blob/main/media/coupon_list.gif" width="200" style="max-width:100%;">   <img src="https://github.com/mm-itservices/Android-mobile-app/blob/main/media/dashboard_page.png" width="200" style="max-width:100%;"></br></br>


Features

- Task List Screen
   - It contains list of task with color whichever saved when adding task and along with time schedule for respective task

- Add Task Screen
   - User can add task with color and set alarm for task.

- Task Detail Screen
   - User can edit task with details like task title,description,set alarm for that and select snooze time for respective task.

- Search Screen
   - It Search task according to category and category with name.

- Category List Screen
  -  It contains list of category and by clicking on item user can go into edit caetgory section where they can edit category

- Add category Screen
  -  User can add category with color.

- Setting screen
 -    In this screen notifications setting for notification like enable and disable 
 -    Alert setting for snooze time
 -    App theme like dark theme,light theme and sytem default theme.

App Architecture :

Based on MVVM architecture and pattern.

<img src="https://github.com/mm-itservices/Android-mobile-app/blob/main/media/mvc_architecture.png" width="500" style="max-width:500%;">

We have follow MVVM architecture in this project because of it treats Activity's classes and XML files and ViewModel classes are where we write our business logic. & it completely separates an app's UI from its logic.

Architecture Components

- Model: This holds the data of the application. It cannot directly talk to the View. Generally, it’s recommended to expose the data to the ViewModel through Observables.

- View: It represents the UI of the application devoid of any Application Logic. It observes the ViewModel.

- ViewModel: It acts as a link between the Model and the View. It’s responsible for transforming the data from the Model. It provides data streams to the View. It also uses hooks or callbacks to update the View. It’ll ask for the data from the Model.

This app uses multi module architecture.The application also relies heavily in modularization for better separation of concerns and encapsulation.

Let's take a look in each major module of the application:

-app - The Application module. It contains all the initialization logic for the Android environment and starts the Jetpack Navigation Compose Graph.

-features - The module/folder containing all the features (visual or not) from the application

-domain - The modules containing the most important part of the application: the business logic. This module depends only on itself and all interaction it does is via dependency inversion.

-data - The module containing the data (local, remote, light etc) from the app

Tech components :

- Room Persistence Library : A local database that servers as a single source of truth for data presented to the user.

- MVVM architecture : It’s pattern provides an easy way to structure the project codes.The reason why MVC is accepted is that it provides modularity, testability, and a maintainable codebase.

- RxJava For Room : For implementing RxJava support for Room

- Hilt: It is a dependency injection library for Android that reduces the boilerplate of doing manual dependency injection in your project.

- JetPack Compose - Jetpack Compose is Android’s modern toolkit for building native UI. It simplifies and accelerates UI development on Android.

- Kotlin Coroutines- kotlinx.coroutines is a rich library for coroutines developed by JetBrains. It contains a number of high-level coroutine-enabled primitives that this guide covers, including launch, async and others.

- Kotlin Flow - A suspending function asynchronously returns a single value, but how can we return multiple asynchronously computed values? This is where Kotlin Flows come in.

- DataStore  - Jetpack DataStore is a data storage solution that allows you to store key-value pairs or typed objects with protocol buffers. DataStore uses Kotlin coroutines and Flow to store data asynchronously, consistently, and transactionally.

Steps to Install Project:
1) Take Git clone of the repository (https) by cliking on the code section in github
2) Open clone in Android studio
3) Clean and Rebuild the project
4) Run the app into device

Package Structure
