package com.vw.preferences.infrastructure.rest.user;
import com.vw.preferences.domain.exception.DuplicateMailException;
import com.vw.preferences.domain.model.user.User;
import com.vw.preferences.domain.usecase.user.GetPreferences;
import com.vw.preferences.domain.usecase.user.PostAccountCreate;
import com.vw.preferences.infrastructure.rest.user.adapter.UserDTOMapper;
import com.vw.preferences.infrastructure.rest.user.dto.UserResponseDTO;
import org.axonframework.commandhandling.gateway.CommandGateway;
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
public class PreferencesControllerTest {
    private final CommandGateway commandGateway = mock(CommandGateway.class);
    private final QueryGateway queryGateway = mock(QueryGateway.class);

    private final UserDTOMapper preferencesDTOMapper = new UserDTOMapper();
    private PreferencesController controller = new PreferencesController(commandGateway,
            queryGateway, preferencesDTOMapper);

    PodamFactory podam = new PodamFactoryImpl();
    UserResponseDTO userResponseMock = podam.manufacturePojo(UserResponseDTO.class);
    String validEmail = "a@gmail.com";
    User userMock = podam.manufacturePojo(User.class);

    @Test
    public void getPreferencesByUserMailReturnsDataWhenFound() throws ExecutionException, InterruptedException {
        var futurePreferences = new CompletableFuture<User>();
        futurePreferences.complete(userMock);
        when(queryGateway.query(any(GetPreferences.class), eq(User.class))).thenReturn(futurePreferences);

        var response = controller.getPreferencesByUserMail(validEmail);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userMock.getUserId(), response.getBody().getUserId());
    }

    @Test
    public void getPreferencesByUserMailThrowsIllegalArgumentWhenWrongMail() throws ExecutionException, InterruptedException {
        when(queryGateway.query(any(GetPreferences.class), eq(User.class))).thenThrow(IllegalArgumentException.class);

        assertThrows(IllegalArgumentException.class, () -> controller.getPreferencesByUserMail("a"));
    }

    @Test
    public void registerMailReturnsDataWhenCreated() throws ExecutionException, InterruptedException {
        when(commandGateway.sendAndWait(any(PostAccountCreate.class))).thenReturn(userMock);

        var response = controller.registerMail(validEmail);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userMock.getUserId(), response.getBody().getUserId());
    }

    @Test
    public void registerMailThrowsDuplicateMailWhenWrongMail() throws ExecutionException, InterruptedException {
        when(commandGateway.sendAndWait(any(PostAccountCreate.class))).thenThrow(DuplicateMailException.class);

        assertThrows(DuplicateMailException.class, () -> controller.registerMail(validEmail));
    }
}
