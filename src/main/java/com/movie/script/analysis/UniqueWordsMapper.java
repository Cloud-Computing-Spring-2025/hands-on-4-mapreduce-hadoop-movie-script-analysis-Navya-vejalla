package com.movie.script.analysis;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.HashSet;
import java.util.StringTokenizer;

public class UniqueWordsMapper extends Mapper<Object, Text, Text, Text> {
    private Text character = new Text();
    private Text uniqueWords = new Text();

    @Override
    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();

        // Assuming the format is "Character: Dialogue"
        if (line.contains(":")) {
            String[] parts = line.split(":");
            character.set(parts[0].trim());
            String dialogue = parts[1].trim();

            // Use a HashSet to collect unique words
            HashSet<String> wordsSet = new HashSet<>();
            StringTokenizer tokenizer = new StringTokenizer(dialogue);
            while (tokenizer.hasMoreTokens()) {
                wordsSet.add(tokenizer.nextToken().toLowerCase());
            }

            uniqueWords.set(wordsSet.toString()); // Store unique words as a comma-separated string
            context.write(character, uniqueWords);
        }
    }
}