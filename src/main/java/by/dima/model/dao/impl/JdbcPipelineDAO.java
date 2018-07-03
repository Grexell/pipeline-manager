package by.dima.model.dao.impl;

import by.dima.model.dao.PipelineDAO;
import by.dima.model.entity.Pipeline;
import by.dima.model.entity.Task;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.stereotype.Repository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcPipelineDAO extends NamedParameterJdbcDaoSupport implements PipelineDAO {

    private static final String INSERT_PIPELINE_SQL = "INSERT INTO `pipeline_manager`.`pipeline` (`name`, `description`) VALUES (:pipelineName, :pipelineDescription);";
    private static final String INSERT_TASK_SQL = "insert into task (name, description, id_pipeline, action) VALUES %s;";
    private static final String GET_PIPELINE_ID_BY_NAME = "select id_pipeline from pipeline where name=:pipelineName;";

    public int identifyPipeline(String name){
        Map<String, Object> params = new HashMap<>();

        params.put("pipelineName", name);

        return getJdbcTemplate().queryForObject(GET_PIPELINE_ID_BY_NAME, Integer.TYPE, params);
    }

    private String getValuesForTask(String paramNameId, String nameKey, String descriptionKey, String pipelineIdKey, String actionKey){
        final char delimeter = ',';
        final char leftValuesLimiter = '(';
        final char rightValuesLimiter = ')';
        final char paramNameDescriptor = ':';

        return leftValuesLimiter + paramNameDescriptor + paramNameId + nameKey + delimeter + paramNameDescriptor + paramNameId + descriptionKey + delimeter + + paramNameDescriptor + paramNameId + pipelineIdKey + delimeter + paramNameDescriptor + paramNameId + actionKey + rightValuesLimiter;
    }

    public void createTasks(Pipeline pipeline) {
        final char delimeter = ',';
        final String nameKey = "nameKey";
        final String descriptionKey = "descriptionKey";
        final String pipelineIdKey = "pipelineKey";
        final String actionKey = "actionKey";

        int id = identifyPipeline(pipeline.getName());
        Map<String, Object> params = new HashMap<>();

        String sql = INSERT_TASK_SQL;

        if (pipeline.getTasks() != null && pipeline.getTasks().size() > 0) {
            String values = "";
            List<Task> taskList = pipeline.getTasks();
            for (int i = 0; i < taskList.size() - 1; i++) {
                values += getValuesForTask(i + "", nameKey, descriptionKey, pipelineIdKey, actionKey) + delimeter;

                params.put(i + nameKey, taskList.get(i).getName());
                params.put(i + descriptionKey, taskList.get(i).getDescription());
                params.put(i + pipelineIdKey, id);
                params.put(i + actionKey, taskList.get(i).getAction().getType());
            }

            values += getValuesForTask(taskList.size() - 1 + "", nameKey, descriptionKey, pipelineIdKey, actionKey) + delimeter;

            params.put(taskList.size() - 1 + nameKey, taskList.get(taskList.size() - 1).getName());
            params.put(taskList.size() - 1 + descriptionKey, taskList.get(taskList.size() - 1).getDescription());
            params.put(taskList.size() - 1 + pipelineIdKey, id);
            params.put(taskList.size() - 1 + actionKey, taskList.get(taskList.size() - 1).getAction().getType());

            sql = String.format(sql, values);

            getNamedParameterJdbcTemplate().update(sql, params);
        }
    }

    @Override
    public void createPipeline(Pipeline pipeline) {
        Map<String, Object> params = new HashMap<>();

        params.put("pipelineName", pipeline.getName());
        params.put("pipelineDescription", pipeline.getDescription());

        getNamedParameterJdbcTemplate().update(
                INSERT_PIPELINE_SQL,
                params);

        createTasks(pipeline);
    }

    @Override
    public void updatePipeline(Pipeline pipeline) {

    }

    @Override
    public void deletePipeline(Pipeline pipeline) {
        getJdbcTemplate().update("", (Class<Object>) null);
    }

    @Override
    public Pipeline getPipelineByName(String name) {
        return null;
    }
}