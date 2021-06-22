package com.mediscreen.mediscreenapp.note.service.impl;

import com.mediscreen.mediscreenapp.note.service.TimeService;
import org.springframework.stereotype.Service;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@Service
public class TimeServiceImpl implements TimeService {
    @Override
    public ZonedDateTime now() {
        return ZonedDateTime.now(ZoneOffset.UTC).withNano(0);
    }
}
