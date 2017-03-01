/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.vn.ntv.vnsegment;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author namtv19
 */
public class Predictor {

    Machine predictor;

    public Predictor() {
        try {
            predictor = new Machine(3, "", Configure.PREDICT);
            if (!predictor.load()) {
                System.out.println("Failed to load data from dongdu.model and dongdu.map");
                throw new RuntimeException("Failed to load data from dongdu.model and dongdu.map");
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

    }
    
    /**
     * Segment string
     * @param in
     * @return 
     */
    public String segment(String in){
        return predictor.segment(in);
    }

    /**
     * Segment string
     * @param in
     * @return 
     */
    public List<String> segmentList(String in){
        return predictor.segmentList(in);
    }
    
    /**
     * Segment string
     * @param in
     * @return 
     */
    public List<Token> segmentToken(String in){
        return predictor.segmentToken(in);
    }
    
    /**
     * 
     * @param inputfile
     * @param outputfile
     * @return
     * @throws Exception 
     */
    public int segment(final String inputfile, final String outputfile) throws Exception {
        long start = System.currentTimeMillis(), finish;
        int number_lines = 0;

        //Predictor
        System.out.println("Start segmenting ...");
        segmentForlder(predictor, inputfile, outputfile);
        System.out.println("End segmenting.");
        finish = System.currentTimeMillis();
        System.out.println(String.format("Segment %d line(s). \n", number_lines));
        System.out.println(String.format("Segmentation took %f seconds to execute\n",
            ((double) (finish - start)) / Configure.CLOCKS_PER_SEC)); //
        return 0;
    }

    public void segmentForlder(final Machine predictor, final String inputfile, final String outputfile) throws Exception {
        File fo = new File(outputfile);
        if (!fo.exists()) {
            fo.mkdirs();
        }
        File[] files = new File(inputfile).listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                segmentForlder(predictor, file.getPath(), outputfile + File.separator + file.getName());
            } else {
                segmentFile(predictor, file.getPath(), outputfile + File.separator + file.getName());
            }
        }

    }

    public static int segmentFile(final Machine predictor, final String inputfile, final String outputfile) throws Exception {
        final int[] number_lines = new int[]{0};

        try (FileAppender out = new FileAppender(outputfile)) {
            FileUtil.scanline(inputfile, new IDataProcessor<String>() {

                @Override
                public boolean processQueue(java.lang.String v) {
                    try {
                        out.writeString(predictor.segment(v) + "\n");
                        number_lines[0] = number_lines[0] + 1;
                    } catch (IOException ex) {
                    }
                    return true;
                }
            });
        }
        return number_lines[0];
    }

   

}
