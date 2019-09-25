package net.streets.web.common;

import net.streets.common.structure.Pair;
import net.streets.persistence.entity.enumeration.*;
import org.springframework.stereotype.Component;

import javax.enterprise.context.ApplicationScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static net.streets.persistence.helper.DaoManager.getEntityManagerRepo;

/***************************************************************************
 *                                                                         *
 * Created:     13 / 02 / 2017                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/

@ApplicationScoped
@Component
public class UpdateOptions implements Serializable {

    private List<Boolean> booleanList = new ArrayList<>(asList(true, false));
    private List<str_country> countries;
    private List<str_language> languages;
    private List<str_auth_group> authGroups;
    private List<str_channel> channels;
    private List<str_event_type> eventTypes;
    private List<str_response_code> userStatuses;
    private List<str_response_code> transactionStatuses;
    private List<str_response_code> logStatuses;

    public UpdateOptions() {
        initCountries();
        initLanguages();
        initAuthGroups();
        initChannels();
        initEventTypes();
        initUserStatuses();
        initTransactionStatuses();
        initLogStatuses();
    }

    private List<str_country> initCountries() {
        countries = getEntityManagerRepo().findAll(str_country.class);
        return countries;
    }

    private List<str_language> initLanguages() {
        languages = getEntityManagerRepo().findAll(str_language.class);
        return languages;
    }

    private List<str_auth_group> initAuthGroups() {
        authGroups = getEntityManagerRepo().findAll(str_auth_group.class);
        return authGroups;
    }

    private List<str_channel> initChannels() {
        channels = getEntityManagerRepo().findAll(str_channel.class);
        return channels;
    }

    private List<str_event_type> initEventTypes() {
        eventTypes = getEntityManagerRepo().findAll(str_event_type.class);
        return eventTypes;
    }

    private List<str_response_code> initUserStatuses() {
        ArrayList<Pair<String, ?>> searchTerms = new ArrayList<>();
        searchTerms.add(new Pair<>("name", "ACC_ACTIVE"));
        searchTerms.add(new Pair<>("name", "ACC_INACTIVE"));
        searchTerms.add(new Pair<>("name", "ACC_SUSPENDED"));
        searchTerms.add(new Pair<>("name", "ACC_CLOSED"));
        searchTerms.add(new Pair<>("name", "ACC_PASSWORD_TRIES_EXCEEDED"));
        searchTerms.add(new Pair<>("name", "ACC_PASSWORD_EXPIRED"));
        userStatuses = getEntityManagerRepo().findWhere(str_response_code.class, searchTerms,
                false, false, true, false);
        return userStatuses;
    }

    private List<str_response_code> initTransactionStatuses() {
        ArrayList<Pair<String, ?>> searchTerms = new ArrayList<>();
        searchTerms.add(new Pair<>("name", "SUCCESS"));
        searchTerms.add(new Pair<>("name", "GENERAL_ERROR"));
        searchTerms.add(new Pair<>("name", "AUTH_AUTHENTICATION_FAILED"));
        searchTerms.add(new Pair<>("name", "INSUFFICIENT_FUNDS"));
        searchTerms.add(new Pair<>("name", "INSUFFICIENT_STOCK"));
        searchTerms.add(new Pair<>("name", "INPUT_INCOMPLETE_REQUEST"));
        searchTerms.add(new Pair<>("name", "INPUT_INVALID_AMOUNT"));
        searchTerms.add(new Pair<>("name", "INPUT_INVALID_WALLET"));
        searchTerms.add(new Pair<>("name", "INPUT_INVALID_CASHIER"));
        searchTerms.add(new Pair<>("name", "INPUT_INVALID_REQUEST"));
        searchTerms.add(new Pair<>("name", "NOT_SUPPORTED"));
        transactionStatuses = getEntityManagerRepo().findWhere(str_response_code.class, searchTerms,
                false, false, true, false);
        return transactionStatuses;
    }

    private List<str_response_code> initLogStatuses() {
        ArrayList<Pair<String, ?>> searchTerms = new ArrayList<>();
        searchTerms.add(new Pair<>("name", "SUCCESS"));
        searchTerms.add(new Pair<>("name", "GENERAL_ERROR"));
        searchTerms.add(new Pair<>("name", "AUTH_AUTHENTICATION_FAILED"));
        searchTerms.add(new Pair<>("name", "DATA_NOT_FOUND"));
        searchTerms.add(new Pair<>("name", "NOT_SUPPORTED"));
        searchTerms.add(new Pair<>("name", "AUTH_INSUFFICIENT_PRIVILEGES"));
        searchTerms.add(new Pair<>("name", "AUTH_AUTHENTICATION_FAILED"));
        searchTerms.add(new Pair<>("name", "AUTH_INCORRECT_PASSWORD"));
        searchTerms.add(new Pair<>("name", "AUTH_NON_EXISTENT"));
        searchTerms.add(new Pair<>("name", "EXISTING_DATA_FOUND"));
        searchTerms.add(new Pair<>("name", "REGISTRATION_FAILED"));
        searchTerms.add(new Pair<>("name", "PREVIOUS_USERNAME_FOUND"));
        searchTerms.add(new Pair<>("name", "PREVIOUS_MSISDN_FOUND"));
        searchTerms.add(new Pair<>("name", "PREVIOUS_EMAIL_FOUND"));
        searchTerms.add(new Pair<>("name", "PREVIOUS_REGISTRATION_FOUND"));
        logStatuses = getEntityManagerRepo().findWhere(str_response_code.class, searchTerms,
                false, false, true, false);
        return logStatuses;
    }

    public List<Boolean> getBooleanList() {
        return booleanList;
    }

    public List<str_country> getCountries() {
        return countries;
    }

    public List<str_language> getLanguages() {
        return languages;
    }

    public List<str_auth_group> getAuthGroups() {
        return authGroups;
    }

    public List<str_channel> getChannels() {
        return channels;
    }

    public List<str_event_type> getEventTypes() {
        return eventTypes;
    }

    public List<str_response_code> getUserStatuses() {
        return userStatuses;
    }

    public List<str_response_code> getTransactionStatuses() {
        return transactionStatuses;
    }

    public List<str_response_code> getLogStatuses() {
        return logStatuses;
    }
}
