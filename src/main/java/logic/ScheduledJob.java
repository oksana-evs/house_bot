package logic;

import org.quartz.Job;
import org.quartz.JobExecutionContext;


public class ScheduledJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext)  {
        for (HouseCleaning cleaning : HouseCleaning.values()) {
            System.out.printf("Setting %s to done\n", cleaning.getName());
            cleaning.setDone(false);
        }
    }
}
