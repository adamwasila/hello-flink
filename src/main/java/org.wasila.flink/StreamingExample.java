package org.wasila.flink;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.apache.flink.util.Collector;

public class StreamingExample {
    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironment();

        DataStreamSource<String> dataStreamSource = env.readTextFile("data/test.txt");

        SingleOutputStreamOperator<String> op = dataStreamSource.flatMap(new FlatMapFunction<String, String>() {
            @Override
            public void flatMap(String value, Collector<String> out) throws Exception {
                String[] values = value.split("[^a-zA-Z]+");
                for (String v : values) {
                    out.collect(v);
                }
            }
        });

        op.addSink(new SinkFunction<String>() {
            @Override
            public void invoke(String s, Context ctx) throws Exception {
                System.out.println("--> " + s);
            }
        });

//        op.print();

        env.execute("Flink Streaming Example");
    }
}
