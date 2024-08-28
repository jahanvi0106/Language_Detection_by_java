package LanguageDetection;


import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Models {

    public static Logger log = Logger.getLogger(Models.class);

    static  Map<String, Map<String, Integer>> ngrams = new HashMap<>();
    public static NgramModel latin_model = new NgramModel(ngrams);
    public static NgramModel han_model;
    public static NgramModel arabic_model;
    public static NgramModel cyrillic_model;

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        log.info("model loading....");
        latin_model = NgramModel.load("W:\\Jahanvi\\IdeaProjects\\Project\\Models\\modelLatin.ser");
//        arabic_model = NgramModel.load("W:\\Jahanvi\\IdeaProjects\\Project\\Models\\modelArabic.ser");
//        han_model = NgramModel.load("W:\\Jahanvi\\IdeaProjects\\Project\\Models\\modelHan.ser");
//        cyrillic_model = NgramModel.load("W:\\Jahanvi\\IdeaProjects\\Project\\Models\\modelCyrillic.ser");
        log.info("Model loaded.");

    }
}
