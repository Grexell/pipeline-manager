package by.dima.model.dao.impl;

import by.dima.model.dao.ExecutionDAO;
import by.dima.model.dao.PipelineDAO;
import by.dima.model.entity.*;
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
public class JdbcExecutionDAO extends NamedParameterJdbcDaoSupport implements ExecutionDAO {

    private static final String INSERT_EXECUTION_SQL = "insert into execution(start_date) values (:startDate);";
    private static final String INSERT_TASK_EXECUTION_SQL = "insert into execution_task(id_execution, id_task, start_date, end_date, status) values (:idExecution, :idTask, :startDate, :endDate,:status);";
    private static final String UPDATE_EXECUTION_SQL = "update execution set start_date=:startDate where id_execution=:idExecution;";
    private static final String UPDATE_TASK_EXECUTION_SQL = "update execution_task set start_date=:startDate, end_date=:endDate, status=:status where id_execution=:idExecution and id_task=:idTask;";
    private static final String GET_EXECUTION_SQL = "select * from execution where id_execution=:idExecution;";
    private static final String GET_TASK_EXECUTION_SQL = "select execution_task.*, task.*, pipeline.name as pipelineName from execution_task inner join task on execution_task.id_task = task.id_task inner join pipeline on pipeline.id_pipeline = task.id_pipeline where id_execution=:idExecution;";

    @Autowired
    private RowMapper<Task> taskRowMapper;

    public void createTaskExecution(Execution execution, TaskExecution taskExecution) {
        Map<String, Object> params = new HashMap<>();

        params.put("idExecution", execution.getId());
        params.put("idTask", taskExecution.getTask().getId());
        params.put("startDate", taskExecution.getStartDate());
        params.put("endDate", taskExecution.getEndDate());
        params.put("status", taskExecution.getStatus().name());


        getNamedParameterJdbcTemplate().update(INSERT_TASK_EXECUTION_SQL, params);
    }

    @Override
    public void createExecution(Execution execution) {
        Map<String, Object> params = new HashMap<>();

        params.put("startDate", execution.getStartDate());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        getNamedParameterJdbcTemplate().update(
                INSERT_EXECUTION_SQL,
                new MapSqlParameterSource(params)
                ,keyHolder);

        execution.setId(keyHolder.getKey().intValue());

        if(execution.getTaskExecutions() != null) {
            List<TaskExecution> tasks = execution.getTaskExecutions();
            for (int i = 0; i < tasks.size(); i++) {
                TaskExecution task = tasks.get(i);
                createTaskExecution(execution, task);
            }
        }
    }

    @Override
    public void updateExecution(Execution execution) {
        Map<String, Object> params = new HashMap<>();

        params.put("startDate", execution.getStartDate());
        params.put("idExecution", execution.getId());

        getNamedParameterJdbcTemplate().update(
                UPDATE_EXECUTION_SQL, params);
    }

    @Override
    public void updateTaskExecution(Execution execution, TaskExecution taskExecution) {
        Map<String, Object> params = new HashMap<>();

        params.put("startDate", taskExecution.getStartDate());
        params.put("endDate", taskExecution.getEndDate());
        params.put("status", taskExecution.getStatus().name());
        params.put("idExecution", execution.getId());
        params.put("idTask", taskExecution.getTask().getId());

        getNamedParameterJdbcTemplate().update(
                UPDATE_TASK_EXECUTION_SQL, params);
    }

    @Override
    public Execution getExecution(int id) {
        Map<String, Object> params = new HashMap<>();

        params.put("idExecution", id);

        Execution result = getNamedParameterJdbcTemplate().queryForObject(GET_EXECUTION_SQL, params, (resultSet, i) -> {
            Execution execution = new Execution();
            execution.setId(resultSet.getInt("id_execution"));
            execution.setStartDate(resultSet.getDate("start_date"));

            return execution;
        });

        Pipeline pipeline = new Pipeline();

        result.setTaskExecutions(getNamedParameterJdbcTemplate().query(GET_TASK_EXECUTION_SQL, params, (resultSet, i) -> {
            TaskExecution taskExecution = new TaskExecution();

            taskExecution.setStatus(Status.valueOf(resultSet.getString("status")));
            taskExecution.setStartDate(resultSet.getDate("start_date"));
            taskExecution.setEndDate(resultSet.getDate("end_date"));
            taskExecution.setTask(taskRowMapper.mapRow(resultSet, i));

            pipeline.setName(resultSet.getString("pipelineName"));

            return taskExecution;
        }));
        result.setPipeline(pipeline);

        return result;
    }
}
