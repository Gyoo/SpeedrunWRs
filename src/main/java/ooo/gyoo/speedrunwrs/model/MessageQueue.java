package ooo.gyoo.speedrunwrs.model;

import org.springframework.stereotype.Component;

import java.util.concurrent.LinkedBlockingQueue;

@Component
public class MessageQueue {

    private LinkedBlockingQueue<String> queue;

    public MessageQueue() {
        this.queue = new LinkedBlockingQueue<>();
    }

    public LinkedBlockingQueue<String> getQueue() {
        return this.queue;
    }

    public void setQueue(final LinkedBlockingQueue<String> queue) {
        this.queue = queue;
    }
}
