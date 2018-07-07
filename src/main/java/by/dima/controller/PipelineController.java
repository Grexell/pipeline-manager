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

    @PutMapping(value = "/pipeline")
    public ResponseEntity addPipeline(@RequestBody Pipeline pipeline){
        if (pipeline != null) {
            pipelineDAO.createPipeline(pipeline);

            return new ResponseEntity(pipeline, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/pipeline/{pipelineName}")
    public ResponseEntity updatePipeline(@PathVariable String pipelineName, @RequestBody Pipeline pipeline){
        if (pipeline != null) {
            pipelineDAO.updatePipeline(pipelineName, pipeline);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping(value = "/pipeline/{pipelineName}")
    public ResponseEntity deletePipeline(@PathVariable String pipelineName){
        pipelineDAO.deletePipeline(pipelineName);
        return new ResponseEntity(HttpStatus.OK);
    }
}