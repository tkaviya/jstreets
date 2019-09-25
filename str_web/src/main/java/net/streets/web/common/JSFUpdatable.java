package net.streets.web.common;

import net.streets.persistence.entity.complex_type.str_auth_user;
import net.streets.persistence.entity.complex_type.str_user;
import net.streets.persistence.entity.enumeration.str_event_type;
import net.streets.persistence.entity.enumeration.str_response_code;
import net.streets.persistence.entity.super_class.str_entity;
import net.streets.web.session.SessionBean;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import static java.lang.String.format;
import static javax.faces.application.FacesMessage.SEVERITY_FATAL;
import static javax.faces.application.FacesMessage.SEVERITY_INFO;
import static javax.faces.context.FacesContext.getCurrentInstance;
import static net.streets.common.utilities.CommonUtilities.getRealThrowable;
import static net.streets.persistence.enumeration.StrEventType.USER_UPDATE;
import static net.streets.persistence.enumeration.StrResponseCode.GENERAL_ERROR;
import static net.streets.persistence.enumeration.StrResponseCode.SUCCESS;
import static net.streets.persistence.helper.StrEnumHelper.fromEnum;

/***************************************************************************
 *                                                                         *
 * Created:     13 / 02 / 2017                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/

public abstract class JSFUpdatable extends JSFExportable implements JSFLoggable, Serializable {

    private static final Logger logger = Logger.getLogger(JSFUpdatable.class.getSimpleName());
    protected final SessionBean sessionBean;
    protected List<String> updatableColumns = new ArrayList<>();
    protected List<String> notUpdatableColumns = new ArrayList<>();
    private str_response_code SUCCESS_RESPONSE = fromEnum(SUCCESS);
    private str_response_code FAILURE_RESPONSE = fromEnum(GENERAL_ERROR);

    @Autowired
    public JSFUpdatable(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    //populate updatable and unUpdatable column lists
    @PostConstruct
    public abstract void init();

    private str_event_type getEventType(str_entity updateTable) {
        if (updateTable instanceof str_user || updateTable instanceof str_auth_user) { return fromEnum(USER_UPDATE); }
        return null;
    }

    public void onRowEdit(RowEditEvent event) {
    }

    public void onRowCancel(RowEditEvent event) {
    }

    public void onCellEdit(CellEditEvent event) {

        Date requestTime = new Date();

        //TODO check permissions
        Object oldValue = event.getOldValue(), newValue = event.getNewValue();
        String columnName = event.getColumn().getHeaderText();

        logger.info(format("Got request to update \"%s\" column from \"%s\" to \"%s\"", columnName, oldValue, newValue));

        if (newValue != null && !newValue.equals(oldValue)) {

            if (notUpdatableColumns.contains(columnName)) {
                getCurrentInstance().addMessage(null, new FacesMessage(SEVERITY_FATAL,
                        "Manual update not permitted",
                        format("The value of the column %s cannot be manually modified", columnName)));
                logger.info("The value of the column %s cannot be manually modified");
                return;
            } else if (updatableColumns.contains(columnName)) {

                str_response_code responseCode;
                try {
                    ((str_entity) ((DataTable) event.getComponent()).getRowData()).save();
                    logger.info(format("Data updated successfully. Old Value: %s, New Value: %s", oldValue, newValue));
                    getCurrentInstance().addMessage(null, new FacesMessage(SEVERITY_INFO,
                            "Data updated successfully",
                            "Successfully updated Old Value: '" + oldValue + "' to New Value: '" + newValue + "'"));
                    responseCode = SUCCESS_RESPONSE;
                } catch (Exception ex) {
                    logger.info(format("Failed to update %s from %s to %s! %s", columnName, oldValue, newValue, ex.getMessage()));
                    getCurrentInstance().addMessage(null, new FacesMessage(SEVERITY_FATAL,
                            "Failed to update %s",
                            format("Failed to update %s from %s to %s! %s", columnName, oldValue, newValue, ex.getMessage())));
                    responseCode = FAILURE_RESPONSE;
                    responseCode.setResponse_message(getRealThrowable(ex).getMessage());
//                    DataTable eventTable = (DataTable) event.getComponent();
                }

                str_event_type eventType = getEventType((str_entity) ((DataTable) event.getComponent()).getRowData());

                String incomingRequest = format("UPDATE \"%s\" from \"%s\" to \"%s\"", columnName, oldValue, newValue);
                log(eventType, sessionBean.getStrAuthUser(), responseCode, requestTime,
                        new Date(), incomingRequest, responseCode.getResponse_message());

            }
        }
    }

}
