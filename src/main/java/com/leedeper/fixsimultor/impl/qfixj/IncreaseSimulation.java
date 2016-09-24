/*
 * Created by Leedeper on Sep 22, 2016.
 * All rights reserved.
 */
package com.leedeper.fixsimultor.impl.qfixj;

import java.math.BigDecimal;

import com.leedeper.fixsimultor.Data;
import com.leedeper.fixsimultor.SimulatorRuntimeException;

import quickfix.Message;

/**
 * 
 * @author Leedeper
 *
 */
public class IncreaseSimulation implements Simulation {
	
	private enum Type{
		DOUBLE,LONG
	}
	private Type type=null;
	private BigDecimal begin=null;
	private BigDecimal end=null;
	private BigDecimal step=null;
	private BigDecimal curr=null;
	public IncreaseSimulation(String from){
		this(from,null,null);
	}
	public IncreaseSimulation(String from,String to,String step){
		step=getStep(from,to,step);
		setType(from,to,step);
		begin=new BigDecimal(from);
		if(to!=null){
			end=new BigDecimal(to);
		}
		this.step=new BigDecimal(step);
	}
	private void setType(String from,String to,String step){
		if(isDecimal(from) 
				|| (to!=null && isDecimal(to))
				|| isDecimal(step)){
			type=Type.DOUBLE;
		}else{
			type=Type.LONG;
		}
	}

	private String getStep(String from,String to,String step){
		if(step==null){
			if(to==null){
				return getPrecision(from);
			}else{
				String f=getPrecision(from);
				String t=getPrecision(to);
				return tran(f)-tran(t)<0?f:t;
			}
		}else{
			if(to!=null && abs(from,to)<tran(step)){
				throw new SimulatorRuntimeException("step > abs(from-to), it's wrong");
			}
			return step;
		}
	}
	private String getPrecision(String str){
		if(isDecimal(str)){
			int precision=str.split("\\.")[1].length();
			return String.valueOf(1/Math.pow(10,precision));
		}else{
			return "1";
		}
	}
	private double abs(String from,String to){
		return Math.abs(tran(from)-tran(to));
	}
	private double tran(String str){
		return Double.parseDouble(str);
	}
	private boolean isDecimal(String str){
		return str.indexOf('.')>0;
	}
	@Override
	public String next(Data<Message> data) {
		if(curr==null){
			curr=begin;
		}else{
			curr=curr.add(step);
			if(end!=null && curr.compareTo(end)>0){
				curr=begin;
			}
		}
		if(type==Type.DOUBLE){
			return String.valueOf(curr.doubleValue());
		}
		return String.valueOf(curr.longValue());
	}
	
    @Override
    public Simulation clone() {
    	IncreaseSimulation clone=new IncreaseSimulation("1");
    	clone.type=this.type;
    	clone.begin=this.begin;
    	clone.end=this.end;
    	clone.step=this.step;
    	return clone;
    }
}

