/*
 * Created by Leedeper on Sep 23, 2016.
 * All rights reserved.
 */
package com.leedeper.fixsimultor.impl.qfixj;

import java.util.HashSet;
import java.util.Set;

import com.leedeper.fixsimultor.ICondition;
import com.leedeper.fixsimultor.SimulatorRuntimeException;

import quickfix.FieldNotFound;
import quickfix.Message;

/**
 * 
 * @author Leedeper
 *
 */
public class MsgTypeCondition implements ICondition<Message> {
	private Set<String> types=new HashSet<String> ();
	public MsgTypeCondition(String ...type){
		for(String t:type){
			types.add(t);
		}

	}
	@Override
	public boolean match(Message msg) {
		String type;
		try {
			type = msg.getHeader().getString(35);
		} catch (FieldNotFound e) {
			throw new SimulatorRuntimeException(e);
		}
		return types.contains(type);
	}

}

