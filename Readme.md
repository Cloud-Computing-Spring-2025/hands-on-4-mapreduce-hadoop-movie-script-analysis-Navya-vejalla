# Movie Script Analysis using Hadoop MapReduce

## Project Overview
This project analyzes movie scripts using Hadoop MapReduce to extract meaningful insights. The analysis is divided into three tasks:

1. **Most Frequent Words by Character:**
   - Identifies and counts the most frequently used words by each character in the script.
   - Uses `CharacterWordMapper` to tokenize and emit words spoken by characters.
   - `CharacterWordReducer` aggregates the word counts per character.

2. **Dialogue Length Analysis:**
   - Computes the total length of dialogues spoken by each character.
   - `DialogueLengthMapper` extracts dialogue lengths for each character.
   - `DialogueLengthReducer` sums up the dialogue lengths per character.

3. **Unique Words by Character:**
   - Determines the unique words spoken by each character.
   - `UniqueWordsMapper` extracts and collects unique words using a HashSet.
   - `UniqueWordsReducer` processes the data to output distinct words for each character.

The script runs these tasks sequentially using Hadoop's Job API, processing the input movie script and generating structured insights as output.

## Prerequisites
- Hadoop 3.2.1 installed and configured
- Java 8 or later
- Maven for building the JAR

## Setup and Execution

### 1. Start the Hadoop Cluster
Run the following command to start the Hadoop cluster:

```bash
docker compose up -d
```

### 2. Build the Code
Build the project code using Maven:

```bash
mvn install
```

### 3. Move JAR File to Shared Folder
After building the code, move the generated JAR file to a shared folder for easy access:

```bash
mv target/*.jar shared-folder/input/code/
```

### 4. Copy JAR to Docker Container
Copy the generated JAR file to the Hadoop ResourceManager container:

```bash
docker cp shared-folder/input/code/hands-on2-movie-script-analysis-1.0-SNAPSHOT.jar resourcemanager:/opt/hadoop-3.2.1/share/hadoop/mapreduce/
```

### 5. Move Dataset to Docker Container
Copy the dataset file into the Hadoop ResourceManager container:

```bash
docker cp shared-folder/input/data/movie_dialogues.txt resourcemanager:/opt/hadoop-3.2.1/share/hadoop/mapreduce/
```

### 6. Connect to Docker Container
Access the Hadoop ResourceManager container:

```bash
docker exec -it resourcemanager /bin/bash
```

Navigate to the Hadoop directory:

```bash
cd /opt/hadoop-3.2.1/share/hadoop/mapreduce/
```

### 7. Set Up HDFS
Create a folder in HDFS for the input dataset:

```bash
hadoop fs -mkdir -p /input/dataset
```

Copy the input dataset to HDFS:

```bash
hadoop fs -put ./movie_dialogues.txt /input/dataset
```

### 8. Execute the MapReduce Job
Run the MapReduce job using the following command:

```bash
hadoop jar /opt/hadoop-3.2.1/share/hadoop/mapreduce/hands-on2-movie-script-analysis-1.0-SNAPSHOT.jar com.example.controller.Controller /input/dataset/movie_dialogues.txt /output
```

### 9. View the Output
To view the output of your MapReduce job, use:

```bash
hadoop fs -cat /output/*
```

### 10. Copy Output from HDFS to Local OS
To copy the output from HDFS to your local machine:

First, copy from HDFS to the container:

```bash
hdfs dfs -get /output /opt/hadoop-3.2.1/share/hadoop/mapreduce/
```

Then, use Docker to copy the output from the container to your local machine:

```bash
exit
docker cp resourcemanager:/opt/hadoop-3.2.1/share/hadoop/mapreduce/output/ shared-folder/output/
```

## Challenges Faced & Solutions

## 1. Issue While Running Hadoop JAR Command
- **Problem:** Encountered an issue while running the command:
  
  ```bash
  hadoop jar /opt/hadoop-3.2.1/share/hadoop/mapreduce/hands-on2-movie-script-analysis-1.0-SNAPSHOT.jar com.example.controller.Controller /input/dataset/movie_dialogues.txt /output
  ``` 
  The execution failed due to classpath or configuration issues.
- **Solution:**
  - Verified that the JAR file was correctly placed in `/opt/hadoop-3.2.1/share/hadoop/mapreduce/`.
  - Checked for typos in the class name (`com.example.controller.Controller`).
  - Ensured that the JAR was built correctly using Maven (`mvn clean install`).
  - Used `hadoop jar ...` with `-libjars` if dependencies were missing.
With these fixes, the MapReduce job executed successfully.

