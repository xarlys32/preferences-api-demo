package com.vw.preferences.domain.usecase.user;

import com.vw.preferences.domain.model.Constants;
import com.vw.preferences.domain.model.user.Consent;
import com.vw.preferences.domain.model.user.User;
import com.vw.preferences.domain.port.user.UserRepository;
import com.vw.preferences.domain.usecase.event.PostConsentEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Pattern;

@Component
public class UserUseCases {

    private final UserRepository userRepository;
    private final CommandGateway commandGateway;

    public UserUseCases(UserRepository preferencesRepo, CommandGateway commandGateway) {
        this.userRepository = preferencesRepo;
        this.commandGateway = commandGateway;
    }

    @QueryHandler
    public User getPreferences(GetPreferences query) {

        return userRepository.getPreferencesByUser(query.userId());
    }

    @CommandHandler
    public User savePreferences(PostConsentUpdate command) {
        validateEmail(command.mail());
        validateConsent(command.consent());
        User userStored = userRepository.createAccount(command.mail());
        Consent lastConsent = getLastConsent(userStored.getConsents());
        if (lastConsent != null) {
            commandGateway.send(new PostConsentEvent(userStored.getUserId(), lastConsent));
        }

        return userRepository.createAccount(command.mail());
    }

    @CommandHandler
    public User createAccount(PostAccountCreate command) {
        validateEmail(command.mail());

        return userRepository.createAccount(command.mail());
    }


    private void validateConsent(Consent consent) {
        if (consent == null) {
            throw new IllegalArgumentException("Consent cannot be null");
        }
        if(consent.getEnabled() == null){
            throw new IllegalArgumentException("Consent status cannot be null");
        }
        if (!Constants.CONSENTS_IDS.contains(consent.getConsentId())){
            throw new IllegalArgumentException("Invalid consent id");
        }
    }

    private void validateEmail(String email) {
        Pattern pattern = Pattern.compile(Constants.EMAIL_REGEX);
        if (!pattern.matcher(email).matches()) {
            throw new IllegalArgumentException("Invalid email format");
        }
    }

    private Consent getLastConsent(List<Consent> consentList) {
        if (consentList.isEmpty()) {
            return null;
        }

        return consentList.get(consentList.size() - 1);
    }
}
