package graphs;

public class BadVertexException extends IllegalArgumentException {
	public BadVertexException(String tag) {
		super(tag);
	}
}
