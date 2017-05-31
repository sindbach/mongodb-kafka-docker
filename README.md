# Docker for MongoDB And Apache Kafka
An example of docker to set up a single Apache Kafka broker with MongoDB as consumer. 
Read more info at [MongoDB & Data Streaming - Implementing a MongoDB Kafka Consumer](https://www.mongodb.com/blog/post/mongodb-and-data-streaming-implementing-a-mongodb-kafka-consumer)

** For demo purposes only ** 

This project is using: 

* [MongoDB Java Driver v3.4](http://mongodb.github.io/mongo-java-driver/3.4/)
* [Apache Kafka v0.8.2.2 (Scala 2.11)](https://www.apache.org/dyn/closer.cgi?path=/kafka/0.8.2.2/kafka_2.11-0.8.2.2.tgz)
* [MongoDB v3.4](https://www.mongodb.com/mongodb-3.4)


### Starting up

You can start by running command :

```
docker-compose run kafka bash
```

This would run the kafka docker and the mongodb docker, and provides you with bash shell for the kafka.

From the kafka docker instance, you could reach the MongoDB instance using `mongodb` hostname.

First of all, let's compile the example Java code: 

```
cd /home/ubuntu/workspace; 
mvn package; 
```

After a successful build, you should be able to find the resulting `jar` file in:

```
/home/ubuntu/workspace/target/kafka-demo-1.0-SNAPSHOT.jar
```


Let's start a single kafka broker:

```
zookeeper-server-start.sh ${KAFKA_HOME}/config/zookeeper.properties &
kafka-server-start.sh ${KAFKA_HOME}/config/server.properties &
```

Now you can stream example documents contained in file [Fish.json](kafka/workspace/src/resources/Fish.json) via local `producer`: 
```
kafka-console-producer.sh --broker-list localhost:9092 --topic fish < /home/ubuntu/workspace/src/resources/Fish.json;
```

Execute our earlier `jar` file to read the messages in topic `fish` above into MongoDB

```
java -jar /home/ubuntu/workspace/target/kafka-demo-1.0-SNAPSHOT.jar
```

Once completed, you can check the result via : 

```
mongo --host "mongodb:30000" --eval "db=db.getSiblingDB('kafka'); db.fish.find({'breed':'Cod'}).limit(3);"
```

See also: [Apache Kafka Quickstart tutorial](https://kafka.apache.org/quickstart)
