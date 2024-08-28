//package LanguageDetection;
//
//import org.apache.log4j.Logger;
//
//import java.io.IOException;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.Map;
//
//public class ClassifyLanguage {
//    public static Logger log = Logger.getLogger(ClassifyLanguage.class);
//    private int n;
//
//    public ClassifyLanguage(int n) {
//        this.n = n;
//    }
//
//
//    public String detect(String text, double percentageRelative, Map<String, Map<String, Integer>> ngrams) throws NullPointerException {
//        log.info("Detecting language.......................");
//        Map<String, Integer> langCount = new HashMap<>();
//        String[] words = text.split(" ");
//        for (int i = 0; i < words.length - n + 1; i++) {
//            String ngram = String.join(" ", Arrays.copyOfRange(words, i, i + n));
//
//            if (ngrams.containsKey(ngram)) {
//                Map<String, Integer> ngramCount = ngrams.get(ngram);
//                for (String lang : ngramCount.keySet()) {
//                    langCount.put(lang, langCount.getOrDefault(lang, 0) + ngramCount.get(lang));
//                }
//            }
//        }
//        System.out.println(langCount.size());
//        int total = langCount.values().stream().mapToInt(Integer::intValue).sum();
//        StringBuilder result = new StringBuilder();
//        for (String lang : langCount.keySet()) {
//            double percentage = percentageRelative * langCount.get(lang) / total;
//            result.append(String.format("%s --> %.2f%%  \n", lang, percentage));
//            log.info(lang+" detected");
//        }
//        return result.toString().trim();
//    }
//    public static String classifyLATIN(String text, double percentage) {
//        ClassifyLanguage detector = new ClassifyLanguage(3);
//        Map<String, Map<String, Integer>> ngrams = LATINlanguageClass.latinModel().getNgrams();
//        String lang = detector.detect(text, percentage, ngrams);
//        return lang;
//    }
//
//    public static String classifyHAN(String text, double percentage) {
//        ClassifyLanguage detector = new ClassifyLanguage(3);
//        Map<String, Map<String, Integer>> ngrams = HANlanguageClass.hanModel().getNgrams();
//        String lang = detector.detect(text, percentage, ngrams);
//        return lang;
//    }
//    public static String classifyARABIC(String text, double percentage) {
//        ClassifyLanguage detector = new ClassifyLanguage(3);
//        Map<String, Map<String, Integer>> ngrams = ARABIClanguageClass.arabicModel().getNgrams();
//        String lang = detector.detect(text, percentage, ngrams);
//        return lang;
//    }
//    public static String classifyCYRILLIC(String text, double percentage) {
//        ClassifyLanguage detector = new ClassifyLanguage(3);
//        Map<String, Map<String, Integer>> ngrams = CyrillicLanguageClass.cyrillicModel().getNgrams();
//        String lang = detector.detect(text, percentage, ngrams);
//        return lang;
//    }
//
//}
