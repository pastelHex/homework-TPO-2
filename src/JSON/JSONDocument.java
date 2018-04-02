package JSON;

public class JSONDocument extends JSONSet {

	public JSONDocument(String key) {
		super(key);
		// TODO Auto-generated constructor stub
	}

	@Override
	public JNodeType get_node_type() {
		return JNodeType.DOCUMENT;
	}
	public JSONSet get_content() {
		JSONSet set = (JSONSet) value.toArray()[0];
		return set;
	}
}
