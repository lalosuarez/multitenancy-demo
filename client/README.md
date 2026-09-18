# OAuth Client (Gateway)

This client acts as a gateway to serve UI and API resources:

:8080/index.html        => Calls UI service running in :8020/index.html
:8080/api/customers**   => Calls BE service running in :8081/api/customers

NOTE: It depends on the OAuth server to run, if that server is not running you'll ger error:

```shell
Caused by: java.lang.IllegalArgumentException: Unable to resolve Configuration with the provided Issuer of "http://localhost:9090"
```

URI: http://127.0.0.1:8080. Make sure to use IP instead of localhost, otherwise you'll get error:

```json
{
  "detail": "A tenant identifier must be specified for HTTP requests to /error",
  "instance": null,
  "properties": null,
  "status": 400,
  "title": "Bad Request",
  "type": null
}
```

Logout URI: http://127.0.0.1:8080/logout

When accessing the URI, the call gets redirected to the auth service for login http://localhost:9090/login.
Once the login is successfull, client makes a call to the downstream service with the proper header.
The downstream service is the one that has the domain login, for example: payments service, orders service, etc.
