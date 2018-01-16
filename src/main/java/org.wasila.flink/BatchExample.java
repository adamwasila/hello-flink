package org.wasila.flink;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;

import java.util.Arrays;
import java.util.List;

public class BatchExample {

    public static void main(String[] args) throws Exception {
        final ExecutionEnvironment env = ExecutionEnvironment.createLocalEnvironment();

        List<String> list = Arrays.asList("a", "b", "c");

        DataSource<String> dataSource = env.fromCollection(list);
        dataSource.map(new MapFunction<String, String>() {
            @Override
            public String map(String value) throws Exception {
                return "'" + value + "'";
            }
        }).print();
//        env.execute();
    }

}
