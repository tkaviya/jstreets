package net.streets.core.contract;

import net.streets.common.interfaces.PrintableStringClass;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.math.BigDecimal;

/***************************************************************************
 * *
 * Created:     22 / 10 / 2016                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 * *
 ***************************************************************************/
@XmlRootElement
public class StrWallet implements Serializable, PrintableStringClass {

    private Long walletId;
    private BigDecimal currentBalance = new BigDecimal(0.0);
    private StrSystemUser systemUser;

    public StrWallet() {
    }

    public StrWallet(Long walletId, BigDecimal currentBalance, StrSystemUser systemUser) {
        this.walletId = walletId;
        this.currentBalance = currentBalance;
        this.systemUser = systemUser;
    }

    public StrWallet(Long walletId, BigDecimal currentBalance) {
        this.walletId = walletId;
        this.currentBalance = currentBalance;
    }

    public Long getWalletId() {
        return walletId;
    }

    public void setWalletId(Long walletId) {
        this.walletId = walletId;
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(BigDecimal currentBalance) {
        this.currentBalance = currentBalance;
    }

    public StrSystemUser getSystemUser() {
        return systemUser;
    }

    public void setSystemUser(StrSystemUser systemUser) {
        this.systemUser = systemUser;
    }

    @Override
    public String toString() {
        return this.toPrintableString();
    }
}
