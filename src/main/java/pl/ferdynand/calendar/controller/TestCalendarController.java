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
@RequestMapping(value = "/api/calendar")
public class TestCalendarController {

    @GetMapping
    public ResponseEntity<String> getURI( @RequestParam(name = "year", defaultValue = "2019") int year,
                                            @RequestParam(name = "month", defaultValue = "12") String month){
        String calendarURL = "http://www.weeia.p.lodz.pl/pliki_strony_kontroler/kalendarz.php?rok=" + year + "&miesiac=" + month;
        return new ResponseEntity<>(calendarURL, HttpStatus.OK);
    }

    @GetMapping(value = "/events/file.ics")
    public ResponseEntity<String> generateICS(@RequestParam(name = "year", defaultValue = "2019") int year, @RequestParam(name = "month", defaultValue = "12") String month) {
        if(year == 2019){
            return new ResponseEntity<>(String.valueOf(year), HttpStatus.OK);
        }
        return new ResponseEntity<>(String.valueOf(year), HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/events")
    public ResponseEntity<List> monthEvents(@RequestParam(name = "year", defaultValue = "2019") int year,@RequestParam(name = "month", defaultValue = "12") String month) {

        String calendarURL = "http://www.weeia.p.lodz.pl/pliki_strony_kontroler/kalendarz.php?rok=" + year + "&miesiac=" + month;
        Document document;
        try {
            document = Jsoup.connect(calendarURL).get();
        } catch (HttpStatusException ex ) {
            System.out.println("HttpStatusException message: " + ex);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (IOException ex) {
            System.out.println("IOException message: " + ex);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Elements days = document.select("a.active");
        Elements descriptions = document.select("p");

        return new ResponseEntity<>(getEventsList(days, descriptions), HttpStatus.OK);
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
