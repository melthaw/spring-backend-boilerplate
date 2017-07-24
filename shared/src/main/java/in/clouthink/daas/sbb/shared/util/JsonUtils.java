package in.clouthink.daas.sbb.shared.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

@SuppressWarnings("rawtypes")
public class JsonUtils {
    
    private static final transient Log logger = LogFactory.getLog(JsonUtils.class);
    
    public static final SimpleDateFormat shortFormatter = new SimpleDateFormat("yyyy-MM-dd");
    
    public static final SimpleDateFormat longFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    public static ObjectMapper objectMapper = new ObjectMapper();
    
    static {
        // objectMapper.registerModule(new HibernateModule());
        // objectMapper.configure(SerializationConfig.Feature.WRITE_DATE_KEYS_AS_TIMESTAMPS,
        // false);
        // objectMapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS,
        // false);
        // objectMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS,
        // false);
        // objectMapper.getSerializationConfig().withDateFormat(longFormatter);
        // objectMapper.getDeserializationConfig().withDateFormat(longFormatter);
        objectMapper.setDateFormat(longFormatter);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }
    
    public static Object deepClone(Object obj) {
        return parseJson(toJson(obj), obj.getClass());
    }
    
    public static <T> T parseJson(String content, Class<T> clazz) {
        try {
            return objectMapper.readValue(content, clazz);
        }
        catch (Exception e) {
            logger.error(e, e);
        }
        return null;
    }
    
    public static List parseList(String content) {
        try {
            return objectMapper.readValue(content, List.class);
        }
        catch (Exception e) {
            logger.error(e, e);
        }
        return null;
    }
    
    public static Map parseMap(String content) {
        try {
            return objectMapper.readValue(content, Map.class);
        }
        catch (Exception e) {
            logger.error(e, e);
        }
        return null;
    }
    
    public static String toJson(Object obj) {
        try {
            StringWriter sw = new StringWriter();
            JsonGenerator jsonGenerator = objectMapper.getJsonFactory()
                                                      .createJsonGenerator(sw);
            objectMapper.writeValue(jsonGenerator, obj);
            jsonGenerator.flush();
            return sw.getBuffer().toString();
        }
        catch (IOException e) {
            logger.error(e, e);
        }
        return null;
    }
    
}
