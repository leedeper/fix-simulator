/*
 * Created by Leedeper on Aug 31, 2016.
 * All rights reserved.
 */
package com.leedeper.fixsimultor;
/**
 * 
 * @author Leedeper
 *
 */
public interface IDataStoreFactory<M> {
	public IDataStore<M> newDataStore();
}

