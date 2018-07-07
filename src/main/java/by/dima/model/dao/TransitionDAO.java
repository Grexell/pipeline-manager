package by.dima.model.dao;

import by.dima.model.entity.Pipeline;

import java.util.IdentityHashMap;

public interface TransitionDAO {
    void createTransition(int idPrev, int idNext);
    void deleteTransition(int idPrev, int idNext);

   IdentityHashMap<String, String> getTransitions(Pipeline pipeline);
}