/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.vn.ntv.vnsegment;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author namtv19
 */
public class Configure {

    public static final int MAX_WORD_LENGTH = 3;

    public static final char SPACE = ' ';
    public static final char UNDER = '_';
    
    public static final int SPACE_LABEL = 1;
    public static final int UNDER_LABEL = 2;
    
    public static final int CLOCKS_PER_SEC = 1000;
    
    public static final int LEARN = 0;
    public static final int PREDICT = 1;
    public static final String SYMBOLS = "@`#$%&~|[]<>'(){}*+-=;,?.!:\"/";
    public static final Set<Character> SYMBOLS_SET = new HashSet<>();
    static {
        for (char ch : SYMBOLS.toCharArray()) {
            SYMBOLS_SET.add(ch);
        }
    }
    
     public static String ensureEndingSlash(String str) {
        if (!str.endsWith("/")) {
            return str + "/";
        } else {
            return str;
        }
    }
     
     /**
     * a double is 0 ?
     *
     * @param x
     * @return
     */
    public static boolean isZero(double x) {
        return x < 1e-10 && x > -1e-10;
    }
}
