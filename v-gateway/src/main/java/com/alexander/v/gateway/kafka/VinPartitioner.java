package com.alexander.v.gateway.kafka;

import kafka.producer.Partitioner;
import kafka.utils.VerifiableProperties;

import org.apache.log4j.Logger;

public class VinPartitioner implements Partitioner{
	
	private final Logger logger = Logger.getLogger(VinPartitioner.class);
	
	public VinPartitioner(){
		
	}

	public VinPartitioner(VerifiableProperties verifiableProperties){
		
	}
	 /**
	 *
	 * @see kafka.producer.Partitioner#partition(Object, int)
	 */
	@Override
	public int partition(Object key, int partitionSize) {
		int hCode = key.hashCode();
		int partitionIndex = Math.abs(hCode)%partitionSize;
		return partitionIndex;
	}

}
