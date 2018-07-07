package by.dima.controller;

import by.dima.model.dao.PipelineDAO;
import by.dima.model.dao.TaskDAO;
import by.dima.model.entity.Pipeline;
import by.dima.model.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TaskController {
    @Autowired
    private PipelineDAO pipelineDAO;

    @Autowired
    private TaskDAO taskDAO;

    @PutMapping(value = "/pipeline/{pipelineName}/tasks/")
    public ResponseEntity addTask(@RequestBody Task task, @PathVariable String pipelineName){
        Pipeline pipeline = pipelineDAO.getPipelineByName(pipelineName);

        if (task != null) {
            taskDAO.createTask(pipeline, task);
            return new ResponseEntity(HttpStatus.OK);
        }

        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/pipeline/{pipelineName}/tasks/{taskName}")
    public ResponseEntity updateTask(@RequestBody Task task, @PathVariable String taskName, @PathVariable String pipelineName){
        if (task != null) {
            Pipeline pipeline = pipelineDAO.getPipelineByName(pipelineName);
            Task oldTask = taskDAO.getTaskByPipelineAndName(pipeline, taskName);

            task.setId(oldTask.getId());
            taskDAO.updateTask(task);

            return new ResponseEntity(HttpStatus.OK);
        }

        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(value = "/pipeline/{pipelineName}/tasks/{taskName}")
    public ResponseEntity deleteTask(@PathVariable String taskName, @PathVariable String pipelineName){
        Pipeline pipeline = pipelineDAO.getPipelineByName(pipelineName);

        Task task = taskDAO.getTaskByPipelineAndName(pipeline, taskName);
        taskDAO.deleteTask(task);

        return new ResponseEntity(HttpStatus.OK);
    }

}
