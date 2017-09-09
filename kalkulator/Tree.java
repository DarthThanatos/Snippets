package my.kalkulator;

public class Tree {
	static node root = null;
	//-----------------------------
	public class node{
		int value;
		node left;
		node right;
		node parent;
		public node(int key){
			value = key;
			left = null;
			right = null;
			parent = null;
		}
	}
	//------------------------------
	public void add (int key, node p){
		if (root==null){
			root = new node(key);
			return;
		}
		if (p.value>=key)
			if(p.left!=null)
				add(key,p.left);
			else{
				p.left = new node(key);
				p.left.parent = p;
				return;
			}
		if (p.value<key)
			if(p.right!=null)
				add(key,p.right);
			else{
				p.right = new node(key);
				p.right.parent = p;
				return;
			}
	}
	
	public void preorder(node p){
		if (p==null) return;
		if (p.left!=null) preorder(p.left);
		if (p.right!=null) preorder(p.right);
		System.out.print(p.value + " ");
	}
	
	public void insert(int key) {       
		if(root == null)
		root = new node(key);
		else {
		node actual = root;
		node parent = null;
		while(actual != null) {
		parent = actual;               
		actual = (actual.value > key) ? actual.left : actual.right;                 
		}
		if(parent.value > key) {
		parent.left = new node(key);
		parent.left.parent = parent;
		}
		else {
		parent.right = new node(key);
		parent.right.parent = parent;
		}
		}              
		}     
	
	public static void main(String [] argv){
		Tree t = new Tree();
		for (int i = 0; i < 7; i++){
			int key = (int)(Math.random()*100);
			System.out.print(key + " ");
			t.add(key,root);

		}
		System.out.println();
		t.preorder(root);
	}
}
