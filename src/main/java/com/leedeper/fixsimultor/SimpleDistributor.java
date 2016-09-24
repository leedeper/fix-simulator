/*
 * Created by Leedeper on Sep 24, 2016.
 * All rights reserved.
 */
package com.leedeper.fixsimultor;
/**
 * send where come from
 * @author Leedeper
 *
 */
public class SimpleDistributor<M> implements IDistributor<M>{

	@Override
	public void send(M msg, Data<M> data) {
		data.getFromSession().send(msg);
		data.addTargetSession(data.getFromSession());
	}

}

