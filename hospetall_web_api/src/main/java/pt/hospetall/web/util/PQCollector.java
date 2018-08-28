package pt.hospetall.web.util;

import java.util.PriorityQueue;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class PQCollector<E> implements Collector<E, PriorityQueue<E>, PriorityQueue<E>> {

	@Override
	public Supplier<PriorityQueue<E>> supplier() {
		return PriorityQueue::new;
	}

	@Override
	public BiConsumer<PriorityQueue<E>, E> accumulator() {

		return (pq, e) -> {
			if(e != null)
				pq.add(e);
		};
	}

	@Override
	public BinaryOperator<PriorityQueue<E>> combiner() {
		return (pq0, pq1) -> {
			if(pq0.isEmpty()) return pq1;
			if(pq1.isEmpty()) return pq0;
			pq1.addAll(pq0);
			return pq1;
		};
	}

	@Override
	public Function<PriorityQueue<E>, PriorityQueue<E>> finisher() {
		return (pq) -> pq;
	}

	@Override
	public Set<Characteristics> characteristics() {
		return null;
	}
}
