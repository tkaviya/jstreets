package net.streets.core.contract;

import net.streets.core.contract.base.DataContract;
import net.streets.persistence.enumeration.StrResponseCode;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;

/***************************************************************************
 * Created:     22 / 01 / 2017                                             *
 * Platform:    Windows 8.1                                                *
 * Author:      Tsungai Kaviya                                             *
 * Copyright:   T3ra Tech                                                  *
 * Website:     http://www.t3ratech.com                                    *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 ***************************************************************************/

@XmlRootElement
public class StrCurrencyList extends DataContract<StrCurrencyList> implements Serializable {

    protected ArrayList<StrCurrency> currencyData;

    public StrCurrencyList() {
    }

    public StrCurrencyList(StrResponseCode symResponseCode) {
        super(symResponseCode);
    }

    public StrCurrencyList(StrResponseCode symResponseCode, ArrayList<StrCurrency> currencyData) {
        super(symResponseCode);
        this.currencyData = currencyData;
    }

    public StrCurrencyList(StrResponseCode symResponseCode, StrCurrency currency) {
        super(symResponseCode);
        this.currencyData = new ArrayList<>();
        this.currencyData.add(currency);
    }

    public ArrayList<StrCurrency> getCurrencyData() {
        return currencyData;
    }

    public void setCurrencyData(ArrayList<StrCurrency> currencyData) {
        this.currencyData = currencyData;
    }

}
