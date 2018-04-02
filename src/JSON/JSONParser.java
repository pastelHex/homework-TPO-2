package JSON;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class JSONParser {

	private static JSONParser instancja;
	private static int i;

	public static JSONParser create_JSONParser() {
		/*if (instancja == null) {
			instancja = new JSONParser();
		}*/
		return new JSONParser();
	};

	public static void generate_document(String json_txt, JSONNode parent_node) {
		get_json_node_from_string(json_txt, parent_node);
		i = 0;
	}

	private static JSONNode get_json_node_from_string(String json_txt, JSONNode parent_node) {

		String key = "";
		StringBuilder sb = new StringBuilder();
		while (json_txt.length() > 0 && i < json_txt.length()) {
			if (json_txt.charAt(i) == ',') {
				i++;
				System.out.print(",");
			}
			if (json_txt.charAt(i) == '"') {
				i++;
				// System.out.print("\"");
				while (json_txt.charAt(i) != '\"') {
					System.out.print("" + json_txt.charAt(i));
					sb.append(json_txt.charAt(i));
					i++;
				}
				i++;
				key = sb.toString();
				sb = new StringBuilder();
			}

			if (json_txt.charAt(i) == ':') {
				i++;
				System.out.print(":");
				if (json_txt.charAt(i) == '"') {
					// textvalue
					// System.out.print("\"");
					i++;
					while (json_txt.charAt(i) != '"') {
						sb.append(json_txt.charAt(i));
						System.out.print("" + json_txt.charAt(i));
						i++;
					}
					i++;
					JSONElement el = new JSONElement(key, sb.toString());
					parent_node.add_value(el);
					key = "";
					sb = new StringBuilder();
				}
				if (json_txt.charAt(i) == '[') {
					// tablevalue
					System.out.print("[");
					i++;
					JSONTable table = new JSONTable(key);
					table = (JSONTable) get_json_node_from_string(json_txt, table);
					parent_node.add_value(table);
					key = new String("");
				}
				if (json_txt.charAt(i) == '{') {
					// setvalue
					System.out.print("{");
					i++;
					JSONSet set = new JSONSet(key);
					set = (JSONSet) get_json_node_from_string(json_txt, set);
					parent_node.add_value(set);
					key = new String("");
				}
				if (Character.isDigit(json_txt.charAt(i)) || json_txt.charAt(i) == '-') {
					sb = new StringBuilder();
					do {
						System.out.print(json_txt.charAt(i));
						sb.append(json_txt.charAt(i));
						i++;
					} while (Character.isDigit(json_txt.charAt(i)) || json_txt.charAt(i) == '.');
					JSONElement txt = new JSONElement(key, sb.toString());
					parent_node.add_value(txt);
					key = new String("");
					sb = new StringBuilder();
				}
				if (Character.isAlphabetic(json_txt.charAt(i))) {
					sb = new StringBuilder();
					do {
						System.out.print(json_txt.charAt(i));
						sb.append(json_txt.charAt(i));
						i++;
					} while (Character.isAlphabetic(json_txt.charAt(i)));
					JSONElement txt = new JSONElement(key, sb.toString());
					parent_node.add_value(txt);
					key = new String("");
					sb = new StringBuilder();
				}
			}
			if (json_txt.charAt(i) == '{') {
				// setvalue
				System.out.print("{");
				i++;
				JSONSet set = new JSONSet(key);
				// System.out.println(json_txt.substring(i));
				set = (JSONSet) get_json_node_from_string(json_txt, set);
				parent_node.add_value(set);
				key = new String("");
			}

			// if nawiasy zamykajace
			// }
			if (i < json_txt.length()) {
				if (json_txt.charAt(i) == '}' || json_txt.charAt(i) == ']') {
					// System.out.print(json_txt.charAt(i) + "\n");
					i++;
					return parent_node;
				}
			} else
				return parent_node;
		}

		return null;
	}

	public static JSONNode get_node(JSONNode parent, String path) {// path = varfiable1/variable2/variable3@2/... @ is
																	// for
		// tables position
		/*
		 * System.out.println("get_node: "+path);
		 * System.out.println(parent.get_node_type()); System.out.println(parent);
		 */
		String[] tab = path.split("/");
		if (tab.length > 0) {
			String name = tab[0];
			path = path.replaceFirst(name + "/", "");
			switch (parent.get_node_type()) {
			case TEXT:
				return parent;
			case ELEMENT:
				return get_node(((JSONNode) parent.value), path);
			case SET:
				JSONNode x = null;
				for (JSONNode n : ((JSONSet) parent).value) {
					if (n.key.equals(name))
						x = n;
				}
				// x=((JSONSet)parent).find_child(name, 0);
				return get_node(x, path);
			case TABLE:
				String[] pos = name.split("@");
				JSONNode z = ((JSONTable) parent).value.get(Integer.parseInt(pos[1]));
				return get_node(z, pos[0] + "/" + path);
			case DOCUMENT:
				return get_node(((JSONDocument) parent).get_content(), path);
			default:
				return null;
			}

		} else
			return parent;
	}

	public static String preety_print(JSONNode node, String indent) {
		StringBuilder s = new StringBuilder();
		// indent += indent;
		if (node.get_node_type() == JNodeType.SET) {
			s.append(indent + node.key + ":\n");
			for (JSONNode n : ((HashSet<JSONNode>) node.value)) {
				s.append(preety_print(n, indent + indent));
			}
		}
		if (node.get_node_type() == JNodeType.TABLE) {
			s.append(indent + node.key + ":\n");
			for (JSONNode n : ((ArrayList<JSONNode>) node.value)) {
				s.append(preety_print(n, indent + indent));
			}
		}
		if (node.get_node_type() == JNodeType.ELEMENT) {
			s.append(indent + node.key + "");
			s.append(preety_print((JSONNode) node.value, indent));
		}
		if (node.get_node_type() == JNodeType.TEXT) {
			s.append("\t" + node.toString() + "\n");
		}
		if (node.get_node_type() == JNodeType.DOCUMENT) {
			s.append(indent + node.key + ":");
			for (JSONNode n : ((HashSet<JSONNode>) node.value)) {
				s.append(preety_print(n, indent));
			}
		}
		return s.toString();
	}
}
