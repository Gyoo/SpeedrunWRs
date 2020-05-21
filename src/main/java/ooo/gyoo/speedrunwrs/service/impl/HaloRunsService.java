package ooo.gyoo.speedrunwrs.service.impl;

import ooo.gyoo.speedrunwrs.model.MessageQueue;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Service
public class HaloRunsService {

    static Logger LOGGER = LoggerFactory.getLogger(HaloRunsService.class);
    private final MessageQueue messageQueue;

    private final File file;

    private String lastRunId;

    @Autowired
    public HaloRunsService(final MessageQueue messageQueue) {
        this.messageQueue = messageQueue;

        this.file = new File("last-haloruns.txt");
        try {
            if (this.file.exists()) {
                if (!Files.readAllLines(this.file.toPath()).isEmpty()) {
                    this.lastRunId = Files.readAllLines(this.file.toPath()).get(0);
                } else {
                    this.lastRunId = "";
                }
            } else {
                this.file.createNewFile();
            }
        } catch (final IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Scheduled(fixedDelay = 60000, initialDelay = 0)
    public void run() {
        try {
            final Document doc = Jsoup.connect("https://haloruns.com/records/recent").get();
            final Elements lines = doc.select("#recentWRTable").first().getElementsByTag("tbody").first().getElementsByTag("tr");
            for (int i = 1; i < lines.size(); i++) {
                final Element tr = lines.get(i);
                final Elements cells = tr.getElementsByTag("td");
                if (tr.text().equals(this.lastRunId)) {
                    break;
                }
                if ("Full Game".equals(cells.get(1).getAllElements().get(1).text())) {
                    final StringBuilder message = new StringBuilder(cells.get(1).getAllElements().get(2).text());
                    message.append(" in ");
                    message.append(cells.get(3).getAllElements().get(1).text());
                    message.append(" by ");
                    message.append(cells.get(3).getAllElements().get(2).getAllElements().get(1).text());
                    message.append(": ");
                    message.append(cells.get(3).getAllElements().get(1).getElementsByTag("a").get(0).attr("href"));
                    try {
                        this.messageQueue.getQueue().put(message.toString());
                    } catch (final InterruptedException e) {
                        LOGGER.error(e.getMessage());
                    }
                }
            }
            this.lastRunId = lines.get(1).text();
            Files.write(this.file.toPath(), this.lastRunId.getBytes());
        } catch (final IOException e) {
            LOGGER.error(e.getMessage());
        }
    }
}
