/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.vn.ntv.vnsegment;

import de.bwaldvogel.liblinear.FeatureNode;
import de.bwaldvogel.liblinear.Linear;
import de.bwaldvogel.liblinear.Model;
import de.bwaldvogel.liblinear.Parameter;
import de.bwaldvogel.liblinear.Problem;
import de.bwaldvogel.liblinear.SolverType;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author namtv19
 */
public final class Machine {

    final int index_SPACE = 1;
    final int index_UNDER = 2;
    int reference = Configure.LEARN;
    DicMap dicmap = new DicMap();
    StrMap strmap = new StrMap();
    Model model = new Model();

    int WINDOW_LENGTH;
    String PATH;

    Pattern PAT_NOMALIZE = Pattern.compile("[ _][ _]|(_[\\.:/-@$£€¢]_)+|\\._|_\\.|_\\%", 42);

    /* return number of bytes of a UTF8 char */
    public int getByteOfUTF8(char c) {
        if (c <= 0x7F) {
            return 1;
        } else if (0xC2 <= c && c <= 0xDF) {
            return 2;
        } else if (0xE0 <= c && c <= 0xEF) {
            return 3;
        } else if (0xF0 <= c && c <= 0xF7) {
            return 4;
        } else if (0xF8 <= c && c <= 0xFB) {
            return 5;
        } else if (0xFC <= c && c <= 0xFD) {
            return 6;
        } else {
            return 0;
        } // c is not head byte of character
    }

    public Machine(int window_length, String path, int ref) {
        WINDOW_LENGTH = window_length;
        PATH = Configure.ensureEndingSlash(path);
        reference = ref;
        if (ref == Configure.LEARN) {
            extract("một ví_dụ", ref); // dont erase it.
        }
    }

    /**
     * Convert a String to vector of featuresOfSyllabel
     *
     * @param sentence
     */
    public List<FeaturesOfSyllabel> convert(String sentence, Feats feats) {
        int pos = 0;
        int end = sentence.length() - 1;
        // delete control characters, such as Return, tabs, ... and SPACE
        // in begin and end of String. Control characters's oct is smaller than 41.
        while (sentence.length() > pos && sentence.charAt(pos) <= 40) {
            pos++;
        }
        while (end >= 0 && sentence.charAt(end) <= 40) {
            end--;
        }

        sentence = sentence.substring(pos, end + 1);

        for (int i = 0; i < WINDOW_LENGTH; ++i) {
            sentence = "BOS " + sentence + " BOS";
        }
        List<FeaturesOfSyllabel> vfeats;
        vfeats = feats.token(sentence, reference);
        return vfeats;
    }

    /* Convert a integer(single char) to String */
    public String itostr(int x) {
        StringBuilder ans = new StringBuilder();
        ans.append((x < 0) ? "-" : "+"); // sign of x
        if (x < 0) {
            x = -x;
        }
        ans.append(x);

        return ans.toString();
    }

    /* Convert a String to vfeats, extract features and put it in feats */
    public Pair<Feats, List<FeaturesOfSyllabel>> extract(String sentence, int ref) {
        Feats feats = new Feats();
        return extract(sentence, ref, feats);
    }

