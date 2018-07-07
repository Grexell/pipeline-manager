package by.dima.model.dao;

import by.dima.model.entity.Pipeline;

public interface PipelineDAO {
    void createPipeline(Pipeline pipeline);
    void updatePipeline(String name, Pipeline pipeline);
    void deletePipeline(String name);

    Pipeline getPipelineByName(String name);
}
