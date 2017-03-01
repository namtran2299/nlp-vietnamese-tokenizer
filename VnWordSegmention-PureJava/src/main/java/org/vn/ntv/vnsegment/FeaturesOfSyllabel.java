/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.vn.ntv.vnsegment;

/**
 *
 * @author namtv19
 */
public class FeaturesOfSyllabel {

    String syllabel;
    String type;
    int label; /* 0 : SPACE, 1 : UNDER */

    public FeaturesOfSyllabel(String syllabel, String type, int label) {
        this.syllabel = syllabel;
        this.type = type;
        this.label = label;
    }

    public FeaturesOfSyllabel() {
    }

    
    public String getSyllabel() {
        return syllabel;
    }

    public void setSyllabel(String syllabel) {
        this.syllabel = syllabel;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getLabel() {
        return label;
    }

    public void setLabel(int label) {
        this.label = label;
    }
    
    

}
