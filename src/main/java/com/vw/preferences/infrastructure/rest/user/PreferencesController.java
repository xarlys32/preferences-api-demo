package com.vw.preferences.infrastructure.rest.user;

import com.mongodb.MongoInterruptedException;
import com.vw.preferences.domain.exception.DuplicateMailException;
import com.vw.preferences.domain.exception.PreferencesNotFoundException;
import com.vw.preferences.domain.model.user.Consent;
import com.vw.preferences.domain.model.user.User;
import com.vw.preferences.domain.usecase.user.GetPreferences;
import com.vw.preferences.domain.usecase.user.PostAccountCreate;
import com.vw.preferences.domain.usecase.user.PostConsentUpdate;
import com.vw.preferences.infrastructure.rest.user.adapter.UserDTOMapper;
import com.vw.preferences.infrastructure.rest.user.dto.UserResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/preferences")
public class PreferencesController {

    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;
    private final UserDTOMapper preferencesDTOMapper;

    public PreferencesController(CommandGateway commandGateway,
                                 QueryGateway queryGateway,
                                 UserDTOMapper preferencesDTOMapper) {
        this.commandGateway = commandGateway;
        this.preferencesDTOMapper = preferencesDTOMapper;
        this.queryGateway = queryGateway;
    }

    @GetMapping()
    @Operation(summary = "Get event preferences by email")
    public ResponseEntity<UserResponseDTO> getPreferencesByUserMail(@RequestParam String email) throws ExecutionException, InterruptedException {
        var futurePreferences = queryGateway.query(new GetPreferences(email), User.class);
        User preference = futurePreferences.get();

        return ResponseEntity.ok(preferencesDTOMapper.toResponseDTO(preference));
    }

    @PostMapping("/register")
    @Operation(summary = "Register a mail")
    public ResponseEntity<UserResponseDTO> registerMail(@RequestParam String email) throws ExecutionException, InterruptedException {
        var newUserPreferences = commandGateway.sendAndWait(new PostAccountCreate(email));
        UserResponseDTO responseDTO = preferencesDTOMapper.toResponseDTO((User) newUserPreferences);

        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/update")
    @Operation(summary = "Update preferences with consent")
    public ResponseEntity<UserResponseDTO> updatePreferences(@RequestParam String email, @RequestParam String consent,
                                                             @RequestParam Boolean enabled) throws ExecutionException, InterruptedException {
        var newUserPreferences = commandGateway.sendAndWait(new PostConsentUpdate(email, new Consent(consent, enabled)));
        UserResponseDTO responseDTO = preferencesDTOMapper.toResponseDTO((User) newUserPreferences);

        return ResponseEntity.ok(responseDTO);
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<String> handleIllegalArgumentExceptions(IllegalArgumentException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler({PreferencesNotFoundException.class})
    public ResponseEntity<String> handlePreferencesNotFoundExceptions(PreferencesNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({MongoInterruptedException.class})
    public ResponseEntity<String> handleMongoInterruptedExceptions(MongoInterruptedException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({DuplicateMailException.class})
    public ResponseEntity<String> handleDuplicateMailExceptions(DuplicateMailException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
