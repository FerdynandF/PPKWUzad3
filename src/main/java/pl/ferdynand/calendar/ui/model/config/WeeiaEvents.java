package pl.ferdynand.calendar.ui.model.config;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class WeeiaEvents {
    private String weeiaURL;

    public WeeiaEvents(String weeiaURL) {
        this.weeiaURL = weeiaURL;
    }

    private Document getURLDocument(){
        try {
            return Jsoup.connect(this.weeiaURL).get();
        } catch (HttpStatusException ex ) {
            System.out.println("HttpStatusException message: " + ex);
        } catch (IOException ex) {
            System.out.println("IOException message: " + ex);
        }
        return new Document(weeiaURL);
    }

    public Elements getDescriptionsOfEvents() {
        return getURLDocument().select("p");
    }

    public Elements getDaysOfEvents(){
        return getURLDocument().select("a.active");
    }



}
