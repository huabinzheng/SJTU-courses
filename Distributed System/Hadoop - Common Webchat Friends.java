import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class WeChatFriends {

    public static class MainMapper
            extends Mapper<Object, Text, Text, Text>{
        public void map(Object key, Text value, Context context
        ) throws IOException, InterruptedException {
            String line = value.toString();
            String[] split=line.split(":");
            String src =split[0];
            String[] friends=split[1].split(",");
            for (String des:friends) {
                context.write(new Text(des), new Text(src));
            }
        }
    }

    public static class MainReducer
            extends Reducer<Text,Text,Text,Text> {
        public void reduce(Text key, Iterable<Text> friends,
                           Context context
        ) throws IOException, InterruptedException {
            StringBuilder builder = new StringBuilder();
            for (Text text : friends) {
                builder.append(text + ",");
            }
            context.write(key, new Text(builder.toString()));
        }
    }

    public static class ReMapper extends Mapper<Object, Text, Text, Text>{
        public void map(Object key, Text value, Context context
        ) throws IOException, InterruptedException {
            String line = value.toString();
            String[] split=line.split("\t");
            String commonFriend =split[0];
            String[] friends=split[1].split(",");

            for (int i = 0; i < friends.length; i++) {
                for (int j = i + 1; j < friends.length; j++) {
                    String s1 = friends[i];
                    String s2 = friends[j];
                    if (s1.compareTo(s2) > 0) {
                        String tmp = s1; s1 = s2; s2 = tmp;
                    }
                    context.write(new Text("{" + s1 + "," + s2 + "}"), new Text(commonFriend));
                }
            }
        }
    }

    public static class ReReducer extends Reducer<Text,Text,Text,Text> {
        public void reduce(Text key, Iterable<Text> friends,
                           Context context
        ) throws IOException, InterruptedException {
            StringBuilder builder = new StringBuilder();
            for (Text text : friends) {
                builder.append(text + ",");
            }
            builder.deleteCharAt(builder.length() - 1);
            context.write(key, new Text(builder.toString()));
        }
    }

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "wordcount");
        job.setJarByClass(WeChatFriends.class);
        job.setMapperClass(MainMapper.class);
        job.setReducerClass(MainReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        if (job.waitForCompletion(true)) {
            Job job2 = Job.getInstance(conf, "wordcount");
            job2.setJarByClass(WeChatFriends.class);
            job2.setMapperClass(ReMapper.class);
            job2.setReducerClass(ReReducer.class);
            job2.setOutputKeyClass(Text.class);
            job2.setOutputValueClass(Text.class);
            FileInputFormat.addInputPath(job2, new Path(args[1]));
            FileOutputFormat.setOutputPath(job2, new Path(args[2]));
            System.exit(job2.waitForCompletion(true)? 0 : 1);
        }
        System.exit(job.waitForCompletion(true)? 0 : 1);
    }
}