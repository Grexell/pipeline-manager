# pipeline-manager
## Deployment
### Database
1. Create tables which are specified in the file [tables.sql](../master/src/main/webapp/tables.sql)
2. Set params to connection in file [dao-config.xml](../master/src/main/webapp/WEB-INF/dao-config.xml)
### REST API
#### Pipeline
* **/api/pipeline** - create pipeline(PUT method)
* **/api/pipeline/{pipelineName}** - update/delete pipeline with *pipelineName* name (POST/DELETE method)
#### Task
* **/api/pipeline/{pipelineName}/tasks/** - add task to *pipelineName* pipeline(PUT method)
* **/api/pipeline/{pipelineName}/tasks/{taskName}** - update/delete task with *task* name in pipeline with *pipelineName* name(POST/DELETE method)
#### Transitions
* **/api/pipeline/{pipelineName}/transition/{prevName}/{nextName}** - add transition from *prev* to *next* to *pipelineName* pipeline(PUT method)
* **/api/pipeline/{pipelineName}/transition/{prevName}/{nextName}** - delete transition from *prev* to *next* to *pipelineName* pipeline(DELETE method)
#### Execution
* **/execution/pipeline/{pipelineName}** - start execute *pipelineName* pipeline(PUT method)
* **/api/execution/{executionId}** - show execution info with *executionId*(GET method)
* **/api/execution/{executionId}** - delete execution with *executionId*(DELETE method)
