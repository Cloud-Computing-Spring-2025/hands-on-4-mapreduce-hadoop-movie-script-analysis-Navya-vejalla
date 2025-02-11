package com.movie.script.analysis;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.HashSet;

public class UniqueWordsReducer extends Reducer<Text, Text, Text, Text> {
    private Text result = new Text();

    @Override
    public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        HashSet<String> uniqueWords = new HashSet<>();
        for (Text val : values) {
            String valStr = val.toString();
            String[] words = valStr.replaceAll("[\\[\\],]", "").split(" ");
            for (String word : words) {
                uniqueWords.add(word.trim());
            }
        }
        result.set(uniqueWords.toString());
        context.write(key, result);
    }
}