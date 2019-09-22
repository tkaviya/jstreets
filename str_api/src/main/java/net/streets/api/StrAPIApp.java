package net.streets.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

import javax.ws.rs.ext.ContextResolver;

/***************************************************************************
 * *
 * Created:     16 / 01 / 2017                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 * *
 ***************************************************************************/

@Configuration
public class StrAPIApp extends ResourceConfig {

    public class ObjectMapperContextResolver implements ContextResolver<ObjectMapper> {

        private final ObjectMapper mapper;

        ObjectMapperContextResolver() {
            mapper = new ObjectMapper();
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        }

        @Override
        public ObjectMapper getContext(Class<?> type) {
            return mapper;
        }

    }

    public StrAPIApp() {
        register(new ObjectMapperContextResolver());
        register(MultiPartFeature.class);
//        register(StrRestExceptionHandler.class);
//        register(MobileXMLRestController.class);
//        register(MobileJSONRestController.class);
//        register(StrXMLRestController.class);
//        register(StrJSONRestController.class);
    }
}
