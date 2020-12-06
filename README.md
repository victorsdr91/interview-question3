Interview question
==================
This is a very basic spring-boot app that serves 4 apis to create our custom forum. Run it (using `mvn spring-boot:run`) or your favorite IDE.

For testing purposes you can execute the calls with Postman one time the app is running.

With this application you can run 4 different APIs:

 - Post new questions
 - Post replies to questions
 - Get a thread of a selected question
 - Get a list of the questions stored

### `POST http://localhost:5000/questions`
#### Post a new question in the forum.
Example:

JSON Body:
```json
{
  "author": "Daniel",
  "message": "Message text"
}
```
201 Status Response:
```json
{
  "id": 1,
  "author": "Daniel",
  "message": "Message text",
  "replies": 0
}
```

### ` POST http://localhost:5000/questions/{questionId}/reply`
#### Posts a new reply for the question passed as param. 
Note: RecordNotFoundException can be thrown if the question attached to the reply is not found on the storage

JSON Body:
```json
{
  "author": "Reply author",
  "message": "Message reply text"
}
```
201 Status response:
```json
{
  "questionId": 1,
  "id": 5,
  "author": "Reply author",
  "message": "Message reply text"
}
```

### `GET http://localhost:5000/questions/{questionId}` 
#### Returns a thread of the question passed as param
Note: RecordNotFoundException can be thrown if the selected question is not found on the storage

200 Status response:
```json
{
  "id": 1,
  "author": "Daniel",
  "message": "Message text",
  "replies": [
    {
       "id": 5,
       "author": "Reply author",
       "message": "Message reply text"
    },
    ...
  ]
}
```

### `GET http://localhost:5000/questions`
#### Returns the list of all the threads stored
200 Status response:
```json
[
    {
      "id": 1,
      "author": "Daniel",
      "message": "Message text",     
      "replies": 0
    },
    ...
]
```
