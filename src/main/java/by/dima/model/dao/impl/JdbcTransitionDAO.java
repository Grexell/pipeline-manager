package by.dima.model.dao.impl;

import by.dima.model.dao.TransitionDAO;
import by.dima.model.entity.Pipeline;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcTransitionDAO extends NamedParameterJdbcDaoSupport implements TransitionDAO {

    private static final String INSERT_TRANSITION_SQL = "insert into transition (prev_task, next_task) values (:prev_task, :next_task);";
    private static final String DELETE_TRANSITION_SQL = "delete from transition where prev_task=:prev_task and next_task =:next_task;";
    private static final String GET_TRANSITIONS_SQL = "select prevTask.name as prevName, nextTask.name as nextName from transition inner join task as prevTask on transition.prev_task = prevTask.id_task inner join task as nextTask on transition.next_task = nextTask.id_task where prevTask.id_pipeline=20;";

    private void updateCommandExecutor(String sql, int idPrev, int idNext){
        Map<String, Object> params = new HashMap<>();

        params.put("prev_task", idPrev);
        params.put("next_task", idNext);

        getNamedParameterJdbcTemplate().update(sql, params);
    }

    @Override
    public void createTransition(int idPrev, int idNext){
        updateCommandExecutor(INSERT_TRANSITION_SQL, idPrev, idNext);
    }

    @Override
    public void deleteTransition(int idPrev, int idNext) {
        updateCommandExecutor(DELETE_TRANSITION_SQL, idPrev, idNext);
    }

    @Override
    public IdentityHashMap<String, String> getTransitions(Pipeline pipeline) {
        Map<String, Object> params = new HashMap<>();

        params.put("idPipeline", pipeline.getId());

        List<Map<String, Object>> result = getNamedParameterJdbcTemplate().queryForList(GET_TRANSITIONS_SQL, params);
        IdentityHashMap<String, String> transitions = new IdentityHashMap<>();
        for (Map<String, Object> transition: result) {
            transitions.put(transition.get("prevName").toString(), transition.get("nextName").toString());
        }

        return transitions;
    }
}
