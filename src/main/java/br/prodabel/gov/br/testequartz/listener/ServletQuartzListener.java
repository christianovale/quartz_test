package br.prodabel.gov.br.testequartz.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import br.prodabel.gov.br.testequartz.job.HelloJob;
import br.prodabel.gov.br.testequartz.job.MySecondJob;

public class ServletQuartzListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {

	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
        // JobDetail é onde informamos qual a classe que implementa a interface Job que deve ser usada para o
        // agendamento
        JobDetail job1 = JobBuilder.newJob(HelloJob.class).withIdentity("dummyJobName", "group1").build();

        // O objeto Trigger é usado para dizer o momento que o job deve ser executado
        // SimpleTrigger – Allows to set start time, end time, repeat interval. (Run every 10 seconds)
        Trigger trigger1 = TriggerBuilder.newTrigger()
                                         .withIdentity("dummyTriggerName", "group1")
                                         .withSchedule(
                                                 SimpleScheduleBuilder.simpleSchedule()
                                                 .withIntervalInSeconds(10).repeatForever())
                                         .build();

        // ---

        JobDetail job2 = JobBuilder.newJob(MySecondJob.class).withIdentity("MySecondJob", "group1").build();

        // CronTrigger – Allows Unix cron expression to specify the dates and times to run your job. (Run every 15
        // seconds)
        //
        // O CronTrigger é um tipo de trigger que é útil para agendamentos baseados em calendários, como, por exemplo,
        // “uma execução deve ocorrer às quartas e quintas às 12:00h” ou “uma execução deve ocorrer todos os dias às
        // 08:00h”,
        // e assim por diante.
        //
        // Ordem dos parâmetros: Segundos; Minutos; Horas; Dia do mês; Mês; Dia da semana; Ano (opcional).
        Trigger trigger2 = TriggerBuilder.newTrigger()
                                         .withIdentity("dummyTriggerName2", "group1")
                                         .withSchedule(CronScheduleBuilder.cronSchedule("0/15 * * * * ?"))
                                         .build();

		try {
            // A instância do Scheduler que será o responsável por agendar as tarefas para a execução
			Scheduler scheduler = new StdSchedulerFactory().getScheduler();
            scheduler.scheduleJob(job1, trigger1);
            scheduler.scheduleJob(job2, trigger2);
            scheduler.start();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}

	}

}
