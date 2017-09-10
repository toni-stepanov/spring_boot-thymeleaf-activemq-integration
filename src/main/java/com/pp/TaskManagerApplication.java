package com.pp;

import com.pp.controller.advice.ExceptionHandlerControllerAdvice;
import com.pp.domain.chat.Email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.core.JmsTemplate;

/**
 * Created by astepanov
 */
@SpringBootApplication
public class TaskManagerApplication extends SpringBootServletInitializer {

    private static Logger LOGGER = LoggerFactory.getLogger(TaskManagerApplication.class);

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(TaskManagerApplication.class, args);
        //TODO move it
        JmsTemplate jmsTemplate = context.getBean(JmsTemplate.class);
        // Send a message with a POJO - the template reuse the message converter
        LOGGER.debug("Sending an email message.");
        jmsTemplate.convertAndSend("mailbox", new Email("info@example.com", "Hey"));
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(TaskManagerApplication.class);
	}

}