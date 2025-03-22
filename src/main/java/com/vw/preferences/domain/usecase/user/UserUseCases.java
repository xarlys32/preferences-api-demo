package com.vw.preferences.domain.usecase.user;

import com.vw.preferences.domain.exception.DuplicateMailException;
import com.vw.preferences.domain.exception.PreferencesNotFoundException;
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
        validateEmail(query.email());
        User user = getUserOrThrowException(query.email());

        return userRepository.getPreferencesByUser(query.email());
    }

    @CommandHandler
    public User savePreferences(PostConsentUpdate command) {
        validateEmail(command.email());
        validateConsent(command.consent());
        User userStored = getUserOrThrowException(command.email());
        updateConsent(userStored.getConsents(), command.consent());
        commandGateway.send(new PostConsentEvent(userStored.getUserId(), command.consent()));

        return userRepository.save(userStored);
    }

    @CommandHandler
    public User createAccount(PostAccountCreate command) throws IllegalArgumentException {
        validateEmail(command.mail());
        User userStored = userRepository.getPreferencesByUser(command.mail());
        if(userStored != null) {
            throw new DuplicateMailException("Email already registered");
        }
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

    private void updateConsent(List<Consent> consentList, Consent consentForSave) {
        boolean consentFoundAndUpdated = consentList.stream()
                .filter(consent -> consent.getConsentId().equals(consentForSave.getConsentId()))
                .findFirst()
                .map(consent -> {
                    consent.setEnabled(consentForSave.getEnabled());
                    return true;
                })
                .orElse(false);

        if (!consentFoundAndUpdated) {
            consentList.add(consentForSave);
        }
    }

    private User getUserOrThrowException(String mail) {
        User userStored = userRepository.getPreferencesByUser(mail);
        if (userStored == null) {
            throw new PreferencesNotFoundException("Email not found");
        }

        return userStored;
    }
}
