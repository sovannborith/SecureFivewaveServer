package com.securefivewave.tool;

import com.securefivewave.exception.InvalidParameterBusinessException;

import lombok.experimental.UtilityClass;

import java.util.regex.Pattern;

@UtilityClass
public class WebXssSecurityUtils {

    private final static Pattern[] patterns = new Pattern[]{
            // Script fragments
            Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE),
            // src='...'
            Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
            Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
            // lonely script tags
            Pattern.compile("</script>", Pattern.CASE_INSENSITIVE),
            Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
            // eval(...)
            Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
            // expression(...)
            Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
            // javascript:...
            Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE),
            // vbscript:...
            Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE),
            // onload(...)=...
            Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
            Pattern.compile("on.*(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
            Pattern.compile("=cmd(.*?)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
            Pattern.compile("<img(.*?)>", Pattern.CASE_INSENSITIVE),
            Pattern.compile("<bAsE(.*?)>", Pattern.CASE_INSENSITIVE),
            Pattern.compile("<iFrAmE(.*?)>", Pattern.CASE_INSENSITIVE)
    };

    /**
     * Utility method to clean up xss script with filter input value based on the list of Patterns
     *
     * @param value input value
     */
    public static String cleanXSS(String value) {
        if (value != null) {
            for (Pattern scriptPattern : patterns) {
                value = value.replaceAll("\0|\n|\r", "");
                value = scriptPattern.matcher(value).replaceAll("");
            }
            value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
        }
        return value;
    }

    /**
     * validate xss script
     * @param value input value
     * @return valid string
     */
    public static String validateXSS(String value) {
        if (value != null) {
            for (Pattern scriptPattern : patterns) {
                value = value.replaceAll("\0|\n|\r", "");
                if (scriptPattern.matcher(value).find()) {
                    throw new InvalidParameterBusinessException(String.format("Invalid request %s", value));
                }
            }
            value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
        }
        return value;
    }
}
