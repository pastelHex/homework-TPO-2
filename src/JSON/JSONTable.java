package JSON;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class JSONTable extends JSONNode<List<JSONSet>> {
	public JSONTable(String key) {
		super();
		this.key = key;
		this.value = new ArrayList<JSONSet>();
	}

	public JSONTable(String key, List<JSONSet> list) {
		super();
		this.key = key;
		this.value = list;
	}

	public void add_value(JSONNode el) {
		this.value.add((JSONSet) el);
	}

	@Override
	public String toString() {
		return this.key + "::" + "[" + this.value.toString() + "]\n";
	}

	@Override
	public <T extends JSONNode<List<JSONSet>>> T find_child(String child_name, int position_in_table) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public JNodeType get_node_type() {
		return JNodeType.TABLE;
	}
}
