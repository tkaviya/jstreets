package net.streets.core.contract;

import net.streets.core.contract.base.DataContract;
import net.streets.core.contract.base.EnumEntityData;
import net.streets.persistence.enumeration.StrResponseCode;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/***************************************************************************
 * Created:     11 / 06 / 2016                                             *
 * Platform:    Mint Linux x86_64                                          *
 * Author:      Tsungai Kaviya                                             *
 ***************************************************************************/

@XmlRootElement
public class StrEnumEntity extends DataContract<StrEnumEntity> implements Serializable {

    protected EnumEntityData enumEntityData;

    public StrEnumEntity() {
    }

    public StrEnumEntity(StrResponseCode symResponseCode) {
        super(symResponseCode);
    }

    public StrEnumEntity(StrResponseCode symResponseCode, EnumEntityData enumEntityData) {
        super(symResponseCode);
        this.enumEntityData = enumEntityData;
    }

    public EnumEntityData getEnumEntityData() {
        return enumEntityData;
    }

    public void setEnumEntityData(EnumEntityData enumEntityData) {
        this.enumEntityData = enumEntityData;
    }
}
