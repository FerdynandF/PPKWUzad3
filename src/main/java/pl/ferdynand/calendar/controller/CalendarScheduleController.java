package pl.ferdynand.calendar.controller;

import org.jsoup.select.Elements;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.ferdynand.calendar.ui.model.config.PLodzSchedule;

@RestController
@RequestMapping(value = "/api/plodz/calendar")
public class CalendarScheduleController {

    @GetMapping(value = "/test")
    public ResponseEntity<String> h3Headers() {
        String plodzURL = "https://www.p.lodz.pl/pl/rozklad-roku-akademickiego-20192010";
// bez uwagi
        PLodzSchedule pLodzSchedule = new PLodzSchedule(plodzURL);

        Elements h3Headers = pLodzSchedule.getH3Headers();

        return new ResponseEntity<>(h3Headers.toString(), HttpStatus.OK);
    }
}
