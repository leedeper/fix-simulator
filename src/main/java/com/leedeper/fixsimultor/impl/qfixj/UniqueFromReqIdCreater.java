/*
 * Created by Leedeper on Sep 22, 2016.
 * All rights reserved.
 */
package com.leedeper.fixsimultor.impl.qfixj;

import com.leedeper.fixsimultor.Data;
import com.leedeper.fixsimultor.IIdCreater;
import com.leedeper.fixsimultor.SimulatorRuntimeException;

import quickfix.FieldNotFound;
import quickfix.Message;

/**
 * It's simple
 * @author Leedeper
 *
 */
public class UniqueFromReqIdCreater  implements IIdCreater<Message>{

	private int fieldId=0;
	public UniqueFromReqIdCreater(int fieldId){
		this.fieldId=fieldId;
	}
	@Override
	public String create(Data<Message> data) {
		try {
			return data.getMessage().getString(fieldId);
		} catch (FieldNotFound e) {
			throw new SimulatorRuntimeException(e);
		}
	}

}

