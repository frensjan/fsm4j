package nl.frensjan.fsm4j;

import java.util.concurrent.atomic.AtomicReference;

public class Machine<S extends State> {
	protected AtomicReference<S> current = new AtomicReference<>();

	public void transition(S to) {
		S from = current.getAndSet(to);
		exitAndEnter(from, to);
	}

	public boolean transition(S from, S to) {
		boolean changed = current.compareAndSet(from, to);

		if (changed) {
			exitAndEnter(from, to);
		}

		return changed;
	}

	private void exitAndEnter(S from, S to) {
		if (from != null) {
			from.exit(to);
		}

		if (to != null) {
			to.enter(to);
		}
	}
}
