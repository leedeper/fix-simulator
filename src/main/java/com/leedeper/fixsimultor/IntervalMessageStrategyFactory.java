/*
 * Created by Leedeper on Sep 24, 2016.
 * All rights reserved.
 */
package com.leedeper.fixsimultor;
/**
 * 
 * @author Leedeper
 *
 */
public class IntervalMessageStrategyFactory<M> implements IMessageStrategyFactory{

	private long intervalInMillisecond=0;
	public IntervalMessageStrategyFactory(long intervalInMillisecond){
		this.intervalInMillisecond=intervalInMillisecond;
	}
	@Override
	public IMessageStrategy<M> getMessageStrategy() {
		return new IntervalMessageStrategy<M>(intervalInMillisecond);
	}

}

