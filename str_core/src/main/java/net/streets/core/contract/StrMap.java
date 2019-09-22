package net.streets.core.contract;

import net.streets.core.contract.base.DataContract;
import net.streets.persistence.enumeration.StrResponseCode;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Map;

/***************************************************************************
 * Created:     16 / 12 / 2016                                             *
 * Platform:    Windows 8.1                                                *
 * Author:      Tsungai Kaviya                                             *
 * Copyright:   T3ra Tech                                                  *
 * Website:     http://www.t3ratech.com                                    *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 ***************************************************************************/

@XmlRootElement
public class StrMap extends DataContract<StrMap> implements Serializable {

    protected Map responseData;

    public StrMap() {
    }

    public StrMap(StrResponseCode symResponseCode) {
        this.strResponse = new StrResponse(symResponseCode);
    }

    public StrMap(StrResponseCode symResponseCode, Map responseData) {
        this.strResponse = new StrResponse(symResponseCode);
        this.responseData = responseData;
    }

    public Map getResponseData() {
        return responseData;
    }

    public void setResponseData(Map responseData) {
        this.responseData = responseData;
    }
}
