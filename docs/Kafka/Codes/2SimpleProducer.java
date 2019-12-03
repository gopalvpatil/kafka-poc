package com.jpmc;

import java.util.Properties;
import java.util.Scanner;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

public class SimpleProducer 
{
	private static Scanner in;

	public static void main(String[] argv)throws Exception 
	{
	if (argv.length != 1) {
	System.err.println("Please specify 1 parameters ");
	System.exit(-1);
	}
	
	String topicName = argv[0];
	in = new Scanner(System.in);
	System.out.println("Enter message(type exit to quit)");
	
	//Configure the Producer
	Properties configProperties = new Properties();
	configProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
	
	configProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.ByteArraySerializer");
	
	configProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
	
	configProperties.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, CountryPartitioner.class.getCanonicalName());
	
	configProperties.put("partitions.0","USA");
	configProperties.put("partitions.1","India");
	
	org.apache.kafka.clients.producer.Producer producer = new
	KafkaProducer(configProperties);
	
	String line = in.nextLine();
	
	while(!line.equals("exit")) 
	{
	ProducerRecord<String, String> rec = new ProducerRecord<String, String>(topicName,
	line);
	
	// Adding a Custom Callback method
	producer.send(rec, new Callback() 
	{
	public void onCompletion(RecordMetadata metadata, Exception exception) 
	{
	System.out.println("Message sent to topic ->" + metadata.topic()+ " ,parition->" +
	metadata.partition() +" stored at offset->" + metadata.offset());
	}
	}); // end of send
	
	line = in.nextLine();
	}

	in.close();
	producer.close();
	}
}