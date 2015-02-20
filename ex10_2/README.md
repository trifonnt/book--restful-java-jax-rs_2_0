Link Headers and UriBuilder
===========================
This project is an example of using UriBuilder to enable HATEOAS through Link headers

System Requirements:
-------------------------

- Maven 3.0.4 or higher

Building the project:
-------------------------

1. In root directory

mvn clean install

This will build a WAR and run it with embedded Jetty


Example requests:
-------------------------
$ curl -vX HEAD http://localhost:8080/services/shop
$ curl -vX GET http://localhost:8080/services/shop

$ curl -vX GET http://localhost:8080/services/orders
$ curl -vX GET http://localhost:8080/services/orders/1

$ curl -vX GET http://localhost:8080/services/customers
$ curl -vX GET http://localhost:8080/services/customers/1