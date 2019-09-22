package net.streets.api.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/***************************************************************************
 * *
 * Created:     21 / 10 / 2016                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 * *
 ***************************************************************************/

public interface StreetsRestService {

    @GET
    @Path("hello")
    default Response hello() {
        return Response.status(200).entity("Hello from server!").build();
    }

    Response getResponseCode(Long responseCodeId);

    Response getResponseCodes();

    Response getLanguage(Long languageId);

    Response getChannel(Long channelId);

    Response getCountry(Long countryId);

    Response getCurrency(Long currencyId);

    Response getCurrencies();
}
