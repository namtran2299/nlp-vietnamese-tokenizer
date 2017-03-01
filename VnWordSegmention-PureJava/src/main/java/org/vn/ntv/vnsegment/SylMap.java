/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.vn.ntv.vnsegment;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author namtv19
 */
public class SylMap {

    Set<String> dSet = new HashSet<>();

    public SylMap() {
        loadData();
    }

    private void loadData() {
        List<String> lines = FileUtil.readAllLines("./data/VNsyl.txt");
        lines.remove(0);
        for (String line : lines) {
            if (!line.isEmpty()) {
                dSet.add(line);
            }
        }
    }

    public boolean isVNESE(String syllabel) {
        return dSet.contains(syllabel.toLowerCase());
    }
}
