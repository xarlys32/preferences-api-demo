package com.vw.preferences.domain.usecase.eventhistory;

import com.vw.preferences.domain.port.eventshistory.UserEventHistoryRepository;
import com.vw.preferences.infrastructure.repository.eventhistory.entity.UserEventHistoryEntity;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

@Component
public class EventHistoryUseCases {

    private final UserEventHistoryRepository eventHistoryRepository;

    public EventHistoryUseCases(UserEventHistoryRepository eventHistoryRepository) {
        this.eventHistoryRepository = eventHistoryRepository;
    }

    @QueryHandler
    public void saveEventHistory(PostEventHistory event) {
        /// buscar si existe el usuario

        UserEventHistoryEntity userEventHistoryEntity = eventHistoryRepository.getHistoryByUser(event.user().getId());
        // añadir evento al historial

    }

    private UserEventHistoryEntity getUserOrCreateOne()

}
