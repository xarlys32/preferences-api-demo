package com.vw.preferences.infrastructure.rest.user;

import com.vw.preferences.domain.model.user.Consent;
import com.vw.preferences.domain.model.user.User;
import com.vw.preferences.domain.usecase.user.GetPreferences;
import com.vw.preferences.domain.usecase.user.PostAccountCreate;
import com.vw.preferences.domain.usecase.user.PostConsentUpdate;
import com.vw.preferences.infrastructure.rest.user.adapter.UserDTOMapper;
import com.vw.preferences.infrastructure.rest.user.dtos.UserResponseDTO;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/preferences")
public class PreferencesController {

    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;
    private final UserDTOMapper preferencesDTOMapper;

    public PreferencesController(CommandGateway commandGateway, UserDTOMapper preferencesDTOMapper,
                                 QueryGateway queryGateway) {
        this.commandGateway = commandGateway;
        this.preferencesDTOMapper = preferencesDTOMapper;
        this.queryGateway = queryGateway;
    }

    @GetMapping()
    public ResponseEntity<UserResponseDTO> getPreferencesByUserId(@RequestParam String userId) throws ExecutionException, InterruptedException {
        //Mail
        var futurePreferences = queryGateway.query(new GetPreferences(userId), User.class);
        User preference = futurePreferences.get();

        return ResponseEntity.ok(preferencesDTOMapper.toResponseDTO(preference));
    }

    @PostMapping()
    public ResponseEntity<UserResponseDTO> registerMail(@RequestParam String email) throws ExecutionException, InterruptedException {
        var newUserPreferences = commandGateway.sendAndWait(new PostAccountCreate(email));
        UserResponseDTO responseDTO = preferencesDTOMapper.toResponseDTO((User) newUserPreferences);

        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping()
    public ResponseEntity<UserResponseDTO> updatePreferences(@RequestParam String email, @RequestParam String consent,
                                                             @RequestParam String enabled) throws ExecutionException, InterruptedException {
        var newUserPreferences = commandGateway.sendAndWait(new PostConsentUpdate(email, new Consent(consent, true)));
        UserResponseDTO responseDTO = preferencesDTOMapper.toResponseDTO((User) newUserPreferences);

        return ResponseEntity.ok(responseDTO);
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<String> handleIllegalArgumentExceptions() {
        return ResponseEntity.badRequest().build();
    }

}
