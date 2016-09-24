/*
 * Created by Leedeper on Aug 30, 2016.
 * All rights reserved.
 */
package com.leedeper.fixsimultor;
/**
 * 
 * @author Leedeper
 *
 */
public interface IMessageStrategyFactory<M> {
	public IMessageStrategy<M> getMessageStrategy();
}

