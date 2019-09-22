package net.streets.core.contract;

import net.streets.common.interfaces.PrintableStringClass;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/***************************************************************************
 * *
 * Created:     22 / 10 / 2016                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 * *
 ***************************************************************************/

@XmlRootElement
public class StrWalletTransaction implements Serializable, PrintableStringClass {
    private Long walletTransactionId;
    private Long walletId;
    private String eventType;
    private String transactionAmount;
    private String transactionDescription;
    private String transactionTime;

    public StrWalletTransaction() {}

    public StrWalletTransaction(Long walletTransactionId, Long walletId, String eventType, String transactionAmount, String transactionDescription, String transactionTime) {
        this.walletTransactionId = walletTransactionId;
        this.walletId = walletId;
        this.eventType = eventType;
        this.transactionAmount = transactionAmount;
        this.transactionDescription = transactionDescription;
        this.transactionTime = transactionTime;
    }

    public Long getWalletTransactionId() {
        return walletTransactionId;
    }

    public void setWalletTransactionId(Long walletTransactionId) {
        this.walletTransactionId = walletTransactionId;
    }

    public Long getWalletId() { return walletId; }

    public void setWalletId(Long walletId) { this.walletId = walletId; }

    public String getEventType() { return eventType; }

    public void setEventType(String eventType) { this.eventType = eventType; }

    public String getTransactionAmount() { return transactionAmount; }

    public void setTransactionAmount(String transactionAmount) { this.transactionAmount = transactionAmount; }

    public String getTransactionDescription() { return transactionDescription; }

    public void setTransactionDescription(String transactionDescription) {
        this.transactionDescription = transactionDescription;
    }

    public String getTransactionTime() { return transactionTime; }

    public void setTransactionTime(String transactionTime) { this.transactionTime = transactionTime; }

    @Override
    public String toString() {
        return this.toPrintableString();
    }
}
