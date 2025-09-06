//    package com.example.orderprocessing.api;
//
//    import java.util.Collection;
//
//    import org.springframework.http.MediaType;
//    import org.springframework.web.bind.annotation.GetMapping;
//    import org.springframework.web.bind.annotation.PostMapping;
//    import org.springframework.web.bind.annotation.RequestParam;
//    import org.springframework.web.bind.annotation.PathVariable;
//    import org.springframework.web.bind.annotation.RequestMapping;
//    import org.springframework.web.bind.annotation.RestController;
//
//    import com.example.orderprocessing.domain.Order;
//    import com.example.orderprocessing.repository.InMemoryOrderRepository;
//    import com.example.orderprocessing.service.EventProcessor;
//    import com.example.orderprocessing.ingestion.EventReader;
//
//    @RestController
//    @RequestMapping("/api")
//    public class OrderController {
//
//        private final InMemoryOrderRepository repo;
//        private final EventReader reader;
//        private final EventProcessor processor;
//
//        public OrderController(InMemoryOrderRepository repo, EventReader reader, EventProcessor processor) {
//            this.repo = repo;
//            this.reader = reader;
//            this.processor = processor;
//        }
//
//        @GetMapping(value = "/orders", produces = MediaType.APPLICATION_JSON_VALUE)
//        public Collection<Order> allOrders() {
//            return repo.findAll();
//        }
//
//        @GetMapping(value = "/orders/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//        public Order getOrder(@PathVariable String id) {
//            return repo.findById(id);
//        }
//
//        @PostMapping("/ingest")
//        public String ingest(@RequestParam String file) {
//            reader.readAndProcessFile(file, processor);
//            return "Ingest triggered for: " + file;
//        }
//    }

package com.example.orderprocessing.api;

import java.util.Collection;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.orderprocessing.domain.Order;
import com.example.orderprocessing.repository.InMemoryOrderRepository;
import com.example.orderprocessing.service.EventProcessor;
import com.example.orderprocessing.ingestion.EventReader;

@RestController
@RequestMapping("/api")
public class OrderController {

    private final InMemoryOrderRepository repo;
    private final EventReader reader;
    private final EventProcessor processor;

    public OrderController(InMemoryOrderRepository repo, EventReader reader, EventProcessor processor) {
        this.repo = repo;
        this.reader = reader;
        this.processor = processor;
    }

    @GetMapping(value = "/orders", produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<Order> allOrders() {
        return repo.findAll();
    }

    @GetMapping(value = "/orders/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Order getOrder(@PathVariable("id") String id) {
        return repo.findById(id);
    }

    @PostMapping("/ingest")
    public String ingest(@RequestParam("file") String file) {
        reader.readAndProcessFile(file, processor);
        return "Ingest triggered for: " + file;
    }
}

