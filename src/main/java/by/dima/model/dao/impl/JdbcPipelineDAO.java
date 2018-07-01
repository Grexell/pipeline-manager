package by.dima.model.dao.impl;

import by.dima.model.dao.PipelineDAO;
import by.dima.model.entity.Pipeline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcPipelineDAO extends JdbcDaoSupport implements PipelineDAO {

    @Override
    public void createPipeline(Pipeline pipeline) {
//        getJdbcTemplate().
    }

    @Override
    public void updatePipeline(Pipeline pipeline) {

    }

    @Override
    public void deletePipeline(Pipeline pipeline) {
        getJdbcTemplate().queryForObject("", (Class<Object>) null);
    }

    @Override
    public Pipeline getPipelineByName(String name) {
        return null;
    }
}
