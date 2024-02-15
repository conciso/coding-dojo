# Quarkus Code Kata
In this exercise we want to create a quarkus application. It should provide a REST-API with
the following properties

- Rest endpoint for creating a welcome message. A name is sent to the endpoint and the result is a
  personalizes welcome message.

```
Endpoint: POST: /welcome
Input: application/json
{
   "name":"string"
}

Output: application/json
{
    "message": "string"
}
```

- Rest endpoint for requesting all welcome messages which are created. For this task the names
  of the first endpoint are stored in a database and can be retrieved with the following
  interface:
```
Endpoint: GET: /welcome
Output: application/json
[
    {
        "message": "string"
    },
...
    {
        "message": "string"
    }
]
```

The goal for this kata is not a complete running application but a complete tested one.