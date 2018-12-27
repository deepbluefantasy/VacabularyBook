package com.example.words;

import java.util.UUID;

public class GUID {
    public static String getGUID(){
        UUID uuid = UUID.randomUUID();
        String a = uuid.toString();
        a = a.toUpperCase();
        a = a.replaceAll("-", "");
        return a;
    }
}
