package net.streets.core.contract.base;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/***************************************************************************
 * *
 * Created:     21 / 10 / 2016                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 * *
 ***************************************************************************/

@XmlRootElement
public class EnumEntityData extends EntityData implements Serializable {

    protected String name;

    protected Boolean enabled;

    public EnumEntityData() {
    }

    public EnumEntityData(Long id, String name, Boolean enabled) {
        super(id);
        this.name = name;
        this.enabled = enabled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
