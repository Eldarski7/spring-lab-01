package kz.iitu.springlab.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.DoubleSummaryStatistics;

@RestController
@RequestMapping("/api")
public class HelloController {

    @Value("${app.owner:unknown}")
    private String owner;

    @GetMapping("/hello")
    public Greeting hello(@RequestParam(defaultValue = "world") String name) {
        return new Greeting("Hello, " + name + "!", owner, LocalDateTime.now());
    }

    @GetMapping("/info")
    public Info info() {
        return new Info(owner,
                        System.getProperty("java.version"),
                        Runtime.getRuntime().availableProcessors());
    }

    // Individual assignment, variant 6: min, max and average of a comma-separated list
    @GetMapping("/stats")
    public Stats stats(@RequestParam(required = false) String numbers) {
        if (numbers == null || numbers.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Parameter 'numbers' is required, for example ?numbers=1,2,3");
        }
        double[] values;
        try {
            values = Arrays.stream(numbers.split(","))
                    .map(String::trim)
                    .mapToDouble(Double::parseDouble)
                    .toArray();
        } catch (NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Parameter 'numbers' must contain only numbers separated by commas");
        }
        DoubleSummaryStatistics s = Arrays.stream(values).summaryStatistics();
        return new Stats(values.length, s.getMin(), s.getMax(), s.getAverage());
    }

    public record Greeting(String message, String owner, LocalDateTime timestamp) { }

    public record Info(String owner, String javaVersion, int cpuCores) { }

    public record Stats(int count, double min, double max, double average) { }
}
