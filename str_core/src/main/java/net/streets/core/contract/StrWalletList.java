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
public class StrWalletList extends DataContract<StrWalletList> implements Serializable {

    protected ArrayList<StrWallet> walletData;

    public StrWalletList() {
    }

    public StrWalletList(StrResponseCode strResponseCode) {
        this.strResponse = new StrResponse(strResponseCode);
    }

    public StrWalletList(StrResponseCode strResponseCode, StrWallet wallet) {
        this.strResponse = new StrResponse(strResponseCode);
        this.walletData = new ArrayList<>();
        this.walletData.add(wallet);
    }

    public StrWalletList(StrResponseCode strResponseCode, ArrayList<StrWallet> walletData) {
        this.strResponse = new StrResponse(strResponseCode);
        this.walletData = walletData;
    }

    public ArrayList<StrWallet> getWalletData() {
        return walletData;
    }

    public void setWalletData(ArrayList<StrWallet> walletData) {
        this.walletData = walletData;
    }

}
