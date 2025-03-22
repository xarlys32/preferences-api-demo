package com.vw.preferences.infrastructure.repository.user.entity;

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
@Document(collection = "userPreferences")
public class UserEntity {
    @Id
    private  String userId;
    @Field(name = "email")
    private  String email;
    @Field(name = "consents")
    private List<ConsentEntity> Consents;
}
