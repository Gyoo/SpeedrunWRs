package ooo.gyoo.speedrunwrs.model;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;

import java.util.concurrent.LinkedBlockingQueue;

@Component
public class MessageQueue {

    private LinkedBlockingQueue<Pair<String, String>> queue;

    public MessageQueue() {
        this.queue = new LinkedBlockingQueue<>();
    }

    public LinkedBlockingQueue<Pair<String, String>> getQueue() {
        return this.queue;
    }

    public void setQueue(final LinkedBlockingQueue<Pair<String, String>> queue) {
        this.queue = queue;
    }
}
