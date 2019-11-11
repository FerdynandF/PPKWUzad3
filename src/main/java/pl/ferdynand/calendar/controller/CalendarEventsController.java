package pl.ferdynand.calendar.controller;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.ferdynand.calendar.ui.model.response.EventRest;

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
    TODO Create ICS file
    TODO Export ICS file to iPhone
    TODO Import ICS file to calendar on iPhone
     */
    @GetMapping(value = "/events/file.ics")
    public ResponseEntity<String> generateICS(@RequestParam(name = "year", defaultValue = "2019") int year,
                                            @RequestParam(name = "month", defaultValue = "12") String month,
                                            @RequestParam(name = "filename", defaultValue = "CalendarEvent") String filename) {


        String response = "Events from year:\t" + year + ",\nmonth:\t" + month + "\ncreated in file " + filename + ".ics";
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping(value = "/events")
    public ResponseEntity<List> monthEvents(@RequestParam(name = "year", defaultValue = "2019") int year,@RequestParam(name = "month", defaultValue = "12") String month) {

        String calendarURL = "http://www.weeia.p.lodz.pl/pliki_strony_kontroler/kalendarz.php?rok=" + year + "&miesiac=" + month;

        Elements days = getDaysOfEvents(calendarURL);
        Elements descriptions = getDescriptionsOfEvents(calendarURL);

        return new ResponseEntity<>(getEventsList(days, descriptions), HttpStatus.OK);
    }

    private Document getURLDocument(String weeiaURL){
        try {
            return Jsoup.connect(weeiaURL).get();
        } catch (HttpStatusException ex ) {
            System.out.println("HttpStatusException message: " + ex);
        } catch (IOException ex) {
            System.out.println("IOException message: " + ex);
        }
        return new Document(weeiaURL);
    }

    private Elements getDescriptionsOfEvents(String weeiaURL) {
        return getURLDocument(weeiaURL).select("p");
    }

    private Elements getDaysOfEvents(String weeiaURL){
        return getURLDocument(weeiaURL).select("a.active");
    }

    private List getEventsList (Elements days, Elements descriptions) {
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
