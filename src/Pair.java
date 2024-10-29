
public class Pair<K,V> {
	private K key;
    private V value;
    
    public Pair() {
    	this.key = null;
    	this.value = null;
    }

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

  
}

