package util;

public class Pair<K, V> {

    private final K element0;
    private final V element1;

    public static <K, V> Pair<K, V> createPair(K element0, V element1) {
        return new Pair<K, V>(element0, element1);
    }

    public Pair(K element0, V element1) {
        this.element0 = element0;
        this.element1 = element1;
    }

    public K getElement0() {
        return element0;
    }

    public V getElement1() {
        return element1;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((element0 == null) ? 0 : element0.hashCode());
		result = prime * result + ((element1 == null) ? 0 : element1.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Pair) {
			if (this.getElement0().equals(((Pair) obj).getElement0())){
				if (this.getElement1().equals(((Pair) obj).getElement1())){
					return true;
				}
			}
			return false;
		} else {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Pair other = (Pair) obj;
			if (element0 == null) {
				if (other.element0 != null)
					return false;
			} else if (!element0.equals(other.element0))
				return false;
			if (element1 == null) {
				if (other.element1 != null)
					return false;
			} else if (!element1.equals(other.element1))
				return false;
			return true;
		}
	}
}
