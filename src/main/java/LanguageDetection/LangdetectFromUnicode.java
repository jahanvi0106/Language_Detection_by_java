package LanguageDetection;


import org.apache.log4j.Logger;
import java.io.*;
import java.nio.file.Files;
import java.util.*;


public class LangdetectFromUnicode {
    public static Logger log = Logger.getLogger(LangdetectFromUnicode.class);

    private static final Map<Character.UnicodeScript, Integer> LANGUAGES = new HashMap<>();

    static {
        LANGUAGES.put(Character.UnicodeScript.LATIN, 1); //English, French, Portuguese, Dutch, Spanish, Swedish, German, Turkish, Italian
        LANGUAGES.put(Character.UnicodeScript.CYRILLIC, 2); //Russian, Kazakh
        LANGUAGES.put(Character.UnicodeScript.HAN, 3); //Chinese, Japanese
        LANGUAGES.put(Character.UnicodeScript.DEVANAGARI, 4); //Hindi
        LANGUAGES.put(Character.UnicodeScript.ARABIC, 5); //Arabic, Urdu
        LANGUAGES.put(Character.UnicodeScript.TAMIL, 6); //Tamil
        LANGUAGES.put(Character.UnicodeScript.MALAYALAM, 7); //Malayalam
        LANGUAGES.put(Character.UnicodeScript.GREEK, 8); //Greek
        LANGUAGES.put(Character.UnicodeScript.GUJARATI, 9); //Gujarati
        LANGUAGES.put(Character.UnicodeScript.KANNADA, 10); //Kannada
        LANGUAGES.put(Character.UnicodeScript.TELUGU, 11); //Telugu
        LANGUAGES.put(Character.UnicodeScript.HANGUL, 12); //Korean.txt
        LANGUAGES.put(Character.UnicodeScript.BENGALI, 13); //Bengali
        LANGUAGES.put(Character.UnicodeScript.THAI, 14); //Thai
        LANGUAGES.put(Character.UnicodeScript.GURMUKHI, 15); //Punjabi
        LANGUAGES.put(Character.UnicodeScript.COMMON, 16); // Common
    }

    public static String classifyLanguage(String text) {
        StringBuilder eng = new StringBuilder(" ");
        StringBuilder arb = new StringBuilder(" ");
        StringBuilder chn = new StringBuilder(" ");
        StringBuilder rus = new StringBuilder(" ");


        int[] frequencies = new int[LANGUAGES.size() + 1];
        String[] splitWord = text.split("[\\p{Punct}\\s]+");

        for (String word : splitWord) {
            Character.UnicodeScript script = Character.UnicodeScript.DEVANAGARI;
            for (int i = 0; i < word.length(); i++) {
                char c = word.charAt(i);
                if (Character.isLetter(c)) {
                    script = Character.UnicodeScript.of(c);
                    int index = LANGUAGES.get(script);
//                    System.out.println(index+" -- "+script);
                    frequencies[index]++;

                }
            }
            if (script.equals(Character.UnicodeScript.LATIN)) {
                eng.append(" ").append(word);
            } else if (script.equals(Character.UnicodeScript.HAN) || script.equals(Character.UnicodeScript.HIRAGANA)) {
                chn.append(" ").append(word);
            } else if (script.equals(Character.UnicodeScript.ARABIC)) {
                arb.append(" ").append(word);
            } else if (script.equals(Character.UnicodeScript.CYRILLIC)) {
                rus.append(" ").append(word);
            }
        }


        log.info("word Splitting done....");
        float maxIndex = Arrays.stream(frequencies).sum();
        StringBuilder result = new StringBuilder();
        for (int i = 1; i < frequencies.length; i++) {

            float temp = ((float) frequencies[i] / maxIndex) * 100;
            if (temp != 0) {

                if(getLanguageScript(i).equals(Character.UnicodeScript.LATIN)) {
                    if(eng!=null) {
                        log.info("words send to "+getLanguageScript(i));
                        String s = LATINlanguageClass.classifyLATIN(eng.toString(), temp);
                        result.append(s+"\n");
                    }
                }
                else if(getLanguageScript(i).equals(Character.UnicodeScript.HAN) || getLanguageScript(i).equals(Character.UnicodeScript.HIRAGANA)) {
                    log.info("words send to "+getLanguageScript(i));
                    String s = HANlanguageClass.classifyHAN(chn.toString(),temp);
                    result.append(s+"\n");
                }
                else if(getLanguageScript(i).equals(Character.UnicodeScript.ARABIC)) {
                    log.info("words send to "+getLanguageScript(i));
                    String s = ARABIClanguageClass.classifyARABIC(arb.toString(),temp);
                    result.append(s+"\n");
                }
                else if(getLanguageScript(i).equals(Character.UnicodeScript.CYRILLIC)) {
                    log.info("words send to "+getLanguageScript(i));
                    String s = CyrillicLanguageClass.classifyCYRILLIC(rus.toString(),temp);
                    result.append(s+"\n");
                }
                else
                    result.append(String.format("%s-> %.2f%%\n ", getLanguageScript(i), temp));
            }
        }
        return result.toString().trim();
    }

    private static Character.UnicodeScript getLanguageScript(int index) {

        for (Map.Entry<Character.UnicodeScript, Integer> entry : LANGUAGES.entrySet()) {
            if (entry.getValue() == index) {
                return entry.getKey();
            }
        }
        return null;
    }

    public static String fileContentRead(String filePath) {
        String s = null;
        try {
            //  File file = new File("W:\\Tjava07\\Language detection data\\language detection task demo code\\src\\main\\java\\LanguageDetection\\urduinput.txt");
            File file = new File(filePath);
            s = new String(Files.readAllBytes(file.toPath()));
            log.info("language detection Process started....");
            String ans = classifyLanguage(s);
            return ans;

        } catch (FileNotFoundException e) {
            log.error(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void main(String []args){
        log.info("Started..!");
//        TrainingModel.trainModel();
        log.info("File reading");
//        System.out.println("1 :  fileContentRead() start.");

        System.out.println(fileContentRead("D:\\internship\\College_report\\MapperProject\\src\\main\\java\\LanguageDetection\\input2.txt"));


        log.info("Program run successfully..!");
    }
}
