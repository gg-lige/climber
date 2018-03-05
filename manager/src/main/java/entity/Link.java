package entity;

public class Link {

	private long source;
	private long target;

	public Link(long source, long target) {
		this.source = source;
		this.target = target;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (source ^ (source >>> 32));
		result = prime * result + (int) (target ^ (target >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Link other = (Link) obj;
		if (source != other.source)
			return false;
		if (target != other.target)
			return false;
		return true;
	}

	public long getSource() {
		return source;
	}

	public long getTarget() {
		return target;
	}
}
