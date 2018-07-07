package by.dima.model.dao.impl;

import by.dima.model.entity.Action;
import by.dima.model.entity.Task;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class TaskMapper implements RowMapper<Task> {
    @Override
    public Task mapRow(ResultSet resultSet, int i) throws SQLException {
        Task result = new Task();

        result.setId(resultSet.getInt("id_task"));
        result.setName(resultSet.getString("name"));
        result.setDescription(resultSet.getString("description"));
        result.setAction(new Action(resultSet.getString("action")));

        return result;
    }
}
