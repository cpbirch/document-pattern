# Embedded Document Example
An example application to demonstrate how flexible document APIs are and how documents can be evaluated by inspecting them.

In financial institutions or in government, there is a common user-journey pattern:

APPLY -> ASSESS -> PAY -> REPAY (replay is not usually a government concern)

Using a mortgage as an example, banks vary their terms and conditions over periods of time and have different demands on evidence, verification etc.  In a relational database, ORM and fine grained object world, the application form is closely coupled to storage and to APIs for evaluating mortgage applications.  This means a change in policy requires a change in all of the steps of the user-journey above.

This code base serves as an example to show how the right level of encapsulation allows applications to be decoupled from their processing.

I'd like to thank Jim Barrit, Shodan Seth for defining this approach and to Martin Fowler for giving me the courage to do this at a client.

## Getting Started

This is a clojure project built by leiningen.  You'll need to both [Clojure](http://clojure.org) and [Leiningen](http://leiningen.org) installed.

You'll also need MongoDB running on it's standard port 27027 without authentication.

Checkout the source, then on the command line enter

    lein midje

To run the code, try

    lein run config/equip.yml

## Flexible Data Creation Examples

Create a ship document

    curl -H "Content-Type: application/json" -X POST -d '{"type": "Ship"}' http://localhost:9090/equipment

Note the equipment ID that was returned, likely it will be 0 if this is the first post you've done.

Now add additional information about the ship

    curl -H "Content-Type: application/json" -X POST -d '{"name": "RRS Boaty McBoatface", "sub-type": "Research Vessel", "length": 129, "helicopters": 2}' http://localhost:9090/equipment/0

Feel free to repeat the above step, adding any other fields you think are interesting.

To see the full document, try

    curl http://localhost:9090/equipment/0

