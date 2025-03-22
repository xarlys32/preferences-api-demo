package com.vw.preferences.domain.usecase.event;

import com.vw.preferences.domain.exception.UserHistoryNotFoundException;
import com.vw.preferences.domain.model.Constants;
import com.vw.preferences.domain.model.event.ConsentHistory;
import com.vw.preferences.domain.model.event.UserEventHistory;
import com.vw.preferences.domain.model.user.Consent;
import com.vw.preferences.domain.port.event.UserEventHistoryRepository;
import com.vw.preferences.infrastructure.repository.event.adapter.UserEventHistoryEntityMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EventHistoryUseCasesTest {
    private final UserEventHistoryRepository userEventHistoryRepository = mock(UserEventHistoryRepository.class);
    private final UserEventHistoryEntityMapper userEventHistoryEntityMapper = new UserEventHistoryEntityMapper();
    private final EventHistoryUseCases eventHistoryUseCases = new EventHistoryUseCases(userEventHistoryRepository,
            userEventHistoryEntityMapper);

    PodamFactory podam = new PodamFactoryImpl();
    String user = "user1";
    GetUserHistory eventGetHistory = new GetUserHistory(user);
    ConsentHistory consentHistoryMock = podam.manufacturePojo(ConsentHistory.class);
    PostConsentEvent event = new PostConsentEvent(user, new Consent(Constants.CONSENTS_IDS.get(0), true));
    UserEventHistory userEventMock =podam.manufacturePojo(UserEventHistory.class);

    @Test
    public void getUserHistoryReturnsDataWhenFound() {
        when(userEventHistoryRepository.getHistoryByUser(user)).thenReturn(userEventMock);

        UserEventHistory userEvent = eventHistoryUseCases.getUserHistory(eventGetHistory);

        assertNotNull(userEvent);
        assertEquals(userEvent.getUserId(), userEventMock.getUserId());
    }

    @Test
    public void getUserHistoryReturnsErrorWhenNotFound() {
        when(userEventHistoryRepository.getHistoryByUser(user)).thenThrow(UserHistoryNotFoundException.class);

        assertThrows(UserHistoryNotFoundException.class, ()-> eventHistoryUseCases.getUserHistory(eventGetHistory) );
    }

    @Test
    public void saveEventHistoryReturnsDataWhenInsert() {
        when(userEventHistoryRepository.getHistoryByUser(user)).thenReturn(userEventMock);
        when(userEventHistoryRepository.save(userEventMock)).thenReturn(userEventMock);

        UserEventHistory userEvent =  eventHistoryUseCases.saveEventHistory(event);

        assertNotNull(userEvent);
    }

    @Test
    public void saveEventHistoryReturnsDataAndNewUserWhenInsert() {
        UserEventHistory newUser = new UserEventHistory(user, new ArrayList<>());
        when(userEventHistoryRepository.getHistoryByUser(user)).thenReturn(null);
        when(userEventHistoryRepository.save(any(UserEventHistory.class))).thenReturn(newUser);

        UserEventHistory userEvent = eventHistoryUseCases.saveEventHistory(event);

        assertNotNull(userEvent);
        assertEquals(newUser.getUserId(), userEvent.getUserId());
        verify(userEventHistoryRepository).getHistoryByUser(user);

    }
}
