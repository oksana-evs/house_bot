package logic;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static org.quartz.JobBuilder.newJob;

public class Main {
    public static void main(String[] args) throws Exception {
        ApiContextInitializer.init();

        TelegramBotsApi botsApi = new TelegramBotsApi();

        try {
            botsApi.registerBot(new HouseBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        JobDetail job = newJob(ScheduledJob.class)
                .build();


        CronTrigger trigger = TriggerBuilder.newTrigger()
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0 7 ? * SAT *"))
                .build();

        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler scheduler = sf.getScheduler();
        scheduler.scheduleJob(job, trigger);
        scheduler.start();
    }
}
