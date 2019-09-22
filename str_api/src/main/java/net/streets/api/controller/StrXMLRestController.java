package net.streets.api.controller;

/***************************************************************************
 * *
 * Created:     19 / 10 / 2016                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 * *
 ***************************************************************************/

import net.streets.api.service.StreetsRestService;
import net.streets.core.service.StreetsRequestProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.logging.Logger;

import static javax.ws.rs.core.Response.status;

@Controller
@Path("/xml/")
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_FORM_URLENCODED, MediaType.MULTIPART_FORM_DATA})
@Produces({MediaType.APPLICATION_XML})
public class StrXMLRestController implements StreetsRestService {

    private final static Logger logger = Logger.getLogger(StrXMLRestController.class.getSimpleName());
    private final StreetsRequestProcessor streetsRequestProcessor;

    @Autowired
    public StrXMLRestController(StreetsRequestProcessor streetsRequestProcessor) {
        this.streetsRequestProcessor = streetsRequestProcessor;
    }

    @GET
    @Path("/responseCode/{responseCodeId}")
    public Response getResponseCode(@PathParam("responseCodeId") Long responseCodeId) {
        logger.info("Got request to get language with id " + responseCodeId);
        return status(200).entity(streetsRequestProcessor.getResponseCode(responseCodeId))
                .header("Access-Control-Allow-Origin", "*").build();
    }

    @GET
    @Path("/responseCode")
    public Response getResponseCodes() {
        logger.info("Got request to get response codes");
        return status(200).entity(streetsRequestProcessor.getResponseCodes())
                .header("Access-Control-Allow-Origin", "*").build();
    }

    @GET
    @Path("/language/{languageId}")
    public Response getLanguage(@PathParam("languageId") Long languageId) {
        logger.info("Got request to get language with id " + languageId);
        return status(200).entity(streetsRequestProcessor.getLanguage(languageId))
                .header("Access-Control-Allow-Origin", "*").build();
    }

    @GET
    @Path("/channel/{channelId}")
    public Response getChannel(@PathParam("channelId") Long channelId) {
        logger.info("Got request to get channel with id " + channelId);
        return status(200).entity(streetsRequestProcessor.getChannel(channelId))
                .header("Access-Control-Allow-Origin", "*").build();
    }

    @GET
    @Path("/country/{countryId}")
    public Response getCountry(@PathParam("countryId") Long countryId) {
        logger.info("Got request to get country with id " + countryId);
        return status(200).entity(streetsRequestProcessor.getCountry(countryId))
                .header("Access-Control-Allow-Origin", "*").build();
    }
    @Override
    @GET
    @Path("/currency/{currencyId}")
    public Response getCurrency(@PathParam("currencyId") Long currencyId) {
        logger.info("Got request to get currency with id " + currencyId);
        return Response.status(200).entity(streetsRequestProcessor.getCurrency(currencyId))
                .header("Access-Control-Allow-Origin", "*").build();
    }

    @Override
    @GET
    @Path("/currency")
    public Response getCurrencies() {
        logger.info("Got request to get currencies");
        return Response.status(200).entity(streetsRequestProcessor.getCurrencies())
                .header("Access-Control-Allow-Origin", "*").build();
    }
}
