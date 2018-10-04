# WunderAndroidApp Programming Test

## Task
As part of the test assignment we would like to ask you to build an app which
tackles the following problem:

The app should fetch a JSON feed of cars:
➔ https://s3-us-west-2.amazonaws.com/wunderbucket/locations.json

It should persist them and display the information in two different screens:

Screen 1:
Display all the cars information in a list.

Screen 2:
Display all the cars in the map.

Selecting the car in the list should open the map screen, zoom on that car’s
location and display its name on top of the map pin.

Selecting the car on the map should display its name on top of the map pin.

## Questions/Discussion
**Architecture**

I structured my code using MVP (Model View Presenter). MVP is a derivation of the MVC (Model View Controller) architectural pattern which is mainly used for building a simple user inteface. I chose to use MVP to allow the View to be more seperated from the Model as much as possible compared to MVC. It also allows for easier creation unit tests. 

Any other structure could have been used, but with a simple task like this one, I thought a simple architecture would suffice.

I have spread out my project into 4 classes:

`HttpHandler.java`: Responsible for establishing an HTTP URL connection using the provided URL above to the JSON feed using a GET method.

`MainActivityPresenter.java`: Works as a middle man between the view (MainActivityPresenter.java) and the model (Model.java).

`Model.java`: Contains all the logic for parsing the JSON string and populating our arrayList with the necessary information.

`Main.java`: This leads the whole orchestra. It listens to user's clicks and also updates the user's view whenever an item on the list is selected. 

**Testability**

`UnitTest.java`: A simple class that contains a single unit test for testing the model's logic by passing in a single JSON string that contains a single car to test how the function populates the arrayList carList.