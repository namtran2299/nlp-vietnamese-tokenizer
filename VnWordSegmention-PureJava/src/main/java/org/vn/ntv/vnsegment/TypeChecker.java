/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.vn.ntv.vnsegment;

import java.util.regex.Pattern;

/**
 *
 * @author namtv19
 */
public class TypeChecker {
    
    private static final String strFTPPattern = "ftp:/[^ ]+";
	private static final String strHTTPPattern = "http://[^ ]+|https://[^ ]+";
	private static final String strEmailPattern = "[_A-Za-z0-9\\-\\+]+(\\.[_A-Za-z0-9\\-]+)*@[A-Za-z0-9\\-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})";
	private static final String ipv6Pattern = "([0-9a-f]{1,4}:){7}([0-9a-f]){1,4}";
	private static final String ipv4Pattern = "(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])";
	private static final String strLongDatePattern = "\\d+[/\\-:]\\d+[/\\-:]\\d+";
	private static final String strViShortDatePattern = "[0-9]{1,2}[h|g][0-9]{2}";
	private static final String strShortDatePattern = "\\d+[/\\-:]\\d+";
	private static final String strNumberPattern = "[+\\-]?\\d+([,\\.]\\d+)*";
	private static final String strPercentagePattern = strNumberPattern + "%";
	private static final String strCurrencyPattern = "[$£€]" + strNumberPattern;
	private static final String strViCurrencyPattern = strNumberPattern + "[¢]";

	private static final String strFacebookTagsPattern = "#[^ \r\n\t\f]";
	private static final String strBracketTagsPattern = "\\[.*\\]|\\{.*\\}";
	private static final String strHtmlTagsPattern = "<[^>]+>";
    
    private static final String REGEX_UNIT = "\\b[\\(\\[\\{]?([-+]?\\d+([,.]\\d+)*?\\.?\\d+?|[-+]?[0-9]+)(giờ|phút|giây|s|ph|h|k|gram|gam|kg|m|mm|km|cm|dm|m2|vnd|vnđ|đ|đồng|usd|Mbps|MB|GB|KB|d/p|d/tin|d/lan)[\\}\\)\\]]?\\b";
    
    
    private static final String WORD_BOUNDARY_BEFORE = "\\b";
	private static final String WORD_BOUNDARY_AFTER = "\\b";
	private static final String REGEX_HTTP_AND_NUMBER = strFTPPattern
			+ "|" + strHTTPPattern
			+ "|" + strEmailPattern
			+ "|" + WORD_BOUNDARY_BEFORE + strLongDatePattern + WORD_BOUNDARY_AFTER
			+ "|" + WORD_BOUNDARY_BEFORE + ipv6Pattern + WORD_BOUNDARY_AFTER
			+ "|" + WORD_BOUNDARY_BEFORE + ipv4Pattern + WORD_BOUNDARY_AFTER
			+ "|" + WORD_BOUNDARY_BEFORE + strViShortDatePattern + WORD_BOUNDARY_AFTER
			+ "|" + WORD_BOUNDARY_BEFORE + strShortDatePattern + WORD_BOUNDARY_AFTER
			+ "|" + WORD_BOUNDARY_BEFORE + strPercentagePattern + WORD_BOUNDARY_AFTER
			+ "|" + WORD_BOUNDARY_BEFORE + strCurrencyPattern + WORD_BOUNDARY_AFTER
			+ "|" + WORD_BOUNDARY_BEFORE + strViCurrencyPattern + WORD_BOUNDARY_AFTER
			+ "|" + WORD_BOUNDARY_BEFORE + strNumberPattern + WORD_BOUNDARY_AFTER
			+ "";

	private static final String REGEX_TAGS = strBracketTagsPattern
			+ "|" + strHtmlTagsPattern
			+ "";
    

    private static final Pattern PAT_NOMALIZE = Pattern.compile(REGEX_HTTP_AND_NUMBER, Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
	private static final Pattern PAT_NORMALIZE_TAG = Pattern.compile(REGEX_TAGS, Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
	private static final Pattern PAT_UNIT = Pattern.compile(REGEX_UNIT, Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
    
    public static Token.Type getType(String t) {
        if (Configure.SYMBOLS.contains(t)) {
            return Token.Type.PUNCT;
        }
        if (isNumber(t)) {
            return Token.Type.NUMBER;
        }
        if (t.trim().isEmpty()) {
            return Token.Type.SPACE;
        }
        return Token.Type.WORD;
    }

    public static Token.Type getTypeExt(String t) {
        return Token.Type.WORD;
    }

    public static boolean isNumber(String t) {
        char[] chars = t.toCharArray();
        for (char ch : chars) {
            if (!Character.isDigit(ch) && ch != '.' && ch != '-' && ch != '+') {
                return false;
            }
        }
        return true;
    }
}
