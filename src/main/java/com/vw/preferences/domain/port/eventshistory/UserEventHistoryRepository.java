package com.vw.preferences.domain.port.eventshistory;

import com.vw.preferences.domain.model.eventhistory.UserEventHistory;

public interface UserEventHistoryRepository {
    UserEventHistory save(UserEventHistory userEventHistory);
    UserEventHistory createHistory(String mail);
    UserEventHistory getHistoryByUser(String userId);
}
