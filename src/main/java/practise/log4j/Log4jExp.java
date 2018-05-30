package practise.log4j;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;

public class Log4jExp {

	private static final Logger LOG = LogManager.getLogger(Log4jExp.class);

	public static void printLogs() {
		LOG.debug("This is debug log");
		LOG.info("This is info log");
		LOG.warn("This is warn log");
		LOG.error("This is error log");
		LOG.warn("----------------------------");

	}

	public static void main(String[] args) {
		for(int i=0; i<20; i++) {
			printLogs();
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				LOG.error("", e);
			}
			if(i==3 || i==10){
				LOG.warn("Resetting configuration");
//				BasicConfigurator.resetConfiguration();
			//	LOG.warn("Resetting done");
//				BasicConfigurator.configure();
		//		DOMConfigurator.configureAndWatch("src/main/resources/log4j.properties", 1000);
//				PropertyConfigurator.configureAndWatch("src/main/resources/log4j.properties", 1000);
				LoggerContext context= (LoggerContext) LogManager.getContext();
		        context.reconfigure();//getConfiguration();
		        LoggerContext context2= (LoggerContext) LogManager.getContext();
		        System.out.println(context2.getConfiguration().getConfigurationSource());

			}
		}
	}
}
