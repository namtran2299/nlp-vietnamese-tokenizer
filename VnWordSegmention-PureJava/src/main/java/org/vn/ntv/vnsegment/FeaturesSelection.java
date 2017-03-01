/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.vn.ntv.vnsegment;

import de.bwaldvogel.liblinear.Model;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author namtv19
 */
public class FeaturesSelection {

    StrMap strmap = new StrMap(), newStrmap = new StrMap();
    Model model = new Model();
    String PATH;

    public FeaturesSelection(String str) {
        init(str);
    }

    private void init(String str) {
        try {
            PATH = Configure.ensureEndingSlash(str);
            String dummy;
            System.out.println("Load strmap");
            dummy = PATH + "dongdu.map";
            strmap.load(dummy);
            System.out.println("Load model");
            dummy = PATH + "dongdu.model";
            model = Model.load(new File(dummy));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * 
     */
    public void selection() {
        System.out.println("Start selection");

        // convert map<string, size_t> to string's array[size_t]
        int mapsize = strmap.getSize();
        System.out.println(mapsize);
        String[] dummy = new String[mapsize + 1];
        for (int i = 0; i < dummy.length; i++) {
            dummy[i] = "";
        }
        for (Map.Entry<String, Integer> entrySet : strmap.getSmap().entrySet()) {
            String key = entrySet.getKey();
            Integer value = entrySet.getValue();
            dummy[value] = key;
        }

        // find features that have weight is 0
        int i, n, nr_feature = model.getNrFeature();
        n = (model.getBias() >= 0) ? nr_feature + 1 : nr_feature;
        int w_size = n;

        Set<Integer> unused = new HashSet<>();
        double[] weightModel = model.getFeatureWeights();
        for (i = 0; i < w_size; i++) {
            if (Configure.isZero(weightModel[i])) {
                unused.add(i + 1);
            }
        }
        // create new strmap
        for (i = 1; i < mapsize; ++i) {
            if (unused.contains(i)) {
                newStrmap.getNum(dummy[i], Configure.LEARN);
            }
        }
        System.out.println("Unused features is ");
    }

    /**
     * Save model
     */
    public void save() {
        String dummy;
        // save new strmap
        dummy = PATH + "dongdu.map";
        File f = new File(dummy);
        if (f.exists()) {
            f.delete();
        }
        newStrmap.print(dummy);

        // save new model
        dummy = PATH + "dongdu.model";
        f = new File(dummy);
        if (f.exists()) {
            f.delete();
        }
        try (FileAppender out = new FileAppender(dummy)) {
            out.writeString("solver_type L1R_LR\n");
            out.writeString("nr_class 2\n");
            out.writeString("label 1 2\n");
            out.writeString(String.format("nr_feature %d\n", newStrmap.getSize()));
            out.writeString("bias -1\n");
            out.writeString("w\n");
            int i, n, nr_feature = model.getNrFeature();
            n = (model.getBias() >= 0) ? nr_feature + 1 : nr_feature;
            int w_size = n;
            double[] weightModel = model.getFeatureWeights();

            for (i = 0; i < w_size; i++) {
                if (!Configure.isZero(weightModel[i])) {
                    out.writeString(String.format("%.15f ", weightModel[i]));
                }
            }
        } catch (IOException ex) {
        }
    }
}
