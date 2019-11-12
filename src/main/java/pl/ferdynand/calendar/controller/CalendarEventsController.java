package pl.ferdynand.calendar.controller;

import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Version;
import net.fortuna.ical4j.util.RandomUidGenerator;
import net.fortuna.ical4j.util.UidGenerator;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.jsoup.select.Elements;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.ferdynand.calendar.ui.model.config.WeeiaEvents;
import pl.ferdynand.calendar.ui.model.response.EventRest;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(value = "/api/weeia/calendar")
public class CalendarEventsController {

    @GetMapping(value = "/events")
    public ResponseEntity<Object> monthEvents(@RequestParam(name = "year", defaultValue = "2019") int year,
            @RequestParam(name = "month", defaultValue = "12") String month) {
        String calendarURL = "http://www.weeia.p.lodz.pl/pliki_strony_kontroler/kalendarz.php?rok=" + year + "&miesiac=" + month;
        if (!validateMonth(month))
            return new ResponseEntity<>("Month must be in two digit format, from 01 to 12", HttpStatus.BAD_REQUEST);
        WeeiaEvents weeiaEvents = new WeeiaEvents(calendarURL);
        Elements days = weeiaEvents.getDaysOfEvents();
        Elements descriptions = weeiaEvents.getDescriptionsOfEvents();

        return new ResponseEntity<>(getEventsList(days, descriptions), HttpStatus.OK);
    }

    @GetMapping(value = "/events/file.ics")
    public ResponseEntity<Object> generateICS(@RequestParam(name = "year", defaultValue = "2019") int year,
            @RequestParam(name = "month", defaultValue = "12") String month,
            @RequestParam(name = "filename", defaultValue = "CalendarEvent") String filename,
            HttpServletResponse response) {
        String calendarURL = "http://www.weeia.p.lodz.pl/pliki_strony_kontroler/kalendarz.php?rok=" + year + "&miesiac=" + month;
        if (!validateMonth(month))
            return new ResponseEntity<>("Month must be in two digit format, from 01 to 12", HttpStatus.BAD_REQUEST);
        boolean flag = true;
        WeeiaEvents events = new WeeiaEvents(calendarURL);
        List weeiaEvents = getEventsList(events.getDaysOfEvents(), events.getDescriptionsOfEvents());
        if (weeiaEvents.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        filename += ".ics";
        Calendar iCal = new Calendar();
        iCal.getProperties().add(new ProdId("-//Apple Inc.//Mac OS X 10.15.1//EN"));
        iCal.getProperties().add(Version.VERSION_2_0);
        iCal.getProperties().add(CalScale.GREGORIAN);

        int properMonth = Integer.parseInt(month);
        for (Object weeiaEvent : weeiaEvents) {
            java.util.Calendar calendar = java.util.Calendar.getInstance();
            calendar.set(java.util.Calendar.YEAR, year);
            calendar.set(java.util.Calendar.MONTH, (properMonth - 1));
            calendar.set(java.util.Calendar.DAY_OF_MONTH, ((EventRest) weeiaEvent).getDay());
            //        Initialize as an all-day event
            VEvent event = new VEvent(new Date(calendar.getTime()), ((EventRest) weeiaEvent).getDescription());
            //        Generate UID for the event
            UidGenerator uGen = new RandomUidGenerator();
            event.getProperties().add(uGen.generateUid());
            iCal.getComponents().add(event);
        }

        try {
            FileOutputStream out = new FileOutputStream(filename);
            CalendarOutputter outputter = new CalendarOutputter();
            outputter.output(iCal, out);

            // get your file as InputStream
            InputStream is = new FileInputStream(new File(filename));
            // copy it to response's OutputStream
            response.setContentType("application/ics");
            response.addHeader("Content-Disposition", "attachment; filename="+filename);
            IOUtils.copy(is, response.getOutputStream());
            response.flushBuffer();
            return new ResponseEntity<>(is, HttpStatus.CREATED);
        } catch (IOException ex) {
            return new ResponseEntity<>("Not created: IOException while generating file named: " + filename, HttpStatus.BAD_REQUEST);
        }
    }

    private boolean generateICSFile(Calendar ical, String filename) {
        try {
            FileOutputStream out = new FileOutputStream(filename);
//            Resource
            CalendarOutputter outputter = new CalendarOutputter();
            outputter.output(ical, out);
        } catch (IOException ex) {
            return false;
        }
        return true;
    }

    private List<EventRest> getEventsList(Elements days, Elements descriptions) {
        int eventDaysCount = days.size();
        int[] eventWeekday = new int[eventDaysCount];
        String[] eventDescription = new String[eventDaysCount];
        List<EventRest> events = new ArrayList<>();

        for (int index = 0; index < eventWeekday.length; index++) {
            try {
                eventWeekday[index] = Integer.parseInt(days.get(index).text());
            } catch (NumberFormatException ex) {
                return Collections.emptyList();
            }
            eventDescription[index] = descriptions.get(index).text();
            EventRest event = new EventRest(eventWeekday[index], eventDescription[index]);
            events.add(event);
        }

        return events;
    }

    private boolean validateMonth(String month) {
        try {
            int monthInt = Integer.parseInt(month);
            if (monthInt > 12 || monthInt < 1)
                return false;
            if(monthInt < 10 && month.length()<2)
                return false;
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }

}
