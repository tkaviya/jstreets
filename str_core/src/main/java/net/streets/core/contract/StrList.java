package net.streets.core.contract;

import net.streets.core.contract.base.DataContract;
import net.streets.persistence.enumeration.StrResponseCode;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;

/***************************************************************************
 * Created:     11 / 06 / 2016                                             *
 * Platform:    Mint Linux x86_64                                          *
 * Author:      Tsungai Kaviya                                             *
 ***************************************************************************/

@XmlRootElement
public class StrList extends DataContract<StrList> implements Serializable {

    protected ArrayList responseData;

    public StrList() {
    }

    public StrList(StrResponseCode symResponseCode) {
        this.strResponse = new StrResponse(symResponseCode);
    }

    public StrList(StrResponseCode symResponseCode, ArrayList responseData) {
        this.strResponse = new StrResponse(symResponseCode);
        this.responseData = responseData;
    }

    public ArrayList getResponseData() {
        return responseData;
    }

    public void setResponseData(ArrayList responseData) {
        this.responseData = responseData;
    }
}
