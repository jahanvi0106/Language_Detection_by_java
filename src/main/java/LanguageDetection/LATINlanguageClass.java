package LanguageDetection;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class LATINlanguageClass {
    public static Logger log = Logger.getLogger(LATINlanguageClass.class);
    public static Map<String, Map<String, Integer>> ngrams = new HashMap<>();
    private int n;
    public LATINlanguageClass(int n) {
        this.n = n;
    }
    public String detect(String text, double percentageRelative) throws IOException, ClassNotFoundException {
//        log.info("Model is Loading...");
//        NgramModel model = null;
//        try {
//            model = NgramModel.load("W:\\MapperProject\\src\\main\\java\\LanguageDetection\\Models\\modelLatin.ser");
//            log.info("Model loaded!");
//        } catch (IOException | ClassNotFoundException e) {
//            log.error(e.getMessage());
//        }
//        assert model != null;
//        Map<String, Map<String, Integer>> ngrams = model.getNgrams();
        if(ngrams.isEmpty())
            ngrams = NgramModel.load1("D:\\internship\\College_report\\MapperProject\\src\\main\\java\\LanguageDetection\\Models\\modelLatin.ser");
        Map<String, Integer> langCount = new HashMap<>();

        String[] words = text.split("\\s+");
        for (int i = 0; i < words.length - n + 1; i++) {
            String ngram = String.join(" ", Arrays.copyOfRange(words, i, i + n));
            if (ngrams.containsKey(ngram)) {
                Map<String, Integer> ngramCount = ngrams.get(ngram);
                for (String lang : ngramCount.keySet()) {
                    langCount.put(lang, langCount.getOrDefault(lang, 0) + ngramCount.get(lang));
                }
            }
        }
        log.info("ngrams counting completed.....");
        int total = langCount.values().stream().mapToInt(Integer::intValue).sum();
        StringBuilder result = new StringBuilder();
        for (String lang : langCount.keySet()) {
            double percentage = percentageRelative * langCount.get(lang) / total;
            result.append(String.format("%s-> %.2f%%\n ", lang, percentage));
            log.info(lang+" detected");
        }
        return result.toString().trim();
    }
    public static String classifyLATIN(String text, double percentage) {
        LATINlanguageClass detector = new LATINlanguageClass(3);
        try {
            String lang = detector.detect(text,percentage);
            return lang;
        } catch (NullPointerException e) {
            log.error("NullPointerException..!");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*public static NgramModel latinModel(){
        log.info("Model is Loading...");
        NgramModel model = null;
        try {
            model = NgramModel.load("W:\\Jahanvi\\IdeaProjects\\Project\\Models\\modelArabic.ser");
        } catch (IOException | ClassNotFoundException e) {
            log.error(e.getMessage());
        }
        log.info("Model loaded!");
        return model;
    }*/
}
