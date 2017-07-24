package in.clouthink.daas.sbb.shared.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class I18nUtils {
    
    public final static Map<String, String> locales = new HashMap<String, String>();
    
    public final static Map<String, String> timezones = new HashMap<String, String>();
    
    public final static Map<String, String> languages = new HashMap<String, String>();

    static HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();

    static {
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);

        Locale[] availableLocales = Locale.getAvailableLocales();
        for (Locale locale : availableLocales) {
            if ("".equals(locale.getCountry())) {
                continue;
            }
            locales.put(locale.getLanguage() + "_" + locale.getCountry(),
                        locale.getDisplayLanguage(Locale.US) + "("
                                + locale.getDisplayCountry(Locale.US)
                                + ")");
        }
        
        languages.put("en_US", "English");
        languages.put("de_DE", "German");
        languages.put("es_ES", "Spanish");
        languages.put("fr_FR", "French");
        languages.put("it_IT", "Italian");
        languages.put("ja_JP", "Japanese");
        languages.put("sv_SE", "Swedish");
        languages.put("ko_KR", "Korean");
        languages.put("zh_TW", "Chinese (Traditional)");
        languages.put("zh_CN", "Chinese (Simplified)");
        languages.put("pt_BR", "Portuguese (Brazilian)");
        languages.put("nl_NL", "Dutch");
        languages.put("da_DK", "Danish");
        languages.put("th_TH", "Thai");
        languages.put("fi_FI", "Finnish");
        languages.put("ru_RU", "Russian");
        
        timezones.put("Pacific/Kiritimati",
                      "(GMT+14:00) Line Islands Time (Pacific/Kiritimati)");
        timezones.put("Pacific/Enderbury",
                      "(GMT+13:00) Phoenix Islands Time (Pacific/Enderbury)");
        timezones.put("Pacific/Tongatapu",
                      "(GMT+13:00) Tonga Time (Pacific/Tongatapu)");
        timezones.put("Pacific/Chatham",
                      "(GMT+12:45) Chatham Standard Time (Pacific/Chatham)");
        timezones.put("Asia/Kamchatka",
                      "(GMT+12:00) Magadan Time (Asia/Kamchatka)");
        timezones.put("Pacific/Auckland",
                      "(GMT+12:00) New Zealand Standard Time (Pacific/Auckland)");
        timezones.put("Pacific/Fiji", "(GMT+12:00) Fiji Time (Pacific/Fiji)");
        timezones.put("Pacific/Norfolk",
                      "(GMT+11:30) Norfolk Islands Time (Pacific/Norfolk)");
        timezones.put("Pacific/Guadalcanal",
                      "(GMT+11:00) Solomon Islands Time (Pacific/Guadalcanal)");
        timezones.put("Australia/Lord_Howe",
                      "(GMT+10:30) Lord Howe Standard Time (Australia/Lord_Howe)");
        timezones.put("Australia/Brisbane",
                      "(GMT+10:00) Australian Eastern Standard Time (Australia/Brisbane)");
        timezones.put("Australia/Sydney",
                      "(GMT+10:00) Australian Eastern Standard Time (Australia/Sydney)");
        timezones.put("Australia/Adelaide",
                      "(GMT+09:30) Australian Central Standard Time (Australia/Adelaide)");
        timezones.put("Australia/Darwin",
                      "(GMT+09:30) Australian Central Standard Time (Australia/Darwin)");
        timezones.put("Asia/Seoul",
                      "(GMT+09:00) Korean Standard Time (Asia/Seoul)");
        timezones.put("Asia/Tokyo",
                      "(GMT+09:00) Japan Standard Time (Asia/Tokyo)");
        timezones.put("Asia/Hong_Kong",
                      "(GMT+08:00) Hong Kong Time (Asia/Hong_Kong)");
        timezones.put("Asia/Kuala_Lumpur",
                      "(GMT+08:00) Malaysia Time (Asia/Kuala_Lumpur)");
        timezones.put("Asia/Manila",
                      "(GMT+08:00) Philippine Time (Asia/Manila)");
        timezones.put("Asia/Shanghai",
                      "(GMT+08:00) China Standard Time (Asia/Shanghai)");
        timezones.put("Asia/Singapore",
                      "(GMT+08:00) Singapore Standard Time (Asia/Singapore)");
        timezones.put("Asia/Taipei",
                      "(GMT+08:00) Taipei Standard Time (Asia/Taipei)");
        timezones.put("Australia/Perth",
                      "(GMT+08:00) Australian Western Standard Time (Australia/Perth)");
        timezones.put("Asia/Bangkok",
                      "(GMT+07:00) Indochina Time (Asia/Bangkok)");
        timezones.put("Asia/Jakarta",
                      "(GMT+07:00) Western Indonesia Time (Asia/Jakarta)");
        timezones.put("Asia/Saigon", "(GMT+07:00) Indochina Time (Asia/Saigon)");
        timezones.put("Asia/Rangoon", "(GMT+06:30) Myanmar Time (Asia/Rangoon)");
        timezones.put("Asia/Dacca", "(GMT+06:00) Bangladesh Time (Asia/Dacca)");
        timezones.put("Asia/Yekaterinburg",
                      "(GMT+06:00) Yekaterinburg Time (Asia/Yekaterinburg)");
        timezones.put("Asia/Katmandu", "(GMT+05:45) Nepal Time (Asia/Katmandu)");
        timezones.put("Asia/Calcutta",
                      "(GMT+05:30) India Standard Time (Asia/Calcutta)");
        timezones.put("Asia/Colombo",
                      "(GMT+05:30) India Standard Time (Asia/Colombo)");
        timezones.put("Asia/Baku",
                      "(GMT+05:00) Azerbaijan Summer Time (Asia/Baku)");
        timezones.put("Asia/Karachi",
                      "(GMT+05:00) Pakistan Time (Asia/Karachi)");
        timezones.put("Asia/Tashkent",
                      "(GMT+05:00) Uzbekistan Time (Asia/Tashkent)");
        timezones.put("Asia/Kabul", "(GMT+04:30) Afghanistan Time (Asia/Kabul)");
        timezones.put("Asia/Tehran",
                      "(GMT+04:30) Iran Daylight Time (Asia/Tehran)");
        timezones.put("Asia/Dubai",
                      "(GMT+04:00) Gulf Standard Time (Asia/Dubai)");
        timezones.put("Asia/Tbilisi", "(GMT+04:00) Georgia Time (Asia/Tbilisi)");
        timezones.put("Asia/Yerevan", "(GMT+04:00) Armenia Time (Asia/Yerevan)");
        timezones.put("Europe/Moscow",
                      "(GMT+04:00) Moscow Standard Time (Europe/Moscow)");
        timezones.put("Africa/Nairobi",
                      "(GMT+03:00) East Africa Time (Africa/Nairobi)");
        timezones.put("Asia/Baghdad",
                      "(GMT+03:00) Arabian Standard Time (Asia/Baghdad)");
        timezones.put("Asia/Beirut",
                      "(GMT+03:00) Eastern European Summer Time (Asia/Beirut)");
        timezones.put("Asia/Kuwait",
                      "(GMT+03:00) Arabian Standard Time (Asia/Kuwait)");
        timezones.put("Asia/Riyadh",
                      "(GMT+03:00) Arabian Standard Time (Asia/Riyadh)");
        timezones.put("Europe/Athens",
                      "(GMT+03:00) Eastern European Summer Time (Europe/Athens)");
        timezones.put("Europe/Bucharest",
                      "(GMT+03:00) Eastern European Summer Time (Europe/Bucharest)");
        timezones.put("Europe/Helsinki",
                      "(GMT+03:00) Eastern European Summer Time (Europe/Helsinki)");
        timezones.put("Europe/Istanbul",
                      "(GMT+03:00) Eastern European Summer Time (Europe/Istanbul)");
        timezones.put("Europe/Minsk",
                      "(GMT+03:00) Further-Eastern European Time (Europe/Minsk)");
        timezones.put("Africa/Cairo",
                      "(GMT+02:00) Eastern European Time (Africa/Cairo)");
        timezones.put("Africa/Johannesburg",
                      "(GMT+02:00) South Africa Standard Time (Africa/Johannesburg)");
        timezones.put("Asia/Jerusalem",
                      "(GMT+02:00) Israel Standard Time (Asia/Jerusalem)");
        timezones.put("Europe/Amsterdam",
                      "(GMT+02:00) Central European Summer Time (Europe/Amsterdam)");
        timezones.put("Europe/Berlin",
                      "(GMT+02:00) Central European Summer Time (Europe/Berlin)");
        timezones.put("Europe/Brussels",
                      "(GMT+02:00) Central European Summer Time (Europe/Brussels)");
        timezones.put("Europe/Paris",
                      "(GMT+02:00) Central European Summer Time (Europe/Paris)");
        timezones.put("Europe/Prague",
                      "(GMT+02:00) Central European Summer Time (Europe/Prague)");
        timezones.put("Europe/Rome",
                      "(GMT+02:00) Central European Summer Time (Europe/Rome)");
        timezones.put("Africa/Algiers",
                      "(GMT+01:00) Central European Time (Africa/Algiers)");
        timezones.put("Africa/Casablanca",
                      "(GMT+01:00) Western European Summer Time (Africa/Casablanca)");
        timezones.put("Europe/Dublin",
                      "(GMT+01:00) Irish Summer Time (Europe/Dublin)");
        timezones.put("Europe/Lisbon",
                      "(GMT+01:00) Western European Summer Time (Europe/Lisbon)");
        timezones.put("Europe/London",
                      "(GMT+01:00) British Summer Time (Europe/London)");
        timezones.put("America/Scoresbysund",
                      "(GMT+00:00) East Greenland Summer Time (America/Scoresbysund)");
        timezones.put("Atlantic/Azores",
                      "(GMT+00:00) Azores Summer Time (Atlantic/Azores)");
        timezones.put("GMT", "(GMT+00:00) Greenwich Mean Time (GMT)");
        timezones.put("Atlantic/Cape_Verde",
                      "(GMT-01:00) Cape Verde Time (Atlantic/Cape_Verde)");
        timezones.put("Atlantic/South_Georgia",
                      "(GMT-02:00) South Georgia Time (Atlantic/South_Georgia)");
        timezones.put("America/St_Johns",
                      "(GMT-02:30) Newfoundland Daylight Time (America/St_Johns)");
        timezones.put("America/Buenos_Aires",
                      "(GMT-03:00) Argentina Time (America/Buenos_Aires)");
        timezones.put("America/Halifax",
                      "(GMT-03:00) Atlantic Daylight Time (America/Halifax)");
        timezones.put("America/Sao_Paulo",
                      "(GMT-03:00) Brasilia Time (America/Sao_Paulo)");
        timezones.put("Atlantic/Bermuda",
                      "(GMT-03:00) Atlantic Daylight Time (Atlantic/Bermuda)");
        timezones.put("America/Indianapolis",
                      "(GMT-04:00) Eastern Daylight Time (America/Indianapolis)");
        timezones.put("America/New_York",
                      "(GMT-04:00) Eastern Daylight Time (America/New_York)");
        timezones.put("America/Puerto_Rico",
                      "(GMT-04:00) Atlantic Standard Time (America/Puerto_Rico)");
        timezones.put("America/Santiago",
                      "(GMT-04:00) Chile Time (America/Santiago)");
        timezones.put("America/Caracas",
                      "(GMT-04:30) Venezuela Time (America/Caracas)");
        timezones.put("America/Bogota",
                      "(GMT-05:00) Colombia Time (America/Bogota)");
        timezones.put("America/Chicago",
                      "(GMT-05:00) Central Daylight Time (America/Chicago)");
        timezones.put("America/Lima", "(GMT-05:00) Peru Time (America/Lima)");
        timezones.put("America/Mexico_City",
                      "(GMT-05:00) Central Daylight Time (America/Mexico_City)");
        timezones.put("America/Panama",
                      "(GMT-05:00) Eastern Standard Time (America/Panama)");
        timezones.put("America/Denver",
                      "(GMT-06:00) Mountain Daylight Time (America/Denver)");
        timezones.put("America/El_Salvador",
                      "(GMT-06:00) Central Standard Time (America/El_Salvador)");
        timezones.put("Mexico/BajaSur",
                      "(GMT-06:00) Mountain Daylight Time (Mexico/BajaSur)");
        timezones.put("America/Los_Angeles",
                      "(GMT-07:00) Pacific Daylight Time (America/Los_Angeles)");
        timezones.put("America/Phoenix",
                      "(GMT-07:00) Mountain Standard Time (America/Phoenix)");
        timezones.put("America/Tijuana",
                      "(GMT-07:00) Pacific Daylight Time (America/Tijuana)");
        timezones.put("America/Anchorage",
                      "(GMT-08:00) Alaska Daylight Time (America/Anchorage)");
        timezones.put("Pacific/Pitcairn",
                      "(GMT-08:00) Pitcairn Time (Pacific/Pitcairn)");
        timezones.put("America/Atka",
                      "(GMT-09:00) Hawaii-Aleutian Standard Time (America/Atka)");
        timezones.put("Pacific/Gambier",
                      "(GMT-09:00) Gambier Time (Pacific/Gambier)");
        timezones.put("Pacific/Marquesas",
                      "(GMT-09:30) Marquesas Time (Pacific/Marquesas)");
        timezones.put("Pacific/Honolulu",
                      "(GMT-10:00) Hawaii-Aleutian Standard Time (Pacific/Honolulu)");
        timezones.put("Pacific/Niue", "(GMT-11:00) Niue Time (Pacific/Niue)");
        timezones.put("Pacific/Pago_Pago",
                      "(GMT-11:00) Samoa Standard Time (Pacific/Pago_Pago)");
    }
    
    public static String getLanguageByCode(String code) {
        return languages.get(code);
    }
    
    public static String getTimezoneByCode(String code) {
        return timezones.get(code);
    }
    
    public static String getLocaleByCode(String code) {
        return locales.get(code);
    }


    public static String chineseToPinyin(String str) {
        if (str == null || StringUtils.isEmpty(str)) {
            return str;
        }

        StringBuilder sb = new StringBuilder();
        for (char c : str.toCharArray()) {
            try {
                String[] result = PinyinHelper.toHanyuPinyinStringArray(c,
                                                                        format);
                if (result != null) {
                    sb.append(result[0]);
                }
            }
            catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                badHanyuPinyinOutputFormatCombination.printStackTrace();
            }

        }
        return sb.toString();
    }
}
