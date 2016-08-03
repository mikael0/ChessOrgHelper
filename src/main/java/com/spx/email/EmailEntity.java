package com.spx.email;

/**
 * Created by timofb on 07-Dec-15.
 */
public class EmailEntity {

    private String address;
    private String html;
    private String theme;

    public EmailEntity(String address, String html, String theme) {
        this.address = address;
        this.html = html;
        this.theme = theme;
    }

    public EmailEntity() {

    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }
}
