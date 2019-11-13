package pl.ferdynand.calendar.ui.model.config;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class URLDocument {

    String url;

    public URLDocument(String url) {
        this.url = url;
    }

    Document getURLDocument() {
        try {
            return Jsoup.connect(this.url).get();
        } catch (HttpStatusException ex) {
            System.out.println("HttpStatusException message: " + ex);
        } catch (IOException ex) {
            System.out.println("IOException message: " + ex);
        }
        return new Document(url);
    }
}
