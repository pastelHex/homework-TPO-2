package JSON;

public class JSONText extends JSONNode<String> {
	public JSONText(String text) {
		super();
		this.value = text;
	}

	public JSONText() {
		super();
		this.value = null;
	}

	@Override
	public void add_value(JSONNode node) {
		// TODO Auto-generated method stub
	}

	@Override
	public String toString() {
		return this.value;
	}

	@Override
	public <T extends JSONNode<String>> T find_child(String child_name, int pos) {
		return null;
	}
	@Override
	public JNodeType get_node_type() {
		return JNodeType.TEXT;
	}
}
