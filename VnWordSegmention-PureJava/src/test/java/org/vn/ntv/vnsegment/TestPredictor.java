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
public class TestPredictor {
    Predictor predictor  = new Predictor();
    @Test
    public void testSegment() {
        System.out.println(predictor.segment("27%. Namtran2299@gmail.com . 10.30.147.222 Sau một thời gian nghiên cứu về việc giải mã các bản tin CRRCC DCCH và có một số kết qủa bước đầu. http://edict.vn 25/5/2017 25-5-2017 25:12:22"));
        System.out.println(predictor.segmentList("Sau một thời gian nghiên cứu về việc giải mã các bản tin CRRCC DCCH và có một số kết qủa bước đầu."));
        System.out.println(predictor.segment("Em sẽ tổ chức 1.2 buổi seminar về việc giải mã các bản tin này và những hướng áp dụng và mở rộng tính năng cho ASS sắp tới"));
        System.out.println(predictor.segmentList("Em sẽ tổ chức 13 buổi seminar về việc giải mã các bản tin này và những hướng áp dụng và mở rộng tính năng cho ASS sắp tới"));
        System.out.println(predictor.segmentToken("Em sẽ tổ chức 1.5 buổi seminar về việc giải mã các bản tin này và những hướng áp dụng và mở rộng tính năng cho ASS sắp tới"));
    }
}
