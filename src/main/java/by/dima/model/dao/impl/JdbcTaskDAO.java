package by.dima.model.dao.impl;

import by.dima.model.dao.TaskDAO;
import by.dima.model.entity.Action;
import by.dima.model.entity.Pipeline;
import by.dima.model.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcTaskDAO extends NamedParameterJdbcDaoSupport implements TaskDAO {

    private static final String ID_KEY = "idTask";
    private static final String NAME_KEY = "nameKey";
    private static final String DESCRIPTION_KEY = "descriptionKey";
    private static final String PIPELINE_ID_KEY = "pipelineKey";
    private static final String ACTION_KEY = "actionKey";

    private static final String INSERT_TASK_SQL = "insert into task (name, description, id_pipeline, action) VALUES (:"
            + NAME_KEY + ",:" + DESCRIPTION_KEY + ",:" + PIPELINE_ID_KEY + ",:" + ACTION_KEY +  ");";
    private static final String UPDATE_TASK_SQL = "update task set name=:" +
            NAME_KEY  + ",description=:" + DESCRIPTION_KEY + ",action=:" + ACTION_KEY + " where id_task=:" + ID_KEY + ";";
    private static final String DELETE_TASK_SQL = "delete from task where id_task=:" + ID_KEY + ";";
    private static final String GET_TASK_BY_PIPELINE_AND_NAME_SQL = "select * from task where id_pipeline=:"
            + PIPELINE_ID_KEY + " and name=:" + NAME_KEY + ";";
    private static final String GET_ALL_TASKS_BY_PIPELINE_SQL = "select * from task where id_pipeline=:"
            + PIPELINE_ID_KEY + ";";

    @Autowired
    private RowMapper<Task> taskRowMapper;

    public void setTaskRowMapper(RowMapper<Task> taskRowMapper) {
        this.taskRowMapper = taskRowMapper;
    }

    @Override
    public void createTask(Pipeline pipeline, Task task){
        Map<String, Object> params = new HashMap<>();

        params.put(NAME_KEY, task.getName());
        params.put(DESCRIPTION_KEY, task.getDescription());
        params.put(PIPELINE_ID_KEY, pipeline.getId());
        params.put(ACTION_KEY, task.getAction().getType());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        getNamedParameterJdbcTemplate().update(INSERT_TASK_SQL, new MapSqlParameterSource(params), keyHolder);
        task.setId(keyHolder.getKey().intValue());
    }

    @Override
    public void deleteTask(Task task) {
        Map<String, Object> params = new HashMap<>();

        params.put(ID_KEY, task.getId());

        getNamedParameterJdbcTemplate().update(DELETE_TASK_SQL, params);
    }

    @Override
    public void updateTask(Task task) {
        Map<String, Object> params = new HashMap<>();

        params.put(ID_KEY, task.getId());
        params.put(NAME_KEY, task.getName());
        params.put(DESCRIPTION_KEY, task.getDescription());
        params.put(ACTION_KEY, task.getAction().getType());

        getNamedParameterJdbcTemplate().update(UPDATE_TASK_SQL, params);
    }

    @Override
    public Task getTaskByPipelineAndName(Pipeline pipeline, String name) {
        Map<String, Object> params = new HashMap<>();

        params.put(PIPELINE_ID_KEY, pipeline.getId());
        params.put(NAME_KEY, name);

        return getNamedParameterJdbcTemplate().queryForObject(GET_TASK_BY_PIPELINE_AND_NAME_SQL, params, taskRowMapper);
    }

    @Override
    public List<Task> getAllTasks(Pipeline pipeline) {
        Map<String, Object> params = new HashMap<>();

        params.put(PIPELINE_ID_KEY, pipeline.getId());

        return getNamedParameterJdbcTemplate().query(GET_ALL_TASKS_BY_PIPELINE_SQL, params, taskRowMapper);
    }
}