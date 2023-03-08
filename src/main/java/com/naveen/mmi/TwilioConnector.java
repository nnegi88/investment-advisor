package com.naveen.mmi;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author Naveen Singh Negi
 * @created 08/03/23
 */
@Service
@Slf4j
public class TwilioConnector {

    @Value("${twilio.accountSid}")
    private String twilioAccountSid;

    @Value("${twilio.authToken}")
    private String twilioAuthToken;

    @Value("${twilio.fromNumber}")
    private String twilioFromNumber;

    @Value("${twilio.toNumber}")
    private String twilioToNumber;

    public void sendWhatsAppMessage(String messageToSend) {
        Twilio.init(twilioAccountSid, twilioAuthToken);
        Message message = Message.creator(
                        new PhoneNumber(twilioFromNumber),
                        new PhoneNumber(twilioToNumber),
                        messageToSend)
                .create();
        log.info("Message sent: {}", message.getSid());
    }
}
