/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.vn.ntv.vnsegment;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author namtv19
 */
public class Feats {

    final static String[] sourceNorm = new String[]{"…", "“", "”"};
    final static String[] replaceNorm = new String[]{"...", "\"", "\""};
    List<Feat> feats = new ArrayList<>();
    SylMap _syl = new SylMap();

    public int size() {
        return feats.size();
    }

    public List<Feat> get() {
        return feats;
    }

    public void add(Feat f) {
        feats.add(f);
    }

    /**
     *
     * @param syl
     * @return
     */
    public String detectTypeSyl(String syl) {
        boolean uppercase = false;
        boolean lowercase = false;
        boolean number = false;
        boolean symbols = false;

        char[] word = syl.toCharArray();
        for (int i = 0; i < word.length; ++i) {
            if (Configure.SYMBOLS_SET.contains(word[i])) {
                symbols = true;
            }else if (word[i] >= '0' && word[i] <= '9') {
                number = true;
            } else if (word[i] >= 'A' && word[i] <= 'Z') {
                uppercase = true;
            } else if (word[i] >= 'a' && word[i] <= 'z') {
                lowercase = true;
            } 
        }

        /*
         * O : other
         * N : number
         * U : Upper
         * L : lower
         */
        if (symbols) {
            return "O";
        }
        if (number && (!(uppercase || lowercase))) {
            return "N";
        }
        if (number && (uppercase || lowercase)) {
            return "O";
        }
        if (_syl.isVNESE(syl)) {
            if (uppercase) {
                return "U";
            }
            if (lowercase) {
                return "L";
            }
        }
        return "O";
    }

    /**
     *
     *
     * @param text
     * @param ref
     * @return
     */
    private String normalizeText(String text, int ref) {
        StringBuilder ans = new StringBuilder();

        // replaceNorm some UTF-8 char by one byte char
        for (int i = 0; i < 3; ++i) {
            text = text.replaceAll(sourceNorm[i], replaceNorm[i]);
        }
        StringBuilder newT = new StringBuilder(text);
        // segment symbols
        if (ref == Configure.PREDICT) {
            for (int i = 0; i < newT.length(); ++i) {
                if (Configure.SYMBOLS_SET.contains(newT.charAt(i))) {
                    newT.insert(i + 1, " ");
                    newT.insert(i, " ");
                    i += 2;
                }
            }
        }
        char[] chars = newT.toString().toCharArray();
        // remove consecutive space and underscore
        for (int i = 0; i < chars.length; ++i) {
            if ((chars[i] == Configure.SPACE || chars[i] == Configure.UNDER)
                && (chars[i - 1] == Configure.SPACE || chars[i - 1] == Configure.UNDER)) {
                // do nothing
            } else {
                ans.append(chars[i]);
            }
        }

        return ans.toString();
    }

    /**
     * 
     * @param text
     * @param ref Mode is LEARN =0 Or PREDICT=1
     * @return 
     */
    public List<FeaturesOfSyllabel> token(String text, int ref) {
        text = normalizeText(text, ref);
        text += Configure.SPACE;
        List<FeaturesOfSyllabel> ans = new ArrayList<>();
        int pos = 0, prev = -1, N = text.length();
        boolean segment;
        
        char[] chars = text.toCharArray();
        
        for (pos = 0; pos < N; pos++) {
            if (ref == Configure.LEARN) /* Learning OR training */ {
                segment = (chars[pos] == Configure.SPACE || chars[pos] == Configure.UNDER);
            } else {
                segment = (chars[pos] == Configure.SPACE);
            }

            if (segment) {
                if (pos <= prev + 1) {
                    prev = pos;
                    continue;
                }
                FeaturesOfSyllabel dummy = new FeaturesOfSyllabel();
                dummy.syllabel = text.substring(prev + 1, pos);
                dummy.type = detectTypeSyl(dummy.syllabel);
                dummy.label = (chars[pos] == Configure.SPACE ? Configure.SPACE_LABEL : Configure.UNDER_LABEL);
                prev = pos;
                ans.add(dummy);
            }
        }
        return ans;
    }

    public void erase(int x) {
        for (Feat feat : feats) {
            feat.remove(x);
        }
    }

    public void clear() {

    }
}
