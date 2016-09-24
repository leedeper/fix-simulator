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
public interface IDataStoreObject<M> {
	public int getTotal();
	public M[] getMessage(int number);
	public M getNewest();
	public void add(M message);
}

