/*
 * Created by Leedeper on Aug 21, 2016.
 * All rights reserved.
 */
package com.leedeper.fixsimultor;
/**
 * 
 * @author Leedeper
 *
 */
public interface ISession<M> {

	public String getName();
	public boolean send(M msg);
}

