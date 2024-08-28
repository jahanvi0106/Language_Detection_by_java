package LanguageDetection;


import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CyrillicLanguageClass {
    public static Logger log = Logger.getLogger(CyrillicLanguageClass.class);
    private int n;
    public static Map<String, Map<String, Integer>> ngrams = new HashMap<>();

    public CyrillicLanguageClass(int n) {
        this.n = n;
    }


    public String detect(String text, double percentageRelative) throws IOException, ClassNotFoundException {

        if(ngrams.isEmpty())
            ngrams = NgramModel.load1("D:\\internship\\College_report\\MapperProject\\src\\main\\java\\LanguageDetection\\Models\\modelCyrillicNew.ser");
        Map<String, Integer> langCount = new HashMap<>();

        log.info("Detecting Language..");
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
            log.info(lang + " detected");
        }
        return result.toString().trim();
    }

    public static String classifyCYRILLIC(String text, double percentage) {
        CyrillicLanguageClass detector = new CyrillicLanguageClass(3);
        try {
            String lang = detector.detect(text, percentage);
            return lang;
        } catch (NullPointerException e) {
            log.error(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    /*public static NgramModel cyrillicModel(){
        log.info("Model is Loading...");
        NgramModel model = null;
        try {
            model = NgramModel.load("W:\\Jahanvi\\IdeaProjects\\Project\\Models\\modelCyrillic.ser");
        } catch (IOException | ClassNotFoundException e) {
            log.error(e.getMessage());
        }
        log.info("Model loaded!");
        return model;
    }*/
}

