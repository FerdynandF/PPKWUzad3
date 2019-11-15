package pl.ferdynand.calendar.ui.model.config;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class PLodzSchedule {
    private String plodzURL;

    public PLodzSchedule(String plodzURL) {
        this.plodzURL = plodzURL;
    }

    private Document getURLDocument() {
        return new URLDocument(this.plodzURL).getURLDocument();
    }

    public Elements getH3Headers() {
        return getURLDocument().select("h3.naglowek_h3");
    }

    public Element getWinterSemesterPeriod() {
        return getURLDocument().select("h3.naglowek_h3").nextAll("p").first();
    }
}
