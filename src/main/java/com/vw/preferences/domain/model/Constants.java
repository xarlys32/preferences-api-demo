package com.vw.preferences.domain.model;

import java.util.List;

public class Constants {
    public static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
    public static final List<String> CONSENTS_IDS = List.of("sms_notifications", "mail_notifications");
}
