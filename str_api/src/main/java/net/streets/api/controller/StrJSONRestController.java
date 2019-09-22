package net.streets.api.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import net.streets.core.service.StreetsRequestProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/***************************************************************************
 * *
 * Created:     19 / 10 / 2016                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 * *
 ***************************************************************************/

@Controller
@Path("/")
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
@Produces({MediaType.APPLICATION_JSON})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StrJSONRestController extends StrXMLRestController {

    @Autowired
    public StrJSONRestController(StreetsRequestProcessor streetsRequestProcessor) {
        super(streetsRequestProcessor);
    }

    @Override
    @GET
    @Path("/responseCode/{responseCodeId}")
    public Response getResponseCode(@PathParam("responseCodeId") Long responseCodeId) {
        return super.getResponseCode(responseCodeId);
    }

    @Override
    @GET
    @Path("/responseCode")
    public Response getResponseCodes() {
        return super.getResponseCodes();
    }

    @Override
    @GET
    @Path("/language/{languageId}")
    public Response getLanguage(@PathParam("languageId") Long languageId) {
        return super.getLanguage(languageId);
    }

    @Override
    @GET
    @Path("/channel/{channelId}")
    public Response getChannel(@PathParam("channelId") Long channelId) {
        return super.getChannel(channelId);
    }

    @Override
    @GET
    @Path("/country/{countryId}")
    public Response getCountry(@PathParam("countryId") Long countryId) {
        return super.getCountry(countryId);
    }

    @Override
    @GET
    @Path("/currency/{currencyId}")
    public Response getCurrency(@PathParam("currencyId") Long currencyId) {
        return super.getCurrency(currencyId);
    }

    @Override
    @GET
    @Path("/currency")
    public Response getCurrencies() {
        return super.getCurrencies();
    }
}
