package com.vw.preferences.domain.usecase.user;

import com.vw.preferences.domain.model.user.User;
import com.vw.preferences.domain.port.user.UserRepository;
import com.vw.preferences.domain.usecase.event.PostConsentEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

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
    public User savePreferences(PostMailPreferences command) {
        User userStored = userRepository.createAccount(command.mail());
        commandGateway.send(new PostConsentEvent(userStored));

        return userRepository.createAccount(command.mail());
    }
}
