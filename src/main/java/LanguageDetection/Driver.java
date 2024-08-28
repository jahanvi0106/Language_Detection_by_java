package LanguageDetection;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class Driver  {

    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        Configuration c = new Configuration();
//        c.addResource(new Path("W:\\TJava10\\hadoop-3.3.4\\etc\\hadoop\\core-site.xml"));
//        c.addResource(new Path("W:\\TJava10\\hadoop-3.3.4\\etc\\hadoop\\hdfs-site.xml"));
//        c.addResource(new Path("W:\\TJava10\\hadoop-3.3.4\\etc\\hadoop\\mapred-site.xml"));
//        c.addResource(new Path("W:\\TJava10\\hadoop-3.3.4\\etc\\hadoop\\yarn-site.xml"));
//
//        c.set("mapreduce.map.memory.mb","4096");
//
//        Path input = new Path("/LanguageDetection/input1");
//        Path output = new Path("/LanguageDetection/langOutputtt1320");
////
        Path input = new Path("D:\\internship\\College_report\\MapperProject\\src\\main\\java\\LanguageDetection\\input1");
        Path output = new Path("D:\\internship\\College_report\\MapperProject\\src\\main\\java\\LanguageDetection\\output456");


        Job job = Job.getInstance(c);
        job.setJarByClass(Driver.class);

        job.setMapperClass(MapperLanguageDetection.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        job.setNumReduceTasks(0);
        FileInputFormat.addInputPath(job, input);
        FileOutputFormat.setOutputPath(job, output);

        System.out.println(job.waitForCompletion(true) ? 0 : -1);

    }

}