package com.movie.script.analysis;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class DialogueLengthMapper extends Mapper<Object, Text, Text, IntWritable> {
    private Text character = new Text();
    private IntWritable dialogueLength = new IntWritable();

    @Override
    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();

        // Assuming the format is "Character: Dialogue"
        if (line.contains(":")) {
            String[] parts = line.split(":");
            character.set(parts[0].trim());
            dialogueLength.set(parts[1].trim().length()); // Get the length of the dialogue
            context.write(character, dialogueLength);
        }
    }
}