//Producer Code

package com.jpmc;

import java.util.Properties;
import java.util.Scanner;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class SimpleProducer 
{
	private static Scanner in;
	public static void main(String[] argv)throws Exception 
	{
		if (argv.length != 1) 
		{
		System.err.println("Please specify 1 parameters ");
		System.exit(-1);
		}

		String topicName = argv[0];
		in = new Scanner(System.in);
		System.out.println("Enter message(type exit to quit)");

		//Configure the Producer
		Properties props = new Properties();
		props.put("bootstrap.servers", "localhost:9092");
		props.put("key.serializer",
		"org.apache.kafka.common.serialization.ByteArraySerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

		Producer<String, String> producer = new KafkaProducer<String, String>(props);
		String line = in.nextLine();

		while(!line.equals("exit")) 
		{
		ProducerRecord<String, String> rec = new ProducerRecord<String,
		String>(topicName, line);
		producer.send(rec);
		line = in.nextLine();
		}
		in.close();
		producer.close();
	}
}