package com.vw.preferences.domain.model.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEventHistory {
    private String userId;
    private List<ConsentHistory> consentHistoryList;
}
