/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.vn.ntv.vnsegment;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author namtv19
 */
public class StrMap {

    Map<String, Integer> smap = new HashMap<>();
//    int size = 1;

    public StrMap() {
    }

    public Map<String, Integer> getSmap() {
        return smap;
    }
    
    /**
     *
     * @param str
     * @param ref
     * @return
     */
    public int getNum(final String str, int ref) {
        Integer n = smap.get(str);
        if (n == null) {
            if (ref == Configure.LEARN) {
                n = smap.size()+1;
                smap.put(str, n);
                return n;
            } else {
                return smap.size() + 1;
            }
        } else {
            return n;
        }
    }

    public int getSize() {
        return smap.size();
    }

    public void insert(String str, int v) {
        smap.put(str, v);
    }

    /**
     * 
     * @param mapFile 
     */
    public void print(String mapFile) {
        try (FileAppender out = new FileAppender(mapFile)) {
            out.writeString(smap.size() + "\n");
            for (Map.Entry<String, Integer> entrySet : smap.entrySet()) {
                String key = entrySet.getKey();
                Integer value = entrySet.getValue();

                out.writeString(key + " " + value + "\n");
            }
        } catch (IOException ex) {
            Logger.getLogger(StrMap.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * 
     * @param path
     * @return 
     */
    public boolean load(String path) {
        List<String> lines = FileUtil.readAllLines(path);
        int index, pos;
        lines.remove(0);
        for (String line : lines) {
            if (line.isEmpty()) {
                continue;
            }
            char[] chars = line.toCharArray();
            pos = line.length() - 1;
            while ( pos>=0  && chars[pos] >= '0' && chars[pos] <= '9') {
                pos--;
            }
            if (pos==-1) {
                continue;
            }

            index = 0;
            for (int i = pos+1; i < chars.length; i++) {
                index = index * 10 + (int) (chars[i] - '0');
            }
            smap.put(line.substring(0, pos), index);
        }
        return true;
    }
}
