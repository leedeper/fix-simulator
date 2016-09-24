/*
 * Created by Leedeper on Sep 23, 2016.
 * All rights reserved.
 */
package com.leedeper.fixsimultor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * 
 * @author Leedeper
 *
 */
public class DataStore<M> implements IDataStore<M> {
	
	private List<IDataStoreObject<M>> all=new ArrayList<>();
	private int maxSize=0;
	public DataStore(int maxSize){
		this.maxSize=maxSize;
	}
	public void add(M request){
		IDataStoreObject<M> store=new DataStoreObject<>(maxSize);
		all.add(store);
	}
	public Iterator<IDataStoreObject<M>> getAll(){
		return all.iterator();
	}
}

