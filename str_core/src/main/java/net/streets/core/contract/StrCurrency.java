package net.streets.core.contract;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/***************************************************************************
 * Created:     30 / 01 / 2018                                             *
 * Platform:    Windows 8.1                                                *
 * Author:      Tsungai Kaviya                                             *
 * Copyright:   T3ra Tech                                                  *
 * Website:     http://www.t3ratech.com                                    *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 ***************************************************************************/

@XmlRootElement
public class StrCurrency implements Serializable {

    private Long currencyId;
    private String currencyName;
    private String iso4217Code;
    private String iso4217Num;

    public StrCurrency() {
    }

    public StrCurrency(Long currencyId, String currencyName, String iso4217Code, String iso4217Num) {
        this.currencyId = currencyId;
        this.currencyName = currencyName;
        this.iso4217Code = iso4217Code;
        this.iso4217Num = iso4217Num;
    }

    public Long getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Long currencyId) {
        this.currencyId = currencyId;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getIso4217Code() {
        return iso4217Code;
    }

    public void setIso4217Code(String iso4217Code) {
        this.iso4217Code = iso4217Code;
    }

    public String getIso4217Num() {
        return iso4217Num;
    }

    public void setIso4217Num(String iso4217Num) {
        this.iso4217Num = iso4217Num;
    }
}
