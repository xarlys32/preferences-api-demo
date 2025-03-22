package com.vw.preferences.infrastructure.rest.event;

import com.vw.preferences.domain.model.event.UserEventHistory;
import com.vw.preferences.domain.usecase.event.GetUserHistory;
import com.vw.preferences.domain.usecase.user.GetPreferences;
import com.vw.preferences.domain.usecase.user.PostAccountCreate;
import com.vw.preferences.infrastructure.rest.event.adapter.UseEventHistoryDTOMapper;
import com.vw.preferences.infrastructure.rest.event.dtos.UserEventHistoryResponseDTO;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/history")
public class EventHistoryController {

    private final QueryGateway queryGateway;
    private final UseEventHistoryDTOMapper preferencesDTOMapper;

    public EventHistoryController(UseEventHistoryDTOMapper preferencesDTOMapper,
                                  QueryGateway queryGateway) {
        this.preferencesDTOMapper = preferencesDTOMapper;
        this.queryGateway = queryGateway;
    }

    @GetMapping()
    public ResponseEntity<UserEventHistoryResponseDTO> getPreferencesByUserId(@RequestParam String userId) throws ExecutionException, InterruptedException {
        var futureResponse = queryGateway.query(new GetUserHistory(userId), UserEventHistory.class);
        UserEventHistory history = futureResponse.get();

        return ResponseEntity.ok(preferencesDTOMapper.toResponseDTO(history));
    }

}
