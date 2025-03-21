package com.vw.preferences.domain.usecase.eventhistory;

import com.vw.preferences.domain.port.eventshistory.UserEventHistoryRepository;
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
        // añadir evento al historial

    }

}
