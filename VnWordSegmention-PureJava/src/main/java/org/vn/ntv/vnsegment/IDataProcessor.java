/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.vn.ntv.vnsegment;

/**
 *
 * @author namtv
 */
public interface IDataProcessor<V> {

    boolean processQueue(V v);
}
