/*
 * Created by Leedeper on Sep 22, 2016.
 * All rights reserved.
 */
package com.leedeper.fixsimultor.impl.qfixj;

import static org.junit.Assert.*;

import org.junit.Test;

import quickfix.Message;

/**
 * 
 * @author Leedeper
 *
 */
public class MessageIteratorTest {

	@Test
	public void test() {
		Message msg=new Message();
		msg.getHeader().setString(35, "W");
		msg.setField(44, new SimulationField(44,new OrderEnumSimulation("a","b","c")));
		MessageIterator mi=new MessageIterator(msg,2);
		while(mi.hasNext()){
			System.out.println(mi.next(null));
		}
		
	 
	}

}

