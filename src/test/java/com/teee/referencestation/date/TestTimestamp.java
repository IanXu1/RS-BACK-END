package com.teee.referencestation.date;


import java.time.Instant;
import java.time.ZoneId;

public class TestTimestamp {

    public static void main(String[] args) {
        Instant now = Instant.ofEpochMilli(1551196800000L);
        System.out.println(now.atZone(ZoneId.systemDefault()).toLocalDateTime());
    }
}
