package ooo.gyoo.speedrunwrs.service.impl;

import ooo.gyoo.speedrunwrs.model.MessageQueue;
import ooo.gyoo.speedrunwrs.service.SubmitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MessageService {

    static Logger LOGGER = LoggerFactory.getLogger(MessageService.class);
    private final MessageQueue messageQueue;

    private final Map<String, SubmitService> services;

    @Autowired
    public MessageService(final MessageQueue messageQueue, final ApplicationContext context) {
        this.messageQueue = messageQueue;
        this.services = context.getBeansOfType(SubmitService.class);
    }

    @Scheduled(fixedDelay = 300000)
    @Async(value = "messageThreadPool")
    public void run() {
        String message = null;
        try {
            message = this.messageQueue.getQueue().take();
        } catch (final InterruptedException e) {
            e.printStackTrace();
        }
        if (message != null) {
            LOGGER.info(message);
            final String finalMessage = message;
            this.services.values().forEach(s -> s.submit(finalMessage));
        }
    }
}
