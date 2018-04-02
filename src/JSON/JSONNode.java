package JSON;

public abstract class JSONNode<V> {
	protected String key;
	protected V value;
	protected int unique_node_number;
	private static int max_node_number;

	private static void node_numeration(JSONNode node) {
		max_node_number++;
		node.unique_node_number = max_node_number;
	}

	public JSONNode() {
		JSONNode.node_numeration(this);
	}
	abstract public JNodeType get_node_type();
	
	//not work
	abstract public <T extends JSONNode<V>> T find_child(String child_name, int position_in_table);
	abstract public void add_value(JSONNode node);
}
