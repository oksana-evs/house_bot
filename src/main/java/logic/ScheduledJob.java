package logic;

import org.quartz.Job;
import org.quartz.JobExecutionContext;


public class ScheduledJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext)  {
        HouseCleaning.reset();
    }
}
