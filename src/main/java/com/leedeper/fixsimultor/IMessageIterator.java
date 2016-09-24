/*
 * Created by Leedeper on Aug 25, 2016.
 * All rights reserved.
 */
package com.leedeper.fixsimultor;


/**
 * Define how to create message
 * @author Leedeper
 *
 */
public interface IMessageIterator<M>  {
    public boolean hasNext();
    public M next(Data<M> data);
}

