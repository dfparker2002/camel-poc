package hello;

import org.springframework.stereotype.Component;

@Component
public class Database {
    public String findAll(){
        return "Hello World";
    }
}
