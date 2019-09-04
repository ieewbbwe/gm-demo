package com.example.calendar;

import java.util.List;

/**
 * Created by s2s8tb on 2019/6/25.
 */

public interface CalendarInterface {

    void insert(Events e);

    void insert(List<Events> elist);

    void delete(String eventId);

    void update(Events e);

    void update(String eventId);

    void query(String eventId);
}
