package br.prodabel.gov.br.testequartz.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Para agendarmos uma tarefa futura com o quartz precisamos implementar a interface Job,
 * onde devemos implementar o método execute, que nada mais é a implementação de
 * nossa tarefa a ser executada.
 * 
 * @author pb003415
 *
 */
public class HelloJob implements Job {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("****** Hello Quartz! *******");
	}

}
