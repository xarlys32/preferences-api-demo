package com.vw.preferences.domain.usecase.user;

import com.vw.preferences.domain.exception.PreferencesNotFoundException;
import com.vw.preferences.domain.model.Constants;
import com.vw.preferences.domain.model.user.Consent;
import com.vw.preferences.domain.model.user.User;
import com.vw.preferences.domain.port.user.UserRepository;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.List;

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
    Consent validConsent = new Consent(Constants.CONSENTS_IDS.get(0), true);

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

    @Test
    public void getPreferencesReturnsErrorWhenMailIsWrong() {
        when(userRepository.getPreferencesByUser("a")).thenThrow(IllegalArgumentException.class);

        GetPreferences query = new GetPreferences("a");

        assertThrows(IllegalArgumentException.class, () -> userUseCases.getPreferences(query));
    }

    @Test
    public void savePreferencesReturnsDataWhenIsCorrect() {
        User updateUser = new User("1", email, List.of(validConsent));
        when(userRepository.getPreferencesByUser(email)).thenReturn(userMock);
        when(userRepository.save(any(User.class))).thenReturn(updateUser);

        PostConsentUpdate command = new PostConsentUpdate(email, validConsent);
        User response = userUseCases.savePreferences(command);

        assertEquals(updateUser, response);
        assertEquals(response.getConsents().get(0).getConsentId(), validConsent.getConsentId());
        verify(userRepository).save(any(User.class));
    }

    @Test
    public void savePreferencesReturnsErrorWhenConsentIsEmpty() {
        when(userRepository.getPreferencesByUser(email)).thenReturn(userMock);
        when(userRepository.save(any(User.class))).thenThrow(IllegalArgumentException.class);

        PostConsentUpdate command = new PostConsentUpdate(email, null);

        assertThrows(IllegalArgumentException.class, () -> userUseCases.savePreferences(command));
    }

    @Test
    public void savePreferencesReturnsErrorWhenConsentIsWrong() {
        when(userRepository.getPreferencesByUser(email)).thenReturn(userMock);
        when(userRepository.save(any(User.class))).thenThrow(IllegalArgumentException.class);

        Consent wrongConsent = new Consent("wrong", true);
        PostConsentUpdate command = new PostConsentUpdate(email, wrongConsent);

        assertThrows(IllegalArgumentException.class, () -> userUseCases.savePreferences(command));
    }

    @Test
    public void savePreferencesReturnsErrorWhenConsentEnabledIsNull() {
        when(userRepository.getPreferencesByUser(email)).thenReturn(userMock);
        when(userRepository.save(any(User.class))).thenThrow(IllegalArgumentException.class);

        Consent wrongConsent = new Consent(Constants.CONSENTS_IDS.get(0), null);
        PostConsentUpdate command = new PostConsentUpdate(email, wrongConsent);

        assertThrows(IllegalArgumentException.class, () -> userUseCases.savePreferences(command));
    }

}
