package by.dima.controller;

import by.dima.model.dao.PipelineDAO;
import by.dima.model.entity.Pipeline;
import by.dima.model.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class PipelineController {

    @Autowired
    private PipelineDAO pipelineDAO;

    @GetMapping(value = "/")
    public Object getExecution(){

        Pipeline pipeline = new Pipeline();

        pipeline.setName("test name");
        pipeline.setDescription("test description");

        List<Task> tasks = new ArrayList<>();

        Task task = new Task();
        task.setName("task 1");
        task.setDescription("test task 1");
        tasks.add(task);
        task = new Task();
        task.setName("task 2");
        task.setDescription("test task 2");
        tasks.add(task);

        pipeline.setTasks(tasks);

        IdentityHashMap<String, String> string = new IdentityHashMap<>();
        for (int i = 0; i < 4; i++) {
            string.put("test" + i, "test" + i + "value");
        }

        string.put("test2", "test2value2");

        pipeline.setTransitions(string);

        return pipeline;
    }

    @PutMapping(value = "/pipeline")
    public ResponseEntity addPipeline(@RequestBody Pipeline pipeline){
        Object o = pipelineDAO;

        return new ResponseEntity(pipeline, HttpStatus.ACCEPTED);
    }

    @PostMapping(value = "/pipeline/{pipelineName}")
    public ResponseEntity updatePipeline(@PathVariable String pipelineName, @RequestBody Pipeline pipeline){
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping(value = "/pipeline/{pipelineName}")
    public ResponseEntity deletePipeline(@PathVariable String pipelineName){
        return new ResponseEntity(HttpStatus.OK);
    }
}
