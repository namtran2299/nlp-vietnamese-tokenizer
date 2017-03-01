/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.vn.ntv.vnsegment;

import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author namtv19
 */
public class Feat {

    int label;
    Set<Integer> vals  = new TreeSet<>() ;

    public Feat() {
    }

    public Feat(int label, Set<Integer> vals) {
        this.label = label;
        this.vals = vals;
    }

    public void setLabel(int label) {
        this.label = label;
    }

    public void setVals(Set<Integer> vals) {
        this.vals = vals;
    }

    public int getLabel() {
        return label;
    }

    public Set<Integer> getVals() {
        return vals;
    }

    public void remove(int x) {
        vals.remove(x);
    }

    public int getSize() {
        return vals.size();
    }
}
