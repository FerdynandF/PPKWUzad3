package pl.ferdynand.calendar.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.ferdynand.calendar.ui.model.Date;
import pl.ferdynand.calendar.ui.model.Event;
import pl.ferdynand.calendar.ui.model.config.PLodzSchedule;

@RestController
@RequestMapping(value = "/api/plodz/calendar")
public class CalendarScheduleController {

    @GetMapping(value = "/test")
    public ResponseEntity<String> h3Headers() {
        String plodzURL = "https://www.p.lodz.pl/pl/rozklad-roku-akademickiego-20192010";
        // bez uwag
        Event winterSemester = new Event();

        PLodzSchedule pLodzSchedule = new PLodzSchedule(plodzURL);
        winterSemester.setTitle(pLodzSchedule.getH3Headers().get(1).text());

        String winterSemesterPeriod = pLodzSchedule.getWinterSemesterPeriod().text();

        winterSemester.setStarts(new Date(winterSemesterPeriod.split(" ")[0]));
        winterSemester.setEnds(new Date(winterSemesterPeriod.split(" ")[3]));

        return new ResponseEntity<>(winterSemester.toString(), HttpStatus.OK);
    }

}
