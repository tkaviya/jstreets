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
public class StrSystemUserList extends DataContract<StrSystemUserList> implements Serializable {

    protected ArrayList<StrSystemUser> systemUserData;

    public StrSystemUserList() {
    }

    public StrSystemUserList(StrResponseCode strResponseCode) {
        super(strResponseCode);
    }

    public StrSystemUserList(StrResponseCode strResponseCode, StrSystemUser systemUser) {
        super(strResponseCode);
        this.systemUserData = new ArrayList<>();
        this.systemUserData.add(systemUser);
    }

    public StrSystemUserList(StrResponseCode strResponseCode, ArrayList<StrSystemUser> systemUserData) {
        super(strResponseCode);
        this.systemUserData = systemUserData;
    }

    public ArrayList<StrSystemUser> getSystemUserData() {
        return systemUserData;
    }

    public void setSystemUserData(ArrayList<StrSystemUser> StrSystemUser) {
        this.systemUserData = StrSystemUser;
    }
}
