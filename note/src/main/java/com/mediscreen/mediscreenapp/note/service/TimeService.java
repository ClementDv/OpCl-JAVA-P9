package com.mediscreen.mediscreenapp.note.service;

import java.time.ZonedDateTime;

public interface TimeService {

    /**
     *
     * @return the current ZoneDateTime in UTC without nano seconds
     */

    ZonedDateTime now();
}
