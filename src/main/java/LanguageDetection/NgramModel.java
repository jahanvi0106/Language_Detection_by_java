package LanguageDetection;

import org.apache.log4j.Logger;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class NgramModel implements Serializable {
    private static final long serialVersionUID = 3181620556363962390L;
    public static Logger log = Logger.getLogger(NgramModel.class);
    private Map<String, Map<String, Integer>> ngrams;


    public NgramModel(Map<String, Map<String, Integer>> ngrams) {
        this.ngrams = ngrams;
    }

    public NgramModel(){
        log.info("Ngram model call for model..");
    }

    public static NgramModel load(String filename) throws IOException, ClassNotFoundException {
        log.info("Ngram Model Process running");
        byte[] data = Files.readAllBytes(Paths.get(filename));
        log.info("convert into byte format");
        InputStream inputStream = new ByteArrayInputStream(data);
        ObjectInputStream objInStream = new ObjectInputStream(inputStream);
        NgramModel ngramModel = (NgramModel) objInStream.readObject();
        log.info("model ready");
        return ngramModel;
    }

    public static Map<String, Map<String, Integer>> load1(String filename) throws IOException, ClassNotFoundException {
        log.info("Ngram Model Process running");
        byte[] data = Files.readAllBytes(Paths.get(filename));
        log.info("convert into byte format");
        InputStream inputStream = new ByteArrayInputStream(data);
        ObjectInputStream objInStream = new ObjectInputStream(inputStream);
        NgramModel ngramModel = (NgramModel) objInStream.readObject();
        log.info("model ready");
        Map<String, Map<String, Integer>> ngram = ngramModel.getNgrams();
        return ngram;
    }

    // Deserialization
    // generic example
    @SuppressWarnings("unchecked")
    public static <T> T readObject(InputStream is, Class<T> anyClass)
            throws IOException, ClassNotFoundException {
        T result = null;
        try (ObjectInputStream ois = new ObjectInputStream(is)) {
            result = (T) ois.readObject();
        }
        return result;
    }

    // Deserialization
    // Convert object to byte[]
    public static byte[] convertObjectToBytes(Object obj) {
        ByteArrayOutputStream boas = new ByteArrayOutputStream();
        try (ObjectOutputStream ois = new ObjectOutputStream(boas)) {
            ois.writeObject(obj);
            return boas.toByteArray();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        throw new RuntimeException();
    }

    public void save(String filename) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename));
        out.writeObject(this);
        out.close();
    }

    public Map<String, Map<String, Integer>> getNgrams() {
        return ngrams;
    }

}
