import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import scala.Tuple2;
import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setMaster("local").setAppName("sparkLab");
        JavaSparkContext sc = new JavaSparkContext(conf);

        JavaRDD<String> deviceInput = sc.textFile("device");
        //device表中不满足条件的记录较多，所以先做过滤操作，减少join的计算量
        //过滤得到满足 id > 0 且 id < 1000 条件的RDD
        JavaRDD<String> deviceFilter = deviceInput.filter(
                new Function<String, Boolean>() {
                    public Boolean call(String s) throws Exception {
                        String[] splits = s.split(",");
                        String id = splits[0];
                        Integer intId = Integer.parseInt(id);
                        if ((intId > 0) && (intId < 1000)) return true;
                            else return false;
                    }
                }
        );
        //Key-Value操作
        JavaPairRDD<Integer, String> devices = deviceFilter.mapToPair(
                new PairFunction<String, Integer, String>() {
                    public Tuple2<Integer, String> call(String s) throws Exception {
                        String[] splits = s.split(",");
                        String id = splits[0];
                        Integer intId = Integer.parseInt(id);
                        String type = splits[1];
                        return new Tuple2(intId, type);
                    }
                }
        );

        JavaRDD<String> dvalueInput = sc.textFile("dvalue");
        //dvalues表中不满足条件的记录同样较多，所以先做过滤操作，减少join的计算量
        //过滤得到满足 did > 0 且 did < 1000 条件的RDD
        //过滤得到满足 date为NULL的RDD
        JavaRDD<String> valueFilter = dvalueInput.filter(
                new Function<String, Boolean>() {
                    public Boolean call(String s) throws Exception {
                        String[] splits = s.split(",");
                        String did = splits[0];
                        String date = splits[1];
                        Integer intId = Integer.parseInt(did);
                        Boolean IdRequirment = (intId > 0) && (intId < 1000);
                        Boolean DateRequirment = date.equals("NULL");
                        if (IdRequirment && DateRequirment) return true;
                            else return false;
                    }
                }
        );
        //Key-Value操作
        //Double类型在之后的加法操作中会发生精度错误 所以此处先保留字符串形式
        JavaPairRDD<Integer, String> values = valueFilter.mapToPair(
                new PairFunction<String, Integer, String>() {
                    public Tuple2<Integer, String> call(String s) throws Exception {
                        String[] splits = s.split(",");
                        String did = splits[0];
                        Integer intDid = Integer.parseInt(did);
                        String value = splits[2];
                        return new Tuple2(intDid, value);
                    }
                }
        );

        JavaPairRDD<Integer, Tuple2<String, String>> joinResult = devices.join(values);
        JavaPairRDD<String, String> typeValuePair = joinResult.mapToPair(
                new PairFunction<Tuple2<Integer, Tuple2<String, String>>, String, String>() {
                    public Tuple2<String, String> call(Tuple2<Integer, Tuple2<String, String>> tuple) throws Exception {
                        return new Tuple2<String, String>(tuple._2()._1(), tuple._2()._2());
                    }
                }
        );

        //做求和操作
        JavaPairRDD<String, String> sums = typeValuePair.reduceByKey(
                new Function2<String, String, String>() {
                    public String call(String s, String s2) throws Exception {
                        BigDecimal b1 = new BigDecimal(s);
                        BigDecimal b2 = new BigDecimal(s2);
                        return b1.add(b2).toString();
                    }
                }
        ).sortByKey(true);
        sums.coalesce(1, true).saveAsTextFile("result1_1");
    }
}
