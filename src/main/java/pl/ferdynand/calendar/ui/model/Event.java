package pl.ferdynand.calendar.ui.model;

public class Event {
    private String title;
    private Date starts;
    private Date ends;

    public Event() {
    }

    public Event(String title, Date starts, Date ends) {
        this.title = title;
        this.starts = starts;
        this.ends = ends;
    }

    public String getTitle() {
        return title;
    }

    public Event setTitle(String title) {
        this.title = title;
        return this;
    }

    public Date getStarts() {
        return starts;
    }

    public Event setStarts(Date starts) {
        this.starts = starts;
        return this;
    }

    public Date getEnds() {
        return ends;
    }

    public Event setEnds(Date ends) {
        this.ends = ends;
        return this;
    }

    @Override
    public String toString() {
        return "Title: " + title + "\nStarts: " + starts + "\nEnds: " + ends;
    }
}
