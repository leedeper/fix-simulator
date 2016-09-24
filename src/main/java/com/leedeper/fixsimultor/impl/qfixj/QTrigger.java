/*
 * Created by Leedeper on Aug 28, 2016.
 * All rights reserved.
 */
package com.leedeper.fixsimultor.impl.qfixj;

import com.leedeper.fixsimultor.AbstractTrigger;

import com.leedeper.fixsimultor.ITrigger;

import quickfix.Message;

/**
 * 
 * @author Leedeper
 *
 */
public class QTrigger extends AbstractTrigger<Message> {

	@Override
	public ITrigger<Message> whenMsgType(String... types) {
		this.when(new MsgTypeCondition(types));
		return this;
	}


}

