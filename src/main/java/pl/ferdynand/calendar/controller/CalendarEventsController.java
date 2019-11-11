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
import org.jsoup.select.Elements;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.ferdynand.calendar.ui.model.config.WeeiaEvents;
import pl.ferdynand.calendar.ui.model.response.EventRest;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/weeia/calendar")
public class CalendarEventsController {

    @GetMapping
    public ResponseEntity<String> getURI( @RequestParam(name = "year", defaultValue = "2019") int year,
                                            @RequestParam(name = "month", defaultValue = "12") String month){
        String calendarURL = "http://www.weeia.p.lodz.pl/pliki_strony_kontroler/kalendarz.php?rok=" + year + "&miesiac=" + month;
        return new ResponseEntity<>(calendarURL, HttpStatus.OK);
    }

    /*
    TODO Create simple ICS file
    TODO Create proper ICS file
    TODO Export ICS file to iPhone
    TODO Import ICS file to calendar on iPhone
     */
    @GetMapping(value = "/events/file.ics")
    public ResponseEntity<String> generateICS(@RequestParam(name = "year", defaultValue = "2019") int year,
                                            @RequestParam(name = "month", defaultValue = "12") String month,
                                            @RequestParam(name = "filename", defaultValue = "CalendarEvent") String filename) {
        String calendarURL = "http://www.weeia.p.lodz.pl/pliki_strony_kontroler/kalendarz.php?rok=" + year + "&miesiac=" + month;
        WeeiaEvents events = new WeeiaEvents(calendarURL);
        List weeiaEvents = getEventsList(events.getDaysOfEvents(), events.getDescriptionsOfEvents());
        if(weeiaEvents.isEmpty())
            return new ResponseEntity<>("There is no events to generate", HttpStatus.NO_CONTENT);

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
            UidGenerator ugen = new RandomUidGenerator();
            event.getProperties().add(ugen.generateUid());
            iCal.getComponents().add(event);
        }

        if(!generateICSFile(iCal, filename))
            return new ResponseEntity<>("Not created: IOException while generating file named: " + filename , HttpStatus.BAD_REQUEST);

        String response = "Events from year:\t" + year + ",\nmonth:\t" + month + "\ncreated in file " + filename;
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    private boolean generateICSFile(Calendar ical, String filename) {
        try{
            FileOutputStream out = new FileOutputStream(filename);
            CalendarOutputter outputter = new CalendarOutputter();
            outputter.output(ical, out);
        } catch (IOException ex) {
            System.out.println("IOException message: " + ex.getMessage());
            return false;
        }
        return true;
    }

    @GetMapping(value = "/events")
    public ResponseEntity<List> monthEvents(@RequestParam(name = "year", defaultValue = "2019") int year,@RequestParam(name = "month", defaultValue = "12") String month) {
        String calendarURL = "http://www.weeia.p.lodz.pl/pliki_strony_kontroler/kalendarz.php?rok=" + year + "&miesiac=" + month;

        WeeiaEvents weeiaEvents = new WeeiaEvents(calendarURL);
        Elements days = weeiaEvents.getDaysOfEvents();
        Elements descriptions = weeiaEvents.getDescriptionsOfEvents();

        return new ResponseEntity<>(getEventsList(days, descriptions), HttpStatus.OK);
    }

    private List<EventRest> getEventsList (Elements days, Elements descriptions) {
        int eventDaysCount = days.size();
        int[] eventWeekday = new int[eventDaysCount];
        String[] eventDescription = new String[eventDaysCount];
        List<EventRest> events = new ArrayList<>();

        for (int index = 0; index < eventWeekday.length; index++) {
            try{
                eventWeekday[index] = Integer.parseInt(days.get(index).text());
            } catch (NumberFormatException ex) {
                System.out.println("Number Format Exception message: " + ex.getMessage());
            }
            eventDescription[index] = descriptions.get(index).text();
            EventRest event = new EventRest(eventWeekday[index], eventDescription[index]);
            events.add(event);
        }
        return events;
    }

}
