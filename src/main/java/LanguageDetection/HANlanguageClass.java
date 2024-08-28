package LanguageDetection;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.SegToken;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class HANlanguageClass {
    public static Logger log = Logger.getLogger(HANlanguageClass.class);
    private int n;
    public static Map<String, Map<String, Integer>> ngrams = new HashMap<>();

    public HANlanguageClass(int n) {
        this.n = n;
    }

    public void train(String filename, String language) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] words = line.split("[\\p{Punct}\\s]+");
            for (int i = 0; i < words.length - n + 1; i++) {
                String ngram = String.join(" ", Arrays.copyOfRange(words, i, i + n));
                Map<String, Integer> langCount = ngrams.computeIfAbsent(ngram, k -> new HashMap<>());
                langCount.put(language, langCount.getOrDefault(language, 0) + 1);
            }
        }
        reader.close();
        log.info(language+" Model trained");
    }

    public String detect(String text, double percentageRelative) throws IOException, ClassNotFoundException {
//        log.info("Model is Loading...");
//        NgramModel model = null;
//        try {
//            model = NgramModel.load("W:\\Jahanvi\\IdeaProjects\\Project\\train2\\src\\main\\java\\modelHantrigramNew.ser");
//            log.info("Model loaded!");
//        } catch (IOException | ClassNotFoundException e) {
//            log.error(e.getMessage());
//        }
//        assert model != null;
//        Map<String, Map<String, Integer>> ngrams = model.getNgrams();
//        if(ngrams.isEmpty())
//            ngrams = NgramModel.load1("W:\\MapperProject\\src\\main\\java\\LanguageDetection\\Models\\modelHan.ser");

        Map<String, Integer> langCount = new HashMap<>();
        JiebaSegmenter segment = new JiebaSegmenter();

        // perform text segmentation and get a list of SegToken objects
        List<SegToken> tokens = segment.process(text, JiebaSegmenter.SegMode.INDEX);
        String[] words = new String[tokens.size()];
        for (int i = 0; i < tokens.size(); i++) {
            words[i] = tokens.get(i).word;
        }
        for (int i = 0; i < words.length - n + 1; i++) {
            String ngram = String.join(" ", Arrays.copyOfRange(words, i, i + n));
            if (ngrams.containsKey(ngram)) {
                Map<String, Integer> ngramCount = ngrams.get(ngram);
                for (String lang : ngramCount.keySet()) {
                    langCount.put(lang, langCount.getOrDefault(lang, 0) + ngramCount.get(lang));
                }
            }
        }

        int total = langCount.values().stream().mapToInt(Integer::intValue).sum();
        StringBuilder result = new StringBuilder();
        for (String lang : langCount.keySet()) {
            double percentage = percentageRelative * langCount.get(lang) / total;
            result.append(String.format("%s-> %.2f%%\n", lang, percentage));
            log.info(lang+" detected");
        }

        return result.toString().trim();
    }
    public static String classifyHAN(String text, double percentage) {
        HANlanguageClass detector = new HANlanguageClass(3);
        try {
            detector.train("D:\\internship\\corpus\\chinese.txt","Japanese");
            detector.train("D:\\internship\\corpus\\japanese.txt","Chinese");

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
    /*public static NgramModel hanModel(){
        log.info("Model is Loading...");
        NgramModel model = null;
        try {
            model = NgramModel.load("W:\\Jahanvi\\IdeaProjects\\Project\\Models\\modelHan.ser");
        } catch (IOException | ClassNotFoundException e) {
            log.error(e.getMessage());
        }
        log.info("Model loaded!");
        return model;
    }*/
}
