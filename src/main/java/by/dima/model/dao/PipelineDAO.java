package by.dima.model.dao;

import by.dima.model.entity.Pipeline;

public interface PipelineDAO {
    void createPipeline(Pipeline pipeline);
    void updatePipeline(Pipeline pipeline);
    void deletePipeline(Pipeline pipeline);

    Pipeline getPipelineByName(String name);
}