    /* Convert a String to vfeats, extract features and put it in feats */
    public Pair<Feats, List<FeaturesOfSyllabel>> extract(String sentence, int ref, Feats feats) {
        // convert sentence(String) to vfeats;
        List<FeaturesOfSyllabel> vfeats = convert(sentence, feats);

        int length = vfeats.size();
        int label;
        String index = "", dummy = "";

        // debug
        for (int i = WINDOW_LENGTH; i < length - WINDOW_LENGTH - 1; ++i) {
            Set<Integer> featHashSet = new TreeSet<>();
            // get 1-gram
            for (int j = i - WINDOW_LENGTH + 1; j <= i + WINDOW_LENGTH; ++j) {
                index = itostr(j - i) + "|";
                featHashSet.add(strmap.getNum(index + vfeats.get(j).syllabel, ref));
                featHashSet.add(strmap.getNum(index + vfeats.get(j).type, ref));
            }
            // get 2-gram
            for (int j = i - WINDOW_LENGTH + 1; j < i + WINDOW_LENGTH; ++j) {
                index = itostr(j - i) + "||";
                featHashSet.add(strmap.getNum(index + vfeats.get(j).syllabel + " " + vfeats.get(j + 1).syllabel, ref));
                featHashSet.add(strmap.getNum(index + vfeats.get(j).type + " " + vfeats.get(j + 1).type, ref));
            }
            // get Dictionary-features
            for (int j = 1; j < Configure.MAX_WORD_LENGTH; ++j) {
                for (int k = i - j + 1; k <= i + 1; ++k) {
                    dummy = vfeats.get(k).syllabel;
                    for (int z = k + 1; z < k + j; ++z) {
                        dummy += " " + vfeats.get(z).syllabel;
                    }
                    if (dicmap.isWord(dummy)) {
                        // word segment is LEFT of dictionary features
                        if (k == i + 1) {
                            index = "L(" + itostr(k - i) + ")|";
                        }
                        // word segment is RIGHT of dictionary features
                        if (k + j - 1 == i) {
                            index = "R(" + itostr(k - i) + ")|";
                        }
                        // word segment is INSIDE of dictionary features
                        if (k <= i && k + j - 1 > i) {
                            index = "I(" + itostr(k - i) + ")|";
                        }
                        featHashSet.add(strmap.getNum(index + dummy, ref));
                    }
                }
            }
            // get label
            label = (vfeats.get(i).label == 1) ? index_SPACE : index_UNDER;
            if (featHashSet.size() > 0) {
                Feat thisfeat = new Feat(label, featHashSet);
                feats.add(thisfeat);
            }
        } // end of i
        return new Pair<>(feats, vfeats);
    }

    /* Convert a feats format to liblinear's problem struct */
    private Problem getProblem(Feats feats) {
        Problem problem = new Problem();
        int sizeOfFeats = feats.size();
        FeatureNode[][] x = new FeatureNode[sizeOfFeats][];
        double[] y = new double[sizeOfFeats];

        for (int i = 0; i < sizeOfFeats; ++i) {
            y[i] = feats.get().get(i).label;
            FeatureNode[] xx = new FeatureNode[feats.get().get(i).getSize()];
            x[i] = xx;
            int j = 0;
            for (Integer index : feats.get().get(i).getVals()) {
                xx[j] = new FeatureNode(index, 1);
                j++;
            }
//            xx[j] = new FeatureNode(feats.get().get(i).getSize(), 1);
        }

        problem.l = sizeOfFeats;
        problem.n = strmap.getSize();
        problem.y = y;
        problem.x = x;
        problem.bias = -1;
        return problem;
    }


    /* Train problem by Liblinear L1R_LR */
    public Problem training(Feats feats) {
        // init parameter
        // type of Machine learning Algorithm
        Parameter _parameter = new Parameter(SolverType.L1R_LR, 1, 0.01);
        _parameter.setWeights(new double[]{1.0}, new int[]{1});

        long start, finish;

        start = System.currentTimeMillis();

        System.out.println("Start training ...");
        Problem problem = getProblem(feats);
        model = Linear.train(problem, _parameter);
        System.out.println("Finish training. ");

        finish = System.currentTimeMillis();
        System.out.println(String.format("training took %f seconds to execute\n",
            ((double) (finish - start)) / 1000)); //
        return problem;
    }

    /* print dongdu.model and dongdu.map */
    public void print() throws IOException {
        String modelfile = PATH + "dongdu.model";
        System.out.println("Save model file : " + modelfile);
        model.save(new File(modelfile));

        // write map file from strmap
        String mapfile = PATH + "dongdu.map";
        System.out.println("Save map file : " + mapfile);
        strmap.print(mapfile);
    }

