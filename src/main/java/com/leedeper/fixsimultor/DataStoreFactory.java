/*
 * Created by Leedeper on Sep 23, 2016.
 * All rights reserved.
 */
package com.leedeper.fixsimultor;
/**
 * 
 * @author Leedeper
 *
 */
public class DataStoreFactory implements IDataStoreFactory {
	private int max=0;
	public DataStoreFactory(int max){
		this.max=max;
	}
	@Override
	public IDataStore newDataStore() {
		return new DataStore(max);
	}

}

