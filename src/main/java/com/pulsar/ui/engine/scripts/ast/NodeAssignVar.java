package ui.engine.scripts.ast;


public class NodeAssignVar implements Node {
	
	public final Node target;
	public final Node value;
	
	public NodeAssignVar(Node t, Node v) {
		target = t;
		value = v;
	}
	
}
