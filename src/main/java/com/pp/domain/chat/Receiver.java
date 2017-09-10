package com.pp.domain.chat;

import com.pp.TaskManagerApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class Receiver {

    private static Logger LOGGER = LoggerFactory.getLogger(Receiver.class);

    @JmsListener(destination = "mailbox", containerFactory = "myFactory")
    public void receiveMessage(Email email) {
        LOGGER.debug("Received <" + email + ">");
    }

}
