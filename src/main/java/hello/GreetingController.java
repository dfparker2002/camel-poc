package hello;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    private static final String template = "OVO/%s";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/some/booking/")
    public Booking greeting(@RequestParam(value="name", defaultValue="5492") String name) {
        return new Booking(counter.incrementAndGet(),
                            String.format(template, name));
    }
}
