package net.streets.core.contract;

import net.streets.persistence.entity.enumeration.str_response_code;
import net.streets.persistence.enumeration.StrResponseCode;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/***************************************************************************
 * Created:     11 / 06 / 2016                                             *
 * Platform:    Mint Linux x86_64                                          *
 * Author:      Tsungai Kaviya                                             *
 ***************************************************************************/

@XmlRootElement
public class StrResponse implements Serializable {
    protected StrResponseCode response;
    protected Integer response_code;
    protected String response_message;

    public StrResponse() {
    }

    public StrResponse(StrResponseCode symResponseCode) {
        this.setResponse(symResponseCode);
    }

    public StrResponse(str_response_code symResponseCode) {
        this.setResponse(StrResponseCode.valueOf(symResponseCode.getId()));
    }

    public StrResponseCode getResponse() {
        return response;
    }

    public void setResponse(StrResponseCode response) {
        this.response = response;
        this.response_code = response.getCode();
        this.response_message = response.getMessage();
    }

    public Integer getResponse_code() {
        return response_code;
    }

    public StrResponse setResponse_code(Integer response_code) {
        this.response_code = response_code;
        return this;
    }

    public String getResponse_message() {
        return response_message;
    }

    public StrResponse setResponse_message(String response_message) {
        this.response_message = response_message;
        return this;
    }
}
