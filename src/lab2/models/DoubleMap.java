package lab2.models;

import com.sun.istack.internal.NotNull;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
}
