package by.dima.model.dao.impl;

import by.dima.model.dao.PipelineDAO;
import by.dima.model.entity.Pipeline;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.stereotype.Repository;
import java.util.HashMap;
import java.util.Map;

@Repository
public class JdbcPipelineDAO extends NamedParameterJdbcDaoSupport implements PipelineDAO {

    private static final String INSERT_PIPELINE_SQL = "INSERT INTO `pipeline_manager`.`pipeline` (`name`, `description`) VALUES (:pipelineName, :pipelineDescription);";
    private static final String INSERT_TASK_SQL = "insert into task (name, description, id_pipeline, action) VALUES (),;";
    private static final String GET_PIPELINE_ID_BY_NAME = "select id_pipeline from pipeline where name=':pipelineName';";

    public int identifyPipeline(String name){
        Map<String, Object> params = new HashMap<>();

        params.put("pipelineName", name);

        return getJdbcTemplate().queryForObject(GET_PIPELINE_ID_BY_NAME, Integer.TYPE, params);
    }

    public void createTasks(Pipeline pipeline) {
        int id = identifyPipeline(pipeline.getName());
        Map<String, Object> params = new HashMap<>();

        String sql = INSERT_TASK_SQL;

        if (pipeline.getTasks() != null && pipeline.getTasks().size() > 0) {
            for (int i = 0; i < pipeline.getTasks().size() - 1; i++) {

            }

        }

        getNamedParameterJdbcTemplate().update(sql, params);
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