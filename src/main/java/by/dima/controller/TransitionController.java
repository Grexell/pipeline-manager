package by.dima.controller;

import by.dima.model.dao.PipelineDAO;
import by.dima.model.dao.TaskDAO;
import by.dima.model.dao.TransitionDAO;
import by.dima.model.entity.Pipeline;
import by.dima.model.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransitionController {

    @Autowired
    private TransitionDAO transitionDAO;

    @Autowired
    private PipelineDAO pipelineDAO;

    @Autowired
    private TaskDAO taskDAO;


    @PutMapping(value = "/pipeline/{pipelineName}/transition/{prevName}/{nextName}")
    public ResponseEntity addTransition(@PathVariable String pipelineName, @PathVariable String prevName, @PathVariable String nextName) {
        Pipeline pipeline = pipelineDAO.getPipelineByName(pipelineName);
        Task prevTask = taskDAO.getTaskByPipelineAndName(pipeline, prevName);
        Task nextTask = taskDAO.getTaskByPipelineAndName(pipeline, nextName);

        transitionDAO.createTransition(prevTask.getId(), nextTask.getId());

        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping(value = "/pipeline/{pipelineName}/transition/{prevName}/{nextName}")
    public ResponseEntity deleteTransition(@PathVariable String pipelineName, @PathVariable String prevName, @PathVariable String nextName) {
        Pipeline pipeline = pipelineDAO.getPipelineByName(pipelineName);
        Task prevTask = taskDAO.getTaskByPipelineAndName(pipeline, prevName);
        Task nextTask = taskDAO.getTaskByPipelineAndName(pipeline, nextName);

        transitionDAO.deleteTransition(prevTask.getId(), nextTask.getId());

        return new ResponseEntity(HttpStatus.OK);
    }
}
