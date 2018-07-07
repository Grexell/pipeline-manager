package by.dima.model.logic;

import by.dima.model.entity.Status;
import by.dima.model.entity.operation.*;

public class OperationBuilder {
    public static final String PRINT_NAME = "print";
    public static final String COMPLETED_NAME = "random";
    public static final String RANDOM_NAME = "completed";
    public static final String DELAYED_NAME = "delayed";

    public static final int DELAYED_DEFAULT_TIME = 10000;

    public static Operation build(String name){
        Operation result = null;

        if (PRINT_NAME.equalsIgnoreCase(name)){
            result = new PrintNameOperation(new StatusOperation(Status.COMPLETED));
        } else if (COMPLETED_NAME.equalsIgnoreCase(name)){
            result = new PrintNameOperation(new DelayOperation(new StatusOperation(Status.COMPLETED)));
        } else if (RANDOM_NAME.equalsIgnoreCase(name)){
            result = new PrintNameOperation(new DelayOperation(new RandomStatusOperation()));
        }else if (DELAYED_NAME.equalsIgnoreCase(name)){
            result = new PrintNameOperation(new DelayOperation(new StatusOperation(Status.COMPLETED), DELAYED_DEFAULT_TIME));
        }

        return result;
    }
}
