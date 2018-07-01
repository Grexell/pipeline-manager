package by.dima.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ExecutionController {

    @PutMapping("/execution/pipeline/{pipelineName}")
    public ResponseEntity startExecution(){
        return null;
    }

    @GetMapping("/execution/{executionId}")
    public ResponseEntity showExecution(){
        return null;
    }

    @DeleteMapping("/execution/{executionId}")
    public ResponseEntity stopExecution(){
        return null;
    }
}
