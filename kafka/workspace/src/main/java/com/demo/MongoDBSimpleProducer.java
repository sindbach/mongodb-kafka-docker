package com.demo;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import org.apache.commons.text.RandomStringGenerator;

import java.util.Properties;
import java.util.Random;

public class MongoDBSimpleProducer {
    public static void main(String args[]) {
        String topicName = "fish";
        Properties props = new Properties();
        props.put("bootstrap.servers", "127.0.0.1:9092");
        props.put("acks", "all");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "com.demo.FishSerializer");
     
        Producer<String, Fish> producer = new KafkaProducer<String, Fish>(props);
           
        for(int i = 0; i < 10; i++)
        {
            RandomStringGenerator generator = new RandomStringGenerator.Builder()
                                                  .withinRange('a', 'z').build();
            String randomFishName = generator.generate(10);
            int randomId = new Random().nextInt(10000000 - 1000000) + 1000000;
            Fish entry = new Fish(randomId, randomFishName, Fish.Breed.Random);
            try {
                System.out.println("Producing: " + entry.toString());
                producer.send(new ProducerRecord<String, Fish>(topicName, Integer.toString(i), entry));
            } catch (Exception e) {
                System.out.println("Oops:" + e);
                e.printStackTrace();
            }
        }
        System.out.println("Messages sent successfully");
        producer.close();
    }
}
