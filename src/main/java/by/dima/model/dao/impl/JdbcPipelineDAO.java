package by.dima.model.dao.impl;

import by.dima.model.dao.PipelineDAO;
import by.dima.model.dao.TaskDAO;
import by.dima.model.dao.TransitionDAO;
import by.dima.model.entity.Pipeline;
import by.dima.model.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class JdbcPipelineDAO extends NamedParameterJdbcDaoSupport implements PipelineDAO {

    private static final String INSERT_PIPELINE_SQL = "INSERT INTO `pipeline_manager`.`pipeline` (`name`, `description`) VALUES (:pipelineName, :pipelineDescription);";
    private static final String UPDATE_PIPELINE_SQL = "update pipeline set name=:pipelineName, description=:pipelineDescription where name=:oldName;";
    private static final String GET_PIPELINE_BY_NAME = "select * from pipeline where name=:pipelineName;";
    private static final String DELETE_PIPELINE_BY_NAME = "delete from pipeline where name=:pipelineName;";

    @Autowired
    private TaskDAO taskDAO;

    @Autowired
    private TransitionDAO transitionDAO;

    public void setTaskDAO(TaskDAO taskDAO) {
        this.taskDAO = taskDAO;
    }

    public void setTransitionDAO(TransitionDAO transitionDAO) {
        this.transitionDAO = transitionDAO;
    }

    @Override
    public void createPipeline(Pipeline pipeline) {
        Map<String, Object> params = new HashMap<>();

        params.put("pipelineName", pipeline.getName());
        params.put("pipelineDescription", pipeline.getDescription());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        getNamedParameterJdbcTemplate().update(
                INSERT_PIPELINE_SQL,
                new MapSqlParameterSource(params)
                ,keyHolder);

        pipeline.setId(keyHolder.getKey().intValue());

        if(pipeline.getTasks() != null) {
            Map<String, Task> nameMap = new HashMap<>();

            List<Task> tasks = pipeline.getTasks();
            for (int i = 0; i < tasks.size(); i++) {
                Task task = tasks.get(i);
                taskDAO.createTask(pipeline, task);
                nameMap.put(task.getName(), task);
            }

            if (pipeline.getTransitions() != null) {
                for (Map.Entry<String, String> transition: pipeline.getTransitions().entrySet()) {
                    transitionDAO.createTransition(nameMap.get(transition.getKey()).getId(), nameMap.get(transition.getValue()).getId());
                }
            }
        }
    }

    @Override
    public void updatePipeline(String name, Pipeline pipeline) {
        Map<String, Object> params = new HashMap<>();

        params.put("oldName", name);
        params.put("pipelineName", pipeline.getName());
        params.put("pipelineDescription", pipeline.getDescription());

        getNamedParameterJdbcTemplate().update(
                UPDATE_PIPELINE_SQL,
                params);
    }

    @Override
    public void deletePipeline(String name) {
        Map<String, Object> params = new HashMap<>();

        params.put("pipelineName", name);

        getNamedParameterJdbcTemplate().update(DELETE_PIPELINE_BY_NAME, params);
    }

    @Override
    public Pipeline getPipelineByName(String name) {
        Map<String, Object> params = new HashMap<>();

        params.put("pipelineName", name);

        Pipeline result = getNamedParameterJdbcTemplate().queryForObject(GET_PIPELINE_BY_NAME, params, (resultSet, i) -> {
            Pipeline pipeline = new Pipeline();
            pipeline.setId(resultSet.getInt("id_pipeline"));
            pipeline.setName(resultSet.getString("name"));
            pipeline.setDescription(resultSet.getString("description"));

            return pipeline;
        });

        result.setTasks(taskDAO.getAllTasks(result));
        result.setTransitions(transitionDAO.getTransitions(result));

        return result;
    }
}