package com.spx.parsers;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URL;

/**
 * Created by timofb on 24-Jun-16.
 */
public class DefSmetaFERParser {

    private static final Logger LOGGER = Logger.getLogger(DefSmetaFERParser.class);

    private String url;

    public DefSmetaFERParser(final String url) {
        this.url = url;
    }

    public String getFullHTML() {
        try {
            Document doc = Jsoup.connect(url).get();
            return doc.outerHtml();
        }
        catch (IOException ex) {
            LOGGER.error(ex.getMessage());
        }
        return null;
    }

}
