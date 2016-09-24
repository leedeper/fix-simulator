/*
 * Created by Leedeper on Aug 25, 2016.
 * All rights reserved.
 */
package com.leedeper.fixsimultor;
/**
 * 
 * @author Leedeper
 *
 */
public interface IDistributor<M> {
	public void send(M msg,Data<M> data);
}

