package in.clouthink.daas.sbb.shared.util;

import cz.mallat.uasparser.UASparser;
import cz.mallat.uasparser.UserAgentInfo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;

public class UserAgentUtils {
    
    private static final Log logger = LogFactory.getLog(UserAgentUtils.class);
    
    private static UASparser parser;

    static {
        try {
            parser = new UASparser(UASparser.class.getClassLoader()
                                                  .getResourceAsStream("user_agent_strings.txt"));
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static UserAgentInfo parse(String userAgentString) {
        try {
            return parser.parse(userAgentString);
        }
        catch (IOException e) {
            return null;
        }
    }

    public static String getSummary(String userAgentString) {
        try {
            UserAgentInfo uai = parser.parse(userAgentString);
            return uai.getOsName() + " - " + uai.getUaName();
        }
        catch (IOException e) {
            logger.error(e, e);
            return "Unknown";
        }
    }

    public static String getOsFamily(String userAgentString) {
        try {
            UserAgentInfo uai = parser.parse(userAgentString);
            return uai.getOsName();
        }
        catch (IOException e) {
            logger.error(e, e);
            return "Unknown";
        }
    }

    public static String getBrowserFamily(String userAgentString) {
        try {
            UserAgentInfo uai = parser.parse(userAgentString);
            return uai.getUaName();
        }
        catch (IOException e) {
            logger.error(e, e);
            return "Unknown";
        }
    }

    
}
