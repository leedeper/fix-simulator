/*
 * Created by Leedeper on Sep 23, 2016.
 * All rights reserved.
 */
package com.leedeper.fixsimultor;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * 
 * @author Leedeper
 *
 */
public class DataStoreObject<M> implements IDataStoreObject<M> {
	private M request=null;
	private ArrayBlockingQueue<M> queue = null;
	private M last=null;
	public DataStoreObject(int maxSize){
		queue = new ArrayBlockingQueue<>(maxSize);
	}

	public int getTotal() {
		return queue.size();
	}


	public M[] getMessage(int number) {
		int size=queue.size();
		M t=null;
		if(number>=size){
			M[] ts = (M[]) Array.newInstance(t.getClass(), size);
			return queue.toArray(ts);
		}else{
			M[] ts = (M[]) Array.newInstance(t.getClass(), number);
			int fromInx=size-number;
			Iterator<M> it = queue.iterator();
			int i=0;
			int j=0;
			while(it.hasNext()){
				M m=it.next();
				i++;
				if(i>=fromInx){
					ts[j]=m;
					j++;
				}
			}
			return ts;
		}
	}



	public M getNewest() {
		return last;
	}


	public void add(M message) {
		while(!queue.offer(message)){
			queue.remove();
		}
		last=message;
	}
}

