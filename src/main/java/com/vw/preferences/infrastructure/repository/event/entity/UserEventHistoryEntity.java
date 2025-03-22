package com.vw.preferences.infrastructure.repository.event.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "userEventsHistory")
public class UserEventHistoryEntity {
    @Id
    private String userId;
    @Field(name = "consent_history")
    private List<ConsentHistoryEntity> consentHistoryList;
}
