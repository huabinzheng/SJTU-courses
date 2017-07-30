import com.google.common.base.Optional;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import scala.Tuple2;
import java.math.BigDecimal;


/**
 * Created by zhenghb on 2017/5/25.
 */
public class Main2 {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setMaster("local").setAppName("sparkLab");
        final JavaSparkContext sc = new JavaSparkContext(conf);

        JavaRDD<String> deviceInput = sc.textFile("device");
        //此处不能对id作过滤操作，如果此id在dvalue表中不存在时
        //left join会添加一个NULL值，同样符合did > 0 and did < 1000的要求
        JavaPairRDD<Integer, String> devices = deviceInput.mapToPair(
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
        //devices.saveAsTextFile("outputDevice");

        JavaRDD<String> dvalueInput = sc.textFile("dvalue");
        //此处同样不能做过滤操作，否则会删除部分did后
        //device表中id不能匹配，因为左外连接产生了NULL值，多计入最后结果
        JavaPairRDD<Integer, String> values = dvalueInput.mapToPair(
                new PairFunction<String, Integer, String>() {
                    public Tuple2<Integer, String> call(String s) throws Exception {
                        String[] splits = s.split(",");
                        String id = splits[0];
                        Integer intId = Integer.parseInt(id);
                        String type = splits[1];
                        String value = splits[2];
                        return new Tuple2(intId, type + "," + value);
                    }
                }
        );
        //values.saveAsTextFile("outputValue");
        //为了后期计算平均数，在得到了type和value的键值对后，再添加了一个个数1
        JavaPairRDD<Integer,Tuple2<String, Optional<String>>> joinResult = devices.leftOuterJoin(values);
        JavaPairRDD<String, Tuple2<String, Integer>> joinFilter = joinResult.mapToPair(
                new PairFunction<Tuple2<Integer, Tuple2<String, Optional<String>>>, String, Tuple2<String, Integer>>() {
                    public Tuple2<String, Tuple2<String, Integer>> call(Tuple2<Integer, Tuple2<String, Optional<String>>> tuple) throws Exception {
                        Integer id = tuple._1();
                        String type = tuple._2()._1();
                        String optional = tuple._2()._2().toString();
                        String nullString = "Optional.absent()";
                        String valueArg;
                        if (!optional.equals(nullString)) {
                            String str = optional.substring(12,optional.length() - 1);

                            String[] splits = str.split(",");
                            String date = splits[0];
                            String value = splits[1];
                            valueArg = value;
                            //删除不满足id > 0 and id < 1000, date is null的条件的项
                            //为了简便省去了filter函数，在此标记value项为NULL，在计算平均值不考虑
                            if (id < 0 || id > 1000) valueArg = "NULL";
                            if (!date.equals("NULL")) valueArg = "NULL";
                        } else valueArg = "NULL";
                        return new Tuple2<String, Tuple2<String, Integer>>(type, new Tuple2<String, Integer>(valueArg,1));
                    }
                }
        );

        //做平均数操作，依次累加和，并计算累加的个数
        JavaPairRDD<String, Tuple2<String, Integer>> sums = joinFilter.reduceByKey(
                new Function2<Tuple2<String, Integer>, Tuple2<String, Integer>, Tuple2<String, Integer>>() {
                    public Tuple2<String, Integer> call(Tuple2<String, Integer> t1, Tuple2<String, Integer> t2) throws Exception {
                        String s = t1._1(); Integer num = t1._2();
                        String s2 = t2._1(); Integer num2 = t2._2();
                        if (!s.equals("NULL") && !s2.equals("NULL")) {
                            BigDecimal b1 = new BigDecimal(s);
                            BigDecimal b2 = new BigDecimal(s2);
                            return new Tuple2<String, Integer>(b1.add(b2).toString(), num + num2);
                        } else if (s.equals("NULL")) return t2;
                        else if (s2.equals("NULL")) return t1;
                        return new Tuple2<String, Integer>("NULL",1);
                    }
                }
        ).sortByKey(true);

        JavaRDD<String> avgs = sums.map(
                new Function<Tuple2<String, Tuple2<String, Integer>>, String>() {
                    public String call(Tuple2<String, Tuple2<String, Integer>> tuple) throws Exception {
                        String type = tuple._1();
                        String value = tuple._2()._1();
                        Integer num = tuple._2()._2();
                        if (value.equals("NULL")) return "null," + type + ",null";
                        BigDecimal sum = new BigDecimal(value);
                        BigDecimal n = new BigDecimal(num);
                        return "null," + type + "," + sum.divide(n).toString();
                    }
                }
        );
        avgs.saveAsTextFile("result1_2");
    }

}
