package net.streets.web.converters;

import net.streets.persistence.entity.enumeration.str_auth_group;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import static java.lang.Long.parseLong;
import static net.streets.persistence.dao.EnumEntityRepoManager.findByName;
import static net.streets.persistence.helper.DaoManager.getEntityManagerRepo;
import static net.streets.utilities.StrValidator.isNumeric;

/***************************************************************************
 *                                                                         *
 * Created:     01 / 03 / 2017                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/
@ApplicationScoped
@FacesConverter(value = "agConverter")
public class AuthGroupConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (isNumeric(value)) {
            return getEntityManagerRepo().findById(str_auth_group.class, parseLong(value));
        } else {
            return findByName(str_auth_group.class, value);
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value instanceof Long) {
            return getEntityManagerRepo().findById(str_auth_group.class, parseLong(value.toString())).getName();
        } else if (value instanceof str_auth_group) {
            return ((str_auth_group) value).getName();
        } else return null;
    }
}