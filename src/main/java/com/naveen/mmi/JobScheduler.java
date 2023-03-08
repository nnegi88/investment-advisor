package com.naveen.mmi;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author Naveen Singh Negi
 * @created 08/03/23
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class JobScheduler {
    private final TwilioConnector twilioConnector;

    @Scheduled(cron = "0 0 8 ? * MON,TUE,WED,THU,FRI", zone = "Asia/Kolkata")
    public void scheduleJob() {
        try {
            Document doc = Jsoup.connect("https://www.tickertape.in/market-mood-index").get();
            Elements mmiDiv = doc.getElementsByClass("mmi-value");
            if (mmiDiv.size() == 1) {
                Elements mmiSpan = mmiDiv.get(0).getElementsByTag("span");
                if (mmiSpan.size() == 1) {
                    String mmiValue = mmiSpan.get(0).text();
                    float mmi = Float.parseFloat(mmiValue);
                    MmiZone zone = MmiZoneUtil.getMmiZone(mmi);
                    RecommendedAction recommendedAction = MmiZoneUtil.getRecommendedAction(zone);
                    log.info("MMI Value: {}, MMI Zone: {}, Recommended Action: {}", mmiValue, zone, recommendedAction);
                    String messageToSend = String.format("MMI Value: %s\n MMI Zone: %s\n Recommended Action: %s", mmiValue, zone, recommendedAction);
                    twilioConnector.sendWhatsAppMessage(messageToSend);
                    return;
                }
            }
            log.error("MMI Value not found");
            twilioConnector.sendWhatsAppMessage("MMI Value not found");
        } catch (IOException e) {
            twilioConnector.sendWhatsAppMessage("Error while fetching MMI Value");
            log.error("Error while fetching MMI Value", e);
        }
    }
}
