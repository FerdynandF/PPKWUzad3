package pl.ferdynand.calendar.ui.model.config;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class WeeiaEvents {

    private String weeiaURL;

    public WeeiaEvents(String weeiaURL) {
        this.weeiaURL = weeiaURL;
    }

    private Document getURLDocument() {
        return new URLDocument(this.weeiaURL).getURLDocument();
    }

    public Elements getDescriptionsOfEvents() {
        return getURLDocument().select("p");
    }

    public Elements getDaysOfEvents() {
        return getURLDocument().select("a.active");
    }

}
