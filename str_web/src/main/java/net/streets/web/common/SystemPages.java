package net.streets.web.common;

/***************************************************************************
 *                                                                         *
 * Created:     13 / 02 / 2017                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/

public class SystemPages {
    public static final SystemPage PAGE_LOGIN = new SystemPage("login", "login.xhtml", null);
    public static final SystemPage PAGE_REGISTRATION = new SystemPage("authentication", "registration.xhtml", null);
    public static final SystemPage PAGE_RESET_PASSWORD = new SystemPage("resetPassword", "reset_password.xhtml", null);
    public static final SystemPage PAGE_SUMMARY = new SystemPage("summary", "admin.xhtml", "summary.xhtml");
    public static final SystemPage PAGE_STREETS = new SystemPage("streets", "admin.xhtml", "streets.xhtml");

    public static final SystemPage PAGE_USER_UPDATE = new SystemPage("userEdit", "admin.xhtml", "u_update.xhtml");
    public static final SystemPage PAGE_AU_UPDATE = new SystemPage("auEdit", "admin.xhtml", "au_update.xhtml");

    public static final SystemPage PAGE_AUTH_REPORT = new SystemPage("authReport", "admin.xhtml", "a_report.xhtml");
    public static final SystemPage PAGE_SYS_REPORT = new SystemPage("sysReport", "admin.xhtml", "s_report.xhtml");

    public static final SystemPage PAGE_S_AUTH_REPORT = new SystemPage("sAuthReport", "admin.xhtml", "s_a_report.xhtml");

    public static final SystemPage PAGE_U_SETTINGS = new SystemPage("uSettings", "admin.xhtml", "settings.xhtml");

}
