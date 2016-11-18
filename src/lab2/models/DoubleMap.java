package lab2.models;

import com.sun.istack.internal.NotNull;

import java.util.*;

/**
 * Uses {@link LinkedHashMap}.
 * @param <K> key1
 * @param <L> key2
 * @param <V> value
 */
public class DoubleMap<K, L, V>{
	protected Map<K, Map<L, V>> map;

	public DoubleMap(){
		map = new LinkedHashMap<>();
	}

	public DoubleMap(DoubleMap doubleMap){
		Objects.requireNonNull(doubleMap);

		map = new LinkedHashMap<>(doubleMap.map);
	}

	public Map<L, V> get(K key1){
		return map.get(key1);
	}

	public V get(K key1, L key2){
		Map<L, V> subMap = map.get(key1);
		if (subMap == null){
			return null;
		}

		return subMap.get(key2);
	}

	public V put(K key1, L key2, V value){
		Map<L, V> subMap = map.get(key1);
		if (subMap == null){
			subMap = new LinkedHashMap<>();
			map.put(key1, subMap);
		}

		return subMap.put(key2, value);
	}

	public Map<K, Map<L, V>> getMap(){
		return map;
	}

	/**
	 * A copy (not a view).
	 * @return
	 */
	public Set<L> getKey2Set(){
		Set<L> key2Set = new LinkedHashSet<L>();

		map.forEach((key1, entry) -> {
			entry.forEach((key2, value) -> key2Set.add(key2));
		});

		return key2Set;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		DoubleMap<?, ?, ?> doubleMap = (DoubleMap<?, ?, ?>) o;

		return map.equals(doubleMap.map);

	}

	@Override
	public int hashCode() {
		return map.hashCode();
	}
}
