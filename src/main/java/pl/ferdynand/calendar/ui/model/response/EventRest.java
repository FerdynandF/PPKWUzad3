package pl.ferdynand.calendar.ui.model.response;

public class EventRest {
    private int day;
    private String description;

    public EventRest(int day, String description) {
        this.day = day;
        this.description = description;
    }

    public int getDay() {
        return day;
    }

    public String getDescription() {
        return description;
    }
}
