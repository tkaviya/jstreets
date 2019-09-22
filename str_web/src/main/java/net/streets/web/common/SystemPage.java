package net.streets.web.common;

/***************************************************************************
 *                                                                         *
 * Created:     13 / 02 / 2017                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/

public class SystemPage {

    String idString;
    String baseXHTML;
    String includeXHTML;

    public SystemPage(String idString, String baseXHTML, String includeXHTML) {
        this.idString = idString;
        this.baseXHTML = baseXHTML;
        this.includeXHTML = includeXHTML;
    }

    public String getIdString() {
        return idString;
    }

    public void setIdString(String idString) {
        this.idString = idString;
    }

    public String getBaseXHTML() {
        return baseXHTML;
    }

    public void setBaseXHTML(String baseXHTML) {
        this.baseXHTML = baseXHTML;
    }

    public String getIncludeXHTML() {
        return includeXHTML;
    }

    public void setIncludeXHTML(String includeXHTML) {
        this.includeXHTML = includeXHTML;
    }
}
