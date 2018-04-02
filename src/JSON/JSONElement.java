package JSON;

public class JSONElement extends JSONNode<JSONText> {

	public JSONElement(String key) {
		super();
		this.key = key;
		this.value = new JSONText();
	}

	public JSONElement(String key, String value) {
		super();
		this.key = key;
		this.value = new JSONText(value);
	}

	public void add_value(JSONNode value) {
		this.value = (JSONText) value;
	}

	@Override
	public String toString() {
		return key + "::" + value.toString() + "\n";
	}

	@Override
	public JSONText find_child(String child_name, int position_in_table) {
		return this.key.equals(child_name) ? this.value : null;
	}

	@Override
	public JNodeType get_node_type() {
		return JNodeType.ELEMENT;
	}

}
