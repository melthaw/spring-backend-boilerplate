package in.clouthink.daas.sbb.shared.util;

import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class WebUtils {

    public static String escapeHTML(String content) {
        return content.replace("<", "&lt;").replace(">", "&gt;");
    }
    
    public static String unescapeHTML(String content) {
        if (StringUtils.isEmpty(content)) {
            return content;
        }
        return content.replace("&lt;", "<").replace("&gt;", ">");
    }

    public static Map<String, String> parseQueryString(String query) {
        Map<String, String> result = new LinkedHashMap<String, String>();
        try {
            String[] pairs = query.split("&");
            for (String pair : pairs) {
                int idx = pair.indexOf("=");
                result.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"),
                           URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
            }
        }
        catch (Exception e) {
        }
        return result;
    }
    
    public static Map<String, String> getRequestParameters(HttpServletRequest request) {
        Map<String, String> parameters = new HashMap<String, String>();
        Map<String, String[]> map = request.getParameterMap();
        for (String key : map.keySet()) {
            String[] values = map.get(key);
            if (values != null && values.length > 0) {
                parameters.put(key, values[0]);
            }
        }
        return parameters;
    }
    
    public static String getUri(String fullUrl) {
        if (StringUtils.isEmpty(fullUrl)) {
            return "";
        }
        int startIndex = fullUrl.indexOf("://");
        if (startIndex < 0) {
            return "";
        }
        
        startIndex = fullUrl.indexOf("/", startIndex + 3);
        if (startIndex < 0) {
            return "";
        }
        
        return fullUrl.substring(startIndex);
    }

}
