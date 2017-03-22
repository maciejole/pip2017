package pl.hycom.pip.messanger.handler.processor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.messenger4j.exceptions.MessengerApiException;
import com.github.messenger4j.exceptions.MessengerIOException;
import com.github.messenger4j.user.UserProfile;
import com.github.messenger4j.user.UserProfileClient;

import lombok.extern.log4j.Log4j2;
import pl.hycom.pip.messanger.handler.PipelineMessageHandler;
import pl.hycom.pip.messanger.pipeline.PipelineContext;
import pl.hycom.pip.messanger.pipeline.PipelineException;
import pl.hycom.pip.messanger.pipeline.PipelineProcessor;

@Component
@Log4j2
public class UserProfileProcessor implements PipelineProcessor {

    public static final String LOCALE = "message";
    public static final String GENDER = "message";
    public static final String FIRST_NAME = "message";
    public static final String LAST_NAME = "message";

    @Autowired
    private UserProfileClient userProfileClient;

    @Override
    public int runProcess(PipelineContext ctx) throws PipelineException {

        try {
            UserProfile userProfile = userProfileClient.queryUserProfile(ctx.get(PipelineMessageHandler.SENDER_ID, String.class));

            ctx.put(LOCALE, userProfile.getLocale());
            ctx.put(GENDER, userProfile.getGender());
            ctx.put(FIRST_NAME, userProfile.getFirstName());
            ctx.put(LAST_NAME, userProfile.getLastName());

        } catch (MessengerApiException | MessengerIOException e) {
            log.error(e);
        }

        return 1;
    }

}
