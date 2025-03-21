package com.vw.preferences.domain.port.event;

import com.vw.preferences.domain.model.event.UserEventHistory;

public interface UserEventHistoryRepository {
    UserEventHistory save(UserEventHistory userEventHistory);
    UserEventHistory getHistoryByUser(String userId);
}
