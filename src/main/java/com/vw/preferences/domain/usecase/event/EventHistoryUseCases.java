package com.vw.preferences.domain.usecase.event;

import com.vw.preferences.domain.model.event.ConsentHistory;
import com.vw.preferences.domain.model.event.UserEventHistory;
import com.vw.preferences.domain.port.event.UserEventHistoryRepository;
import com.vw.preferences.infrastructure.repository.event.adapter.UserEventHistoryEntityMapper;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.List;

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
       return  eventHistoryRepository.getHistoryByUser(command.userId());
    }

    @QueryHandler
    public UserEventHistory saveEventHistory(PostConsentEvent event) {
        UserEventHistory userEvent = getUserFromRepo(event.userId());
        ConsentHistory consentFromEvent = userEventHistoryEntityMapper.fromPostEventToDom(event.consent());
        addEventToUserFromRepo(userEvent, consentFromEvent);
        return eventHistoryRepository.save(userEvent);
    }

    private UserEventHistory getUserFromRepo(String userId) {
        UserEventHistory userEventHistoryFromRepo = eventHistoryRepository.getHistoryByUser(userId);
        if (userEventHistoryFromRepo == null) {
            return new UserEventHistory(userId, List.of());
        }
        return userEventHistoryFromRepo;
    }

    private void addEventToUserFromRepo(UserEventHistory userEvent, ConsentHistory consent) {
        userEvent.getConsentHistoryList().add(consent);
    }

}
