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
public interface ICondition<M> {
	public static final ICondition   LOGIN=new LogCondition<>();
	public static final ICondition   LOGOUT=new LogCondition<>();
	public boolean match(M msg);
	
	public class LogCondition<M> implements ICondition<M> {
		@Override
		public boolean match(M msg) {
			throw new SimulatorRuntimeException("Pls don't use this method to check. Using instanceof ");
		}

	}
}

