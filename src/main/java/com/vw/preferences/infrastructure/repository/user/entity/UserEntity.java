package com.vw.preferences.infrastructure.repository.user.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class UserEntity {
    private  String userId;
    private  String email;

    private List<ConsentEntity> Consents;
}
