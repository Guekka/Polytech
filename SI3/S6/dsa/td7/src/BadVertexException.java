package graphs.graph;

public class BadVertexException extends IllegalArgumentException {
	public BadVertexException(String tag) {
		super(tag);
	}
}
