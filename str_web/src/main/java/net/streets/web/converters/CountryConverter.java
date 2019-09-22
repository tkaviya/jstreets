package net.streets.web.converters;

import net.streets.persistence.entity.enumeration.str_country;

import javax.faces.bean.ManagedBean;
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
@ManagedBean
@FacesConverter(value = "countryConverter")
public class CountryConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (isNumeric(value)) {
            return getEntityManagerRepo().findById(str_country.class, parseLong(value));
        } else {
            return findByName(str_country.class, value);
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value instanceof Long) {
            return getEntityManagerRepo().findById(str_country.class, parseLong(value.toString())).getName();
        } else if (value instanceof str_country) {
            return ((str_country) value).getName();
        } else return null;
    }
}