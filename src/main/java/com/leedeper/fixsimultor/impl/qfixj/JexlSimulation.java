/*
 * Created by Leedeper on Sep 25, 2016.
 * All rights reserved.
 */
package com.leedeper.fixsimultor.impl.qfixj;

import org.apache.commons.jexl3.JexlBuilder;
import org.apache.commons.jexl3.JexlContext;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlExpression;
import org.apache.commons.jexl3.ObjectContext;

import com.leedeper.fixsimultor.Data;

import quickfix.Message;

/**
 * 
 * @author Leedeper
 *
 */
public class JexlSimulation  implements Simulation {
	private String expressionStr=null;
	private  JexlExpression expression=null;
	private JexlEngine jexl = new JexlBuilder().create();
	public JexlSimulation(String expressionStr){
		this.expressionStr=expressionStr;
	    // for example "getGroups(268)[0].isSetField(270)?getGroups(268)[0].getDouble(270).intValue()+2:'no268'";
	    //TODO I will simplify it can be as //268[0].isSetField(270)?//268[0]//270D.intValue()+2 : 'no270'"
	    expression = jexl.createExpression( expressionStr );
	}
	
	@Override
	public String next(Data<Message> data) {
	    JexlContext jc = new ObjectContext(jexl, data.getMessage());
	    Object o = expression.evaluate(jc);
	    if(o==null){
	    	return null;
	    }
	    
		return o.toString();
	}

	@Override
	public Simulation clone() {
		return new JexlSimulation(expressionStr);
	}

}

