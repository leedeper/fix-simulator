/*
 * Created by Leedeper on Aug 31, 2016.
 * All rights reserved.
 */
package com.leedeper.fixsimultor;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 
 * @author Leedeper
 *
 */
public class DefaultAction {

/*	public static IMessageIteratorFactory messageIteratorFactory(){
		
	}*/
	
	public static IMessageStrategyFactory messageStrategyFactory(){
		return new IMessageStrategyFactory(){
			@Override
			public IMessageStrategy getMessageStrategy() {
				return null;
			}
			
		};
	}
	
/*	public static IDataStoreFactory historyDataFactory(){
		
	}*/
	private static AtomicLong ID=new AtomicLong(10000);
	public static IIdCreater idCreater(){
		return new IIdCreater(){
			@Override
			public String create(Data data) {
				return String.valueOf(ID.getAndIncrement());
			}
			
		};
	}
}

