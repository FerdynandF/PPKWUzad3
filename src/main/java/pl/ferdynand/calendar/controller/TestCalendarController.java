package pl.ferdynand.calendar.controller;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping(value = "/api/test/calendar")
public class TestCalendarController {

    @GetMapping
    public ResponseEntity<String> getMonth( @RequestParam(name = "year", defaultValue = "2019") int year,
                                            @RequestParam(name = "month", defaultValue = "11") int month){
        String calendarURL = "http://www.weeia.p.lodz.pl/pliki_strony_kontroler/kalendarz.php?rok=" + year + "&miesiac=" + month;
        Document document;
        try {
            document = Jsoup.connect(calendarURL).get();
        } catch (HttpStatusException ex ) {
            System.out.println("HttpStatusException message: " + ex);
        } catch (IOException ex) {
            System.out.println("IOException message: " + ex);
        }
        return new ResponseEntity<>(calendarURL, HttpStatus.OK);
    }
}
