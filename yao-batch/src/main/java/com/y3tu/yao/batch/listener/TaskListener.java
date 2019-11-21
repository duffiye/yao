package com.y3tu.yao.batch.listener;

import org.springframework.cloud.task.listener.TaskExecutionListener;
import org.springframework.cloud.task.repository.TaskExecution;

import java.util.logging.Logger;

/**
 * ClassName: JobListener
 * Description:
 * date: 2019/10/8 10:11
 *
 * @author zht
 */
public class TaskListener implements TaskExecutionListener {

    private final static Logger LOGGER = Logger.getLogger(TaskListener.class.getName());

    @Override
    public void onTaskEnd(TaskExecution arg0) {
        LOGGER.info("End of Task");
    }

    @Override
    public void onTaskFailed(TaskExecution arg0, Throwable arg1) {

    }

    @Override
    public void onTaskStartup(TaskExecution arg0) {
        LOGGER.info("Task Startup");
    }
}
