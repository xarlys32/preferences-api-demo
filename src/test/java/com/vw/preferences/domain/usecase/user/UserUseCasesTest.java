package com.vw.preferences.domain.usecase.user;

import com.vw.preferences.domain.exception.PreferencesNotFoundException;
import com.vw.preferences.domain.model.user.User;
import com.vw.preferences.domain.port.user.UserRepository;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserUseCasesTest {
    private final UserRepository userRepository = mock(UserRepository.class);
    private final CommandGateway commandGateway = mock(CommandGateway.class);

    private final UserUseCases userUseCases = new UserUseCases(userRepository, commandGateway);
    private String email = "a@gmail.com";

    PodamFactory podam = new PodamFactoryImpl();
    User userMock = podam.manufacturePojo(User.class);

    @Test
    public void getPreferencesReturnsUserWhenFound() {
        when(userRepository.getPreferencesByUser(email)).thenReturn(userMock);

        GetPreferences query = new GetPreferences(email);
        User result = userUseCases.getPreferences(query);

        assertEquals(userMock, result);
    }

    @Test
    public void getPreferencesReturnsErrorWhenNotFound() {
        when(userRepository.getPreferencesByUser(email)).thenThrow(PreferencesNotFoundException.class);

        GetPreferences query = new GetPreferences(email);

        assertThrows(PreferencesNotFoundException.class, () -> userUseCases.getPreferences(query));
    }

}
