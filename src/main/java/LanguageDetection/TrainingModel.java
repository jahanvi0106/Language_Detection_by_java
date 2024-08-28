package LanguageDetection;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

public class TrainingModel {

    private static Map<String, Map<String, Integer>> ngrams = new HashMap<>();
    static Logger log = Logger.getLogger(TrainingModel.class);
    int n;

    public TrainingModel(int n){
        this.n = n;
    }

    void train(String filename, String language) throws IOException {
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

    public static void main(String []a) {
        TrainingModel detector = new TrainingModel(3);
        try {
//            detector.train("W:\\Jahanvi\\IdeaProjects\\Language\\src\\main\\java\\LanguageDetection\\corpus\\japanese.txt","Japanese");
//            detector.train("W:\\Jahanvi\\IdeaProjects\\Language\\src\\main\\java\\LanguageDetection\\corpus\\chinese.txt","Chinese");
//            detector.train("W:\\Jahanvi\\IdeaProjects\\Language\\src\\main\\java\\LanguageDetection\\corpus\\arabic.txt", "Arabic");
//            detector.train("W:\\Jahanvi\\Dataset\\corpus\\urdu.txt", "Urdu");
//            detector.train("W:\\Jahanvi\\IdeaProjects\\Language\\src\\main\\java\\LanguageDetection\\corpus\\english.txt", "English");
//            detector.train("W:\\Jahanvi\\IdeaProjects\\Language\\src\\main\\java\\LanguageDetection\\corpus\\french.txt", "French");
//            detector.train("W:\\Jahanvi\\IdeaProjects\\Language\\src\\main\\java\\LanguageDetection\\corpus\\german.txt", "German");
//            detector.train("W:\\Jahanvi\\IdeaProjects\\Language\\src\\main\\java\\LanguageDetection\\corpus\\italian.txt","Italian");
//            detector.train("W:\\Jahanvi\\IdeaProjects\\Language\\src\\main\\java\\LanguageDetection\\corpus\\dutch.txt","Dutch");
//            detector.train("W:\\Jahanvi\\IdeaProjects\\Language\\src\\main\\java\\LanguageDetection\\corpus\\spanish.txt","Spanish");
//            detector.train("W:\\Jahanvi\\Dataset\\corpus\\portugese.txt","Portuguese");
//            detector.train("W:\\Tjava07\\Language detection data\\Markov chain\\corpus\\swedish.txt","Swedish");
//            detector.train("W:\\Tjava07\\Language detection data\\Markov chain\\corpus\\turkish.txt","Turkish");
//            detector.train("W:\\Tjava07\\Language detection data\\language detection task demo code\\src\\main\\java\\LanguageDetection\\urduinput.txt","Turkish");
            detector.train("W:\\Tjava07\\Language detection data\\Markov chain\\corpus\\russian.txt","Russian");
            detector.train("W:\\Tjava07\\Language detection data\\Markov chain\\corpus\\kazakh.txt","Kazakh");

            log.info("Model saving......");
            NgramModel model = new NgramModel(ngrams);
            model.save("W:\\Tjava07\\Language detection data\\language detection task demo code\\src\\main\\java\\LanguageDetection\\modelCyrillicNew.ser");
            log.info("Model save successfully..!");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
