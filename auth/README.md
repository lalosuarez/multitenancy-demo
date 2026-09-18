# OAuth Authorization Server

This is an an oauth authorization server. It issues tokens for users that succesfully authenticate.
Adds a claim in the JWT token that contains the right tenant, so downstream services can use that to pick
the righr resources for that tenant.

- OAuth server
- OAuth clients - Interacts with the OAuth server to then handle request to the Resource servers
- Resource server (Downstream Service)

Login URI: http://localhost:9090
Logout URI: http://localhost:9090/logout