    /* do close test */
    public double close_test(Feats feats, Problem problem) {
        int count = 0;
        for (int i = 0; i < feats.size(); ++i) {
            if (Linear.predict(model, problem.x[i]) != feats.get().get(i).label) {
                count++;
            }
        }

        double P = 100.0 - (double) (count) / (double) (feats.size()) * 100.0;
        System.out.println("Close test : " + P + "%");
        System.out.println(count + " : " + feats.size());
        return P;
    }

    /* a double is 0 ? */
    public boolean zero(double x) {
        return x < 1e-10 && x > -1e-10;
    }

    /* do features selection */
    public void featuresSelection() {
        int i;
        int nr_feature = model.getNrFeature();
        int n;

        n = (model.getBias() >= 0) ? nr_feature + 1 : nr_feature;
        int w_size = n;
        double[] ww = model.getFeatureWeights();
        for (i = 0; i < w_size; i++) {
            // unused features
            if (zero(ww[i])) {
                // find i in feats and delete it-
            }
        }

    }

    public boolean load() throws IOException {
        System.out.println("Start loading ...");
        model = Model.load(new File("./data/dongdu.model"));
        if (!strmap.load("data/dongdu.map")) {
            System.out.println("End loading ...");
            return false;
        }
        System.out.println("End loading ...");
        return true;
    }

    /**
     *
     * @param sentence
     * @return
     */
    public String segment(String sentence) {
        // initialize feats
        // extract sentence to feats
        Pair<Feats, List<FeaturesOfSyllabel>> pair = extract(sentence, Configure.PREDICT);
        Feats feats = pair._1;
        List<FeaturesOfSyllabel> vfeats = pair._2;
        if (feats.size() == 0) {
            return "";
        }

        // convert feats to Liblinear's problem struct
        Problem problem = getProblem(feats);

        StringBuilder ans = new StringBuilder();
        for (int i = 0; i < feats.size(); i++) {
            if (Linear.predict(model, problem.x[i]) == index_SPACE) {
                ans.append(vfeats.get(i + WINDOW_LENGTH).syllabel).append(Configure.SPACE);
            } else {
                ans.append(vfeats.get(i + WINDOW_LENGTH).syllabel).append(Configure.UNDER);
            }
        }
        ans.append(vfeats.get(feats.size() + WINDOW_LENGTH).syllabel);
        feats.clear();
//        return ans.toString();
        return normlizeText(ans.toString());
    }

    /**
     *
     * @param sentence
     * @return
     */
    public List<String> segmentList(String sentence) {
        List<String> tokens = new LinkedList<>();
        // initialize feats
        // extract sentence to feats
        Pair<Feats, List<FeaturesOfSyllabel>> pair = extract(sentence, Configure.PREDICT);
        Feats feats = pair._1;
        List<FeaturesOfSyllabel> vfeats = pair._2;
        if (feats.size() == 0) {
            return tokens;
        }

        // convert feats to Liblinear's problem struct
        Problem problem = getProblem(feats);

        StringBuilder token = new StringBuilder();
        for (int i = 0; i < feats.size(); i++) {
            if (Linear.predict(model, problem.x[i]) == index_SPACE) {
                token.append(vfeats.get(i + WINDOW_LENGTH).syllabel);
                tokens.add(normlizeText(token.toString()));
                token.setLength(0);
            } else {
                token.append(vfeats.get(i + WINDOW_LENGTH).syllabel).append(Configure.SPACE);
            }
        }
        token.append(vfeats.get(feats.size() + WINDOW_LENGTH).syllabel);
        tokens.add(normlizeText(token.toString()));
        token.setLength(0);
        return tokens;
    }

