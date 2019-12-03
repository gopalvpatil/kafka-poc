package com.jpmc;

import java.util.HashMap;
import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Tuple;



public class WordCountBolt implements IRichBolt{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1957581242046781955L;
	Integer id;
	String name;
	Map<String, Integer> counters;
	private OutputCollector collector;
	
	@Override
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		this.counters = new HashMap<String, Integer>();
		this.collector = collector;
		this.name = context.getThisComponentId();
		this.id = context.getThisTaskId();
		
	}

	@Override
	public void execute(Tuple input) {
		String str = input.getString(0);
		if(!counters.containsKey(str)){
			counters.put(str, 1);
			for(Map.Entry<String, Integer> entry:counters.entrySet()){
			System.out.println("====================>"+entry.getKey()+" : " + entry.getValue()+
			"=============================================>");
			}
		}else{
			Integer c = counters.get(str) +1;
			counters.put(str, c);
			for(Map.Entry<String, Integer> entry:counters.entrySet()){
			System.out.println("====================>"+entry.getKey()+" : " + entry.getValue()+
			"=============================================>");
		}
		}
		collector.ack(input);
	}

	@Override
	public void cleanup() {
		System.out.println(" -- Word Counter ["+ name + "-"+id +"]");
		
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}

}
