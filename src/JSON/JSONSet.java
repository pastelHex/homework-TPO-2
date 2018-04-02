package JSON;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

public class JSONSet extends JSONNode<LinkedHashSet<JSONNode>> {
	public JSONSet(String key) {
		super();
		this.key = key;
		this.value = new LinkedHashSet<JSONNode>();
	}

	public JSONSet(String key, LinkedHashSet<JSONNode> set) {
		super();
		this.key = key;
		this.value = set;
	}

	public void add_value(JSONNode el) {
		this.value.add(el);
	}

	@Override
	public String toString() {
		return key + ":: {" + value.toString() + "}\n";
	}

	@Override
	public JSONNode find_child(String child_name, int position_in_table) {
		// Iterator<JSONNode> it = value.iterator();
		JSONNode val;
		for (JSONNode node : value) {
			val = node.find_child(child_name, position_in_table);
			if (val != null) {
				return val;
			}
		}
		return null;
	}
	@Override
	public JNodeType get_node_type() {
		return JNodeType.SET;
	}
}
