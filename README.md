The workflow of this API is as follows:

Admins will create the dispenser by specifying a flow_volume. This config will help to know how many liters of beer come out per second and be able to calculate the total spend.

Every time an attendee opens the tap of a dispenser to puts some beer, the API will receive a change on the corresponding dispenser to update the status to open. With this change,
 the API will start counting how much time the tap is open and be able to calculate the total price later

Once the attendee closes the tap of a dispenser, as the glass is full of beer, the API receives a change on the corresponding dispenser to update the status to close. 
At this moment, the API will stop counting and mark it closed.

To determine how much money is earned with each dispenser, we provide information about how many times a dispenser was used, for how long, and how much money was made with each service.

It is possible to check how much money was spent on each dispenser while an attendee is dispensing beer.

The technology stack includes Java 17, Spring Boot 3.2, Spring JPA, Maven and an in-memory H2 database 

