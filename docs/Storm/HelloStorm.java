package com.jpmc;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;

public class HelloStorm {

	public static void main(String[] args) throws Exception{
		
		Config config = new Config();
		config.put("inputFile", args[0]);
		config.setDebug(true);
		config.put(Config.TOPOLOGY_MAX_SPOUT_PENDING, 1);
		
		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout("line-reader-spout", new LineRSpout());
		builder.setBolt("word-spitter", new WordSplitBolt()).shuffleGrouping("line-reader-spout");
		builder.setBolt("word-counter", new WordCountBolt()).shuffleGrouping("word-spitter");
		
		LocalCluster cluster = new LocalCluster();
		cluster.submitTopology("HelloStorm", config, builder.createTopology());
	//	Thread.sleep(10000);
		
	//	cluster.shutdown();
	}

}
