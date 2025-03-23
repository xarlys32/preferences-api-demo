package com.vw.preferences.infrastructure.rest.event;
import com.vw.preferences.domain.exception.UserHistoryNotFoundException;
import com.vw.preferences.domain.model.event.UserEventHistory;
import com.vw.preferences.domain.usecase.event.GetUserHistory;
import com.vw.preferences.infrastructure.rest.event.adapter.UseEventHistoryDTOMapper;
import org.axonframework.queryhandling.QueryGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EventHistoryControllerTest {
    private final QueryGateway queryGateway = mock(QueryGateway.class);
    private final UseEventHistoryDTOMapper preferencesDTOMapper = new UseEventHistoryDTOMapper();

    EventHistoryController constroller = new EventHistoryController(queryGateway, preferencesDTOMapper);
    PodamFactory podam = new PodamFactoryImpl();
    String userId = "1";
    UserEventHistory userHistoryMock = podam.manufacturePojo(UserEventHistory.class);

    @Test
    public void getPreferencesByUserIdReturnsDataWhenFound() throws ExecutionException, InterruptedException {
        var futureHistory = new CompletableFuture<UserEventHistory>();
        futureHistory.complete(userHistoryMock);
        when(queryGateway.query(any(GetUserHistory.class), eq(UserEventHistory.class))).thenReturn(futureHistory);

        var response = constroller.getPreferencesByUserId(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userHistoryMock.getUserId(), response.getBody().getUserId());
    }

    @Test
    public void getPreferencesByUserIdThrowUserHistoryNotFoundWhenNotFound() throws ExecutionException, InterruptedException {
        when(queryGateway.query(any(GetUserHistory.class), eq(UserEventHistory.class))).thenThrow(UserHistoryNotFoundException.class);

        assertThrows(UserHistoryNotFoundException.class, () -> constroller.getPreferencesByUserId(userId));
    }
}
