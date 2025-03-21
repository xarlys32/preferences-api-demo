package com.vw.preferences.infrastructure.rest.eventhistory;

import com.vw.preferences.domain.usecase.user.GetPreferences;
import com.vw.preferences.domain.usecase.user.PostMailPreferences;
import com.vw.preferences.infrastructure.rest.eventhistory.adapter.PreferencesDTOMapper;
import com.vw.preferences.infrastructure.rest.eventhistory.dtos.EventResponseDTO;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/preferences")
public class EventHistoryController {

    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;
    private final PreferencesDTOMapper preferencesDTOMapper;

    public EventHistoryController(CommandGateway commandGateway, PreferencesDTOMapper preferencesDTOMapper,
                                  QueryGateway queryGateway) {
        this.commandGateway = commandGateway;
        this.preferencesDTOMapper = preferencesDTOMapper;
        this.queryGateway = queryGateway;
    }

    @GetMapping()
    public ResponseEntity<EventResponseDTO> getPreferencesByUserId(@RequestParam String userId) throws ExecutionException, InterruptedException {
        var futurePreferences = queryGateway.query(new GetPreferences(userId), Event.class);
        Event preference = futurePreferences.get();

        return ResponseEntity.ok(preferencesDTOMapper.toResponseDTO(preference));
    }

    @PostMapping()
    public ResponseEntity<EventResponseDTO> registerMail(@RequestParam String mail) throws ExecutionException, InterruptedException {
        // validar mail
        var newUserPreferences = commandGateway.sendAndWait(new PostMailPreferences(mail));
        EventResponseDTO responseDTO = preferencesDTOMapper.toResponseDTO((Event) newUserPreferences);

        return ResponseEntity.ok(responseDTO);
    }
}
