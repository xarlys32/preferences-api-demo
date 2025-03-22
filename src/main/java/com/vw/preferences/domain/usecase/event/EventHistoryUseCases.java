package com.vw.preferences.domain.usecase.event;

import com.vw.preferences.domain.exception.UserHistoryNotFoundException;
import com.vw.preferences.domain.model.event.ConsentHistory;
import com.vw.preferences.domain.model.event.UserEventHistory;
import com.vw.preferences.domain.port.event.UserEventHistoryRepository;
import com.vw.preferences.infrastructure.repository.event.adapter.UserEventHistoryEntityMapper;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class EventHistoryUseCases {

    private final UserEventHistoryRepository eventHistoryRepository;

    private final UserEventHistoryEntityMapper userEventHistoryEntityMapper;

    public EventHistoryUseCases(UserEventHistoryRepository eventHistoryRepository,
                                UserEventHistoryEntityMapper userEventHistoryEntityMapper) {
        this.eventHistoryRepository = eventHistoryRepository;
        this.userEventHistoryEntityMapper = userEventHistoryEntityMapper;
    }

    @QueryHandler
    public UserEventHistory getUserHistory(GetUserHistory command) {
       return  getHistoryOrThrowError(command.userId());
    }

    @CommandHandler
    public UserEventHistory saveEventHistory(PostConsentEvent event) {
        UserEventHistory userEvent = getHistoryFromRepoOrCreate(event.userId());
        ConsentHistory consentFromEvent = userEventHistoryEntityMapper.fromPostEventToDom(event.consent());
        addEventToUserFromRepo(userEvent, consentFromEvent);
        return eventHistoryRepository.save(userEvent);
    }

    private UserEventHistory getHistoryFromRepoOrCreate(String userId) {
        UserEventHistory userEventHistoryFromRepo = eventHistoryRepository.getHistoryByUser(userId);
        if (userEventHistoryFromRepo == null) {
            return new UserEventHistory(userId, new ArrayList<>());
        }
        return userEventHistoryFromRepo;
    }

    private void addEventToUserFromRepo(UserEventHistory userEvent, ConsentHistory consent) {
        userEvent.getConsentHistoryList().add(consent);
    }

    private UserEventHistory getHistoryOrThrowError(String userId) {
        UserEventHistory history = eventHistoryRepository.getHistoryByUser(userId);
        if (history == null) {
            throw new UserHistoryNotFoundException("Id not found");
        }

        return history;
    }

}
