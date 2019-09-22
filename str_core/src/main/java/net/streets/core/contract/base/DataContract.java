package net.streets.core.contract.base;

import net.streets.core.contract.StrResponse;
import net.streets.persistence.enumeration.StrResponseCode;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/***************************************************************************
 * Created:     11 / 06 / 2016                                             *
 * Platform:    Mint Linux x86_64                                          *
 * Author:      Tsungai Kaviya                                             *
 ***************************************************************************/

@XmlRootElement
public abstract class DataContract<T extends DataContract> implements Serializable {

    protected StrResponse strResponse;

    public DataContract() {
    }

    public DataContract(StrResponseCode symResponseCode) {
        this.strResponse = new StrResponse(symResponseCode);
    }

    public StrResponse getStrResponse() {
        return strResponse;
    }

    @SuppressWarnings("unchecked")
    public T setStrResponse(StrResponse symResponse) {
        this.strResponse = symResponse;
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T setResponse(String response) {
        this.strResponse.setResponse_message(response);
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T setStrResponseCode(StrResponseCode symResponseCode) {
        this.setStrResponse(new StrResponse(symResponseCode));
        return (T) this;
    }
}
