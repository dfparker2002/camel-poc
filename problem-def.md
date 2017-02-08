#Rest intergration Camel PoC

```java
    from("file:data/inbox?noop=true")
                        .log("${body}")
                        .setHeader(Exchange.HTTP_QUERY,simple("name=${body}"))
                        .enrich("http4://localhost:8080/abe/booking/")
                        .convertBodyTo(String.class)
                        .log("${body}")
                .to("file:data/outbox");
```

```
2017-02-08 13:23:03.823  INFO 40012 --- [nio-8080-exec-1] o.a.c.c.s.CamelHttpTransportServlet      : Initialized CamelHttpTransportServlet[name=CamelServlet, contextPath=]
2017-02-08 13:23:03.877  INFO 40012 --- [nio-8080-exec-1] books-api                                : 123
2017-02-08 13:23:04.201  INFO 40012 --- [nio-8080-exec-1] o.a.camel.component.http4.HttpComponent  : Created ClientConnectionManager org.apache.http.impl.conn.PoolingHttpClientConnectionManager@7c71cbff
2017-02-08 13:23:04.330 ERROR 40012 --- [nio-8080-exec-1] o.a.camel.processor.DefaultErrorHandler  : Failed delivery for (MessageId: ID-DESKTOP-I65J6PP-12019-1486560162192-0-3 on ExchangeId: ID-DESKTOP-I65J6PP-12019-1486560162
192-0-2). Exhausted after delivery attempt: 1 caught: java.lang.IllegalArgumentException: Invalid uri: /camel-rest-jpa/books/123/books/123?name=123. If you are forwarding/bridging http endpoints, then enable the bridgeEndpoint
 option on the endpoint: http4://localhost:8080/some/booking/

Message History
---------------------------------------------------------------------------------------------------------------------------------------
RouteId              ProcessorId          Processor                                                                        Elapsed (ms)
[books-api         ] [books-api         ] [servlet:/books/%7Bid%7D?httpMethodRestrict=GET                                ] [       459]
[books-api         ] [restBinding1      ] [                                                                              ] [         7]
[books-api         ] [log1              ] [log                                                                           ] [         1]
[books-api         ] [setHeader1        ] [setHeader[CamelHttpQuery]                                                     ] [         2]
[books-api         ] [enrich1           ] [enrich[constant{http4://localhost:8080/some/booking/}]                        ] [       441]

Stacktrace
---------------------------------------------------------------------------------------------------------------------------------------

java.lang.IllegalArgumentException: Invalid uri: /camel-rest-jpa/books/123/books/123?name=123. If you are forwarding/bridging http endpoints, then enable the bridgeEndpoint option on the endpoint: http4://localhost:8080/some/b
ooking/
``` 

