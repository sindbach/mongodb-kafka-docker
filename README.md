# Docker for MongoDB And Apache Kafka

** UPDATE ** 

This has been superseeded by [MongoDB Kafka Docker: End to End Example](https://github.com/mongodb/mongo-kafka/blob/master/docker/README.md#mongodb--kafka-docker-end-to-end-example). Which utilises [MongoDB Kafka Connector](https://github.com/mongodb/mongo-kafka)


An example of docker to set up a single Apache Kafka broker with MongoDB as consumer. 
Read more info at [MongoDB & Data Streaming - Implementing a MongoDB Kafka Consumer](https://www.mongodb.com/blog/post/mongodb-and-data-streaming-implementing-a-mongodb-kafka-consumer)

** For demo purposes only ** 

This project is using: 

* [MongoDB Java Driver v3.8](http://mongodb.github.io/mongo-java-driver/3.8/)
* [Apache Kafka v1.1.1 (Scala 2.11)](https://www.apache.org/dyn/closer.cgi?path=/kafka/1.1.1/kafka_2.11-1.1.1.tgz)
* [MongoDB v4.0](https://www.mongodb.com/mongodb-4.0)


### Execute

You can start by running command :

```s
docker-compose run kafka
```

This would run the Kafka docker and the mongodb docker, and provides you with bash shell for the Kafka.

From the Kafka docker instance, you could reach the MongoDB instance using `mongodb` hostname.

First of all, let's compile the example Java code: 

```
cd /home/ubuntu/workspace; 
mvn package; 
```

After a successful build, you should be able to find the resulting `jar` file in:

```
/home/ubuntu/workspace/target/kafka-demo-1.0-SNAPSHOT.jar
```

### Starting Kafka

Let's start a single Kafka broker:

```s
zookeeper-server-start.sh ${KAFKA_HOME}/config/zookeeper.properties &
kafka-server-start.sh ${KAFKA_HOME}/config/server.properties &
```

### Produce a stream

Now you can stream example documents contained in file [Fish.json](kafka/workspace/src/resources/Fish.json) via local `producer` using the provided Kafka tool: 

```s
kafka-console-producer.sh --broker-list localhost:9092 --topic fish < /home/ubuntu/workspace/src/resources/Fish.json;
```

### Run Kafka Consumer

Execute our earlier `jar` file to read the messages in topic `fish` above into MongoDB

```s
java -cp /home/ubuntu/workspace/target/kafka-demo-1.0-SNAPSHOT.jar com.demo.MongoDBSimpleConsumer
```

Once completed, you can check the result using [mongo](https://docs.mongodb.com/manual/mongo/) shell: 

```s
mongo --host "mongodb:30000" --eval "db=db.getSiblingDB('kafka'); db.fish.find({'breed':'Cod'}).limit(5);"
```

### Alternative Producer 

The above method uses the provided Kafka tool `kafka-console-producer.sh` to send the content of JSON file as stream. You can also use a simple Java class producer to send data into the stream. 

```s
java -cp /home/ubuntu/workspace/target/kafka-demo-1.0-SNAPSHOT.jar com.demo.MongoDBSimpleProducer
```

Same as above, once completed you can read the messages in the stream and store into MongoDB using the consumer: 

```s
java -cp /home/ubuntu/workspace/target/kafka-demo-1.0-SNAPSHOT.jar com.demo.MongoDBSimpleConsumer
```

Once completed, you can check the result using [mongo](https://docs.mongodb.com/manual/mongo/) shell: 

```s
mongo --host "mongodb:30000" --eval "db=db.getSiblingDB('kafka'); db.fish.find({'breed':'Random'}).limit(5);"
```

### Misc.

#### Clear up the topics queue 

```s
kafka-topics.sh --zookeeper localhost:2181 --delete --topic fish 
```

### More Information

See also: [Apache Kafka Quickstart tutorial](https://kafka.apache.org/quickstart)
