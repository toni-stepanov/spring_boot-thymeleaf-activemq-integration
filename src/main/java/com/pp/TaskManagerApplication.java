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
		SpringApplication.run(TaskManagerApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(TaskManagerApplication.class);
	}

}