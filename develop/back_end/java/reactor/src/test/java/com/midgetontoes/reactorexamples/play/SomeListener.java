package com.midgetontoes.reactorexamples.play;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Victor J Grazi
 */
//source: https://github.com/vgrazi/rxjava-snippets/
public abstract class SomeListener {
	private static final AtomicInteger COUNTER = new AtomicInteger(1);
	private final int ID;
	public SomeListener()
	{
		ID = COUNTER.getAndIncrement();
	}

	public abstract void priceTick(PriceTick event);
	public abstract void error(Throwable throwable);

	@Override
	public String toString() {
		return String.format("Listener ID:%d:%s", ID, super.toString());
	}
}