    /**
     *
     * @param sentence
     * @return
     */
    public List<Token> segmentToken(String sentence) {
        List<Token> tokens = new LinkedList<>();
        // initialize feats
        // extract sentence to feats
        Pair<Feats, List<FeaturesOfSyllabel>> pair = extract(sentence, Configure.PREDICT);
        Feats feats = pair._1;
        List<FeaturesOfSyllabel> vfeats = pair._2;
        if (feats.size() == 0) {
            return tokens;
        }

        // convert feats to Liblinear's problem struct
        Problem problem = getProblem(feats);

        StringBuilder token = new StringBuilder();
        int pos = 0;
        for (int i = 0; i < feats.size(); i++) {
            if (Linear.predict(model, problem.x[i]) == index_SPACE) {
                token.append(vfeats.get(i + WINDOW_LENGTH).syllabel);
                String l = normlizeText(token.toString());
                tokens.add(new Token(l, TypeChecker.getType(l), pos, pos + token.length()));
                pos = pos + token.length() + 1;
                token.setLength(0);
            } else {
                token.append(vfeats.get(i + WINDOW_LENGTH).syllabel).append(Configure.UNDER);
            }
        }
        token.append(vfeats.get(feats.size() + WINDOW_LENGTH).syllabel);
        String l = normlizeText(token.toString());
        tokens.add(new Token(l, TypeChecker.getType(l), pos, pos + token.length()));
//        pos  = pos +token.length()+1;
        token.setLength(0);
        return tokens;
    }

    /**
     *
     * @param ans
     * @return
     */
    public String normlizeText(String ans) {
//        char[] chars = ans.toCharArray();
//        int pos = 0;

//        while (chars[0] <= 40 && chars.length > pos) {
//            pos++;
//        }
//        StringBuilder dummy = new StringBuilder();
//        dummy.append(ans.charAt(pos));
//        pos++;
//        for (int i = pos; i < ans.length(); ++i) {
//            if ((chars[i] == Configure.SPACE || chars[i] == Configure.UNDER)
//                && (chars[i - 1] == Configure.SPACE || chars[i - 1] == Configure.UNDER)) {
//                // do nothing
//            } else {
//                dummy.append(chars[i]);
//            }
//        }
        //[ _][ _]|_\._|\._|_\.
        // check the last character. If last character is '.' and previous character
        // is underscore, we will replace underscore by space
//        if (dummy.charAt(dummy.length() - 1) == '.' && dummy.charAt(dummy.length() - 2) == Configure.UNDER) {
//            dummy.setCharAt(dummy.length() - 2, Configure.SPACE);
//        }
//        return dummy.toString();
        StringBuffer newText = new StringBuffer(ans);
        List<int[]> matchedWordSet = new ArrayList<>();
        Matcher mat;
        mat = PAT_NOMALIZE.matcher(ans);

        while (mat.find()) {
            matchedWordSet.add(new int[]{mat.start(), mat.end()});
        }

        for (int i = matchedWordSet.size() - 1; i >= 0; i--) {
            int[] offs = matchedWordSet.get(i);
            //Get link
            String wordToReplace = newText.substring(offs[0], offs[1]);
            switch (wordToReplace) {
                case "_-_":
                    wordToReplace = "-";
                    break;
                case "_:_":
                    wordToReplace = ":";
                    break;
                case "_/_":
                    wordToReplace = "/";
                    break;
                case "_._":
                    wordToReplace = ".";
                    break;
                case "_@_":
                    wordToReplace = "@";
                    break;
                case "_$":
                    wordToReplace = "$";
                    break;
                case "_£":
                    wordToReplace = "£";
                    break;
                case "_€":
                    wordToReplace = "€";
                    break;
                case "_¢":
                    wordToReplace = "¢";
                    break;
                case "_%":
                    wordToReplace = "%";
                    break;
                case "_:_/_/_":
                    wordToReplace = "://";
                    break;
                default:
                    wordToReplace = "";

            }

            newText = newText.replace(offs[0], offs[1], wordToReplace);
        }

        return newText.toString();
    }
}
