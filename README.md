

**Nike Demo**
-------------


----------


Table of Contents  

 - [My Thoughts](#my-thoughts)  
 - [Documentation](#documentation)
 - [Setup](#setup)
 - [Installing New DataSouce](#installing-new-db)
 - [Installing New Shuffle](#installing-new-shuffle)
 -  [Error Response](#error-response)
 -  [Endpoints](#endpoints)

----------


My Thoughts
===========


----------


Firstly I would like to say thanks for the opportunity, I appreciate it, and I hope you enjoy reading my project.

That said, I stayed true to the time limit. I only worked on Saturday. Because of the time constraints I decided to cut some things off (like you asked in the requirements). Small disclaimer, I read the specs on Wednesday so I had some time to plan it before I began coding. I decided to make sure all the features I deemed invaluable where solid before I splurged on the extras. I did however make sure that any features I did not implement where very easy to do so in the future by me or anyone else.

First lets begin with the features that I implemented. 

 - I used gradle and jetty
 - I created all the logic for the card deck
 - I created all the endpoints
 - I designed my own data and API structure
 - I used sessions for inmemory storage
 - I designed this so that you can dependency injection any type of db
 - I only implemented one shuffle algorithm 
 - I designed algorithm's this same way as db
 - An exception mapper to give us control over what we print out on failure
 - Custom exception to be more verbose about our errors
 - And I wrote many test cases so I can test my code quickly after I make changes

Next the features I left stubs for but did not implement

 - I created an interface for shuffle algorithms. The deck object is
   plug and play for any shuffle algorithm I can imagine. 
 - I left my self a stub to write a .properties file so I can change algorithm at deploy time
 - I also left my self a stub as requested to allow myself an easy change of database.


All in all I was able to follow my game plan. I figured that an MVP card shuffler did not need to have multiple modes of shuffling, it also did not need to have on deploy shuffle changes. I also realized I could spend hours trying to write all the imaginative algorithms in the world and it really wouldn't change the core functionality of the service. 

I figure the most important part was a well designed production ready simple card shuffler with data persistence. 


----------

Documentation
=============


Setup
-----

In order to start the software just run the class called **Starter.java**.  This should open your default browser to a page with a link to this documentation pointer to the endpoints. The default uri is http://localhost:8081/NikeDemo. If you would like to run on a different port or default path simply change the **DEFAULT_PORT**and the **DEFAULT_PATH** variable , this would ideally go into a config file.

Once started if you would like to make sure everything is working well you may run the project as a jUnit test.

These junit tests will run each endpoint. If you would like to run them your self, please refer to the endpoint section of this documentation.


----------


Installing New Db
------------------------
First create a class in com.gigamog.datastore package. Make sure to implement DataStorageModule interface.
Once this is done you can write your code in their responding methods.

To use your new datastore you go into CardController class inside com.gigamog.controller package. Originally I wanted to put this easy switch up top under:

```
	@Context
	HttpServletRequest request;

```

but I coulnd't get passed a runtime error in the time frame.

That said, you can replace each instance of 

```
new InSession(request)
```
with your own datastore class.


----------


Installing New Shuffle
------------------------

First create a class in the com.gigamog.shufflealgorithm package. Make sure to implement ShuffleAlgorithm interface. In the run method write your algorithm. 

Now that the class has been created we have to add the class to the pseudo factory method. In a future update we will dynamically add this class at the server start up.  But because we didn't have time we have to do some work.

Go to the ConfigValues class inside com.gigamog.utils. Inside the getShuffleAlgorithm method just add your algorithm like so:

```
	public static ShuffleAlgorithm getShuffleAlgorithm() {
		ShuffleAlgorithm shuffle;
		
		switch (SHUFFLE_ALGORITHM) {

		case "modern":
			shuffle = new ModernShuffleAlgorithm();
			break;
		case "<< new algorithm name >>":
			shuffle = << instantiate your algorithm here>>;
		default:

			shuffle = new ModernShuffleAlgorithm();
			log.log(Level.WARNING, "");
		}
		return shuffle;

	}
```

Once this is done you can change the **SHUFFLE_ALGORITHM** variable up to to your algorithm.  If we had time this wouldn't be a step we would get it from a config file.


----------




Error Response
--------------
All errors should have the same schema and if possible a descriptive response. 
A sample as follows:

```
{
   "status":404,
   "errorMessage":"There doesnt seem to be a deck by that name"
}
```


----------


Endpoints
---------


| Method  | Description  | path | Details |
|---|---|---|---|
| PUT | creation of a new named deck | /v1/card/{deck_name}| [link](#creating-a-deck) |
| POST | request to shuffle an existing named deck | /v1/card/{deck_name} | [link](#shuffling-a-deck) |
| GET | list of the current decks persisted in the service | /v1/card | [link](#list-of-all-decks) |
| GET |  a named deck in its current sorted/shuffled order | /v1/card/{deck_name} | [link](#get-a-single-deck) |
| DELETE | delete's a named deck | /v1/card/{deck_name} | [link](#delete-a-deck) |


----------

Creating a Deck
---------------

**Method:** PUT

**Path:** /v1/card/{deck_name}

**Description:** creation of a new named deck. This endpoint will overwrite an existing deck with the same name

**Response Code:** 200

**Response:**

```
{
   "deckName":"standard",
   "deck":[
      {
         "deckName":"standard",
         "rank":"Ace",
         "suit":"Spades"
      },
      {
         "deckName":"standard",
         "rank":"2",
         "suit":"Spades"
      },
      {
         "deckName":"standard",
         "rank":"King",
         "suit":"Clubs"
      }
   ]
}
```


----------
Shuffling a Deck
---------------

**Method:** POST

**Path:** /v1/card/{deck_name}

**Description:** Request to shuffle an existing named deck. This endpoint will also return a shuffled deck. I decided that a user will more often than not want his deck after shuffling. This will save us one trip to the service.

**Response Code:** 200

**Response:**

```
{
   "deckName":"standard",
   "deck":[
      {
         "deckName":"standard",
         "rank":"Ace",
         "suit":"Spades"
      },
      {
         "deckName":"standard",
         "rank":"2",
         "suit":"Spades"
      },
      {
         "deckName":"standard",
         "rank":"King",
         "suit":"Clubs"
      }
   ]
}
```


----------
List of all Decks
---------------

**Method:** GET

**Path:** /v1/card/

**Description:** list of the current decks persisted in the service. I also added number of decks. It just seems like a good idea.
 
**Response Code:** 200

**Response:**

```
{
   "numberOfDecks":2,
   "cardDecks":[
      {
         "deckName":"deckCreated0",
         "deck":[
            {
               "deckName":"deckCreated0",
               "rank":"Ace",
               "suit":"Spades"
            },
            {
               "deckName":"deckCreated0",
               "rank":"King",
               "suit":"Clubs"
            }
         ]
      },
      {
         "deckName":"deckCreated1",
         "deck":[
            {
               "deckName":"deckCreated1",
               "rank":"Ace",
               "suit":"Spades"
            },
            {
               "deckName":"deckCreated1",
               "rank":"2",
               "suit":"Spades"
            },
            {
               "deckName":"deckCreated2",
               "rank":"King",
               "suit":"Clubs"
            }
         ]
      }
   ]
}
```


----------
Get a Single Deck
---------------

**Method:** GET

**Path:** /v1/card/{deck_name}

**Description:** a named deck in its current sorted/shuffled order

**Response Code:** 200

**Response:**

```
{
   "deckName":"standard",
   "deck":[
      {
         "deckName":"standard",
         "rank":"Ace",
         "suit":"Spades"
      },
      {
         "deckName":"standard",
         "rank":"2",
         "suit":"Spades"
      },
      {
         "deckName":"standard",
         "rank":"King",
         "suit":"Clubs"
      }
   ]
}
```


----------
Delete a Deck
---------------

**Method:** DELETE

**Path:** /v1/card/{deck_name}

**Description:** delete's a named deck 

**Response Code:** 204

**Response:**


```
Empty Response
```
