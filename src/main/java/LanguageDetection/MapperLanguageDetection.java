package LanguageDetection;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.log4j.Logger;
import java.io.IOException;
import java.util.Arrays;

//public class Mapper1 extends Mapper<Object, Text,Text, Text> {
//    private Text word = new Text();
//    private Text word1 = new Text();
//
//
//    @Override
//    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
//        String line = value.toString();
//        System.out.println(line);
//        word1.set(line);
//        word.set(LangdetectFromUnicode.fileContentRead(line));
//        context.write(word1,word);
//    }
//}

public class MapperLanguageDetection extends Mapper<Object, Text, Text, Text> {

    public static Logger log = Logger.getLogger(MapperLanguageDetection.class);

    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        log.info("Mapper Process Started....");
        String itr = value.toString();
        String [] file = itr.split("\\s+");
        System.out.println(Arrays.toString(file));
        String result = LangdetectFromUnicode.fileContentRead(file[1]);
        System.out.println("Result --> "+result);
        log.info("Result write in file");
        context.write(new Text(file[0]),new Text(result));
    }
}

