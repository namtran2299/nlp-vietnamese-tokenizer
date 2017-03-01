/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.vn.ntv.vnsegment;

import org.junit.Test;

/**
 *
 * @author namtv19
 */
public class TestLearner {
//    @Test
    public void test() throws Exception{
        
        Learner leaner = new Learner("train/train.txt", "./train", 3);
        leaner.train();
    }
    
    public static void main(String[] args) throws Exception {
        new TestLearner().test();
    }
}
