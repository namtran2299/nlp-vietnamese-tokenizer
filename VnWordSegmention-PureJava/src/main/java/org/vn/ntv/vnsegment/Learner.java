/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.vn.ntv.vnsegment;

import de.bwaldvogel.liblinear.Problem;

/**
 *
 * @author namtv19
 */
public class Learner {

    String corpusLink;
    String outputPath;
    int windowLength;
    int line_num = 0;

    public Learner(String corpus_link, String outputPath, int window_length) {
        this.corpusLink = corpus_link;
        this.outputPath = outputPath;
        this.windowLength = window_length;
    }

    public Learner() {
    }

    public String getCorpusLink() {
        return corpusLink;
    }

    public void setCorpusLink(String corpus_link) {
        this.corpusLink = corpus_link;
    }

    public String getOutputPath() {
        return outputPath;
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }

    public int getWindowLength() {
        return windowLength;
    }

    public void setWindowLength(int window_length) {
        this.windowLength = window_length;
    }

    public void train() throws Exception {
        // Learner
        System.out.println("Learning...");
        final Machine learner = new Machine(windowLength, outputPath, Configure.LEARN);

        final Feats feats = new Feats();
        long start = System.currentTimeMillis(), finish;
        FileUtil.scanline(corpusLink, new IDataProcessor<String>() {

            @Override
            public boolean processQueue(String v) {
                learner.extract(v, Configure.LEARN, feats);
                line_num++;
                if (line_num % 10000 == 0) {
                    System.out.println(line_num);
                }
                return true;
            }
        });

        System.out.println(line_num + " line(s).");
        Problem problem = learner.training(feats);
        learner.close_test(feats, problem);
        learner.print();

        // Features Selection
        System.out.println("Features selection...");
        FeaturesSelection fselect = new FeaturesSelection(outputPath);
        fselect.selection();
        fselect.save();

        finish = System.currentTimeMillis();
        System.out.println(String.format("Learning took %f seconds\n",
            ((double) (finish - start)) / Configure.CLOCKS_PER_SEC)); //
    }

}
