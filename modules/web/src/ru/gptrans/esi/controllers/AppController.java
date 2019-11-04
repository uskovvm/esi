package ru.gptrans.esi.controllers;

import com.google.common.base.Preconditions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.gptrans.esi.service.ClientSystemMqService;
import ru.gptrans.esi.service.ExtSystemMqService;

import javax.inject.Inject;

@RestController
@RequestMapping("/api")
public class AppController {

    @Inject
    private ExtSystemMqService extSystemMqService;

    @Inject
    private ClientSystemMqService clientSystemMqService;

    @PostMapping(value="/get-data")
    public String getData(@RequestBody String request) {
        extSystemMqService.produce(request);
        return "Ok";
    }

    @PostMapping(value="/clientmq")
    @ResponseStatus(HttpStatus.CREATED)
    public boolean create(@RequestBody String message) {
        Preconditions.checkNotNull(message);
        return clientSystemMqService.produce(message);
    }
}