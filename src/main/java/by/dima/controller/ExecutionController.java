package by.dima.controller;

import by.dima.model.dao.ExecutionDAO;
import by.dima.model.dao.PipelineDAO;
import by.dima.model.entity.Execution;
import by.dima.model.entity.Pipeline;
import by.dima.model.logic.Executor;
import by.dima.util.ExecutionCreator;
import by.dima.util.ExecutionPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ExecutionController {

    @Autowired
    private PipelineDAO pipelineDAO;

    @Autowired
    private ExecutionCreator executionCreator;

    @Autowired
    private ExecutionDAO executionDAO;

    @Autowired
    private ExecutionPool executionPool;

    @PutMapping("/execution/pipeline/{pipelineName}")
    public ResponseEntity startExecution(@PathVariable String pipelineName){
        Pipeline pipeline = pipelineDAO.getPipelineByName(pipelineName);

        Execution execution = executionCreator.create(pipeline);

        Executor executor = new Executor(execution);
        executor.setExecutionDAO(executionDAO);
        executor.setExecutionPool(executionPool);
        executionPool.put(executor);
        new Thread(executionPool.get(execution.getId())).start();

        return new ResponseEntity(execution, HttpStatus.OK);
    }

    @GetMapping("/execution/{executionId}")
    public ResponseEntity showExecution(@PathVariable int executionId){
        if (executionPool.containsKey(executionId)){
            return new ResponseEntity(executionPool.get(executionId), HttpStatus.OK);
        } else {
            return new ResponseEntity(executionDAO.getExecution(executionId), HttpStatus.OK);
        }
    }

    @DeleteMapping("/execution/{executionId}")
    public ResponseEntity stopExecution(@PathVariable int executionId){
        if (executionPool.containsKey(executionId)){
            Executor executor = executionPool.get(executionId);
            executor.getWorkingState().setWorking(false);
            return new ResponseEntity(executor, HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
}
