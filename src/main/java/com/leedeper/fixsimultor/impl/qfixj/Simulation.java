/*
 * Created by Leedeper on Sep 22, 2016.
 * All rights reserved.
 */
package com.leedeper.fixsimultor.impl.qfixj;

import com.leedeper.fixsimultor.Data;

import quickfix.Message;

/**
 * 
 * @author Leedeper
 *
 */
public interface Simulation {
	
	public String next(Data<Message> data);
	public Simulation clone();
	
}

