import kafka.serializer.StringDecoder;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaPairInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka.KafkaUtils;
import scala.Tuple2;

import java.util.*;

/**
 * Created by Ken on 12/15/16.
 */
public class KafkaDemoMain {

    public static void main(String[] args) {

        SparkConf conf = new SparkConf().setMaster("local[2]").setAppName("KafkaDemo");
        JavaStreamingContext jsc = new JavaStreamingContext(conf, Durations.seconds(10));

        Map<String,String> kafkaParameters = new HashMap<String, String>();
        kafkaParameters.put("bootstrap.servers", "localhost:9092");
        kafkaParameters.put("group.id", "spark");

        Set<String> topics = new HashSet<String>();
        topics.add("test");

        JavaPairInputDStream<String, String> lines = KafkaUtils.createDirectStream(jsc,
                String.class, String.class, StringDecoder.class, StringDecoder.class,
                kafkaParameters, topics);

        JavaDStream<String> words = lines.flatMap(new FlatMapFunction<Tuple2<String, String>, String>() {
            @Override
            public Iterator<String> call(Tuple2<String, String> stringStringTuple2) throws Exception {
                return Arrays.asList(stringStringTuple2._2.split(" ")).iterator();
            }
        });

        JavaPairDStream<String, Integer> pairs = words.mapToPair(
                new PairFunction<String, String, Integer>() {
                    @Override
                    public Tuple2<String, Integer> call(String s) throws Exception {
                        return new Tuple2<String, Integer>(s, 1);
                    }
                });

        JavaPairDStream<String, Integer> wordCount = pairs.reduceByKey(
                new Function2<Integer, Integer, Integer>() {
                    @Override
                    public Integer call(Integer v1, Integer v2) throws Exception {
                        return v1 + v2;
                    }
                });

        wordCount.print();

        jsc.start();

        try {
            jsc.awaitTermination();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}