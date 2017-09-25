package pFactorial;

public class QList {
	
	class Node{
		private Node next;
		private int v;
		public Node(int v){
			this.v = v;
			this.next = null;
		}
	}
	
	private Node head, tail;
	public QList(){
		this.tail = new Node(0);
		this.head = tail;
	}
	
	public void addFirst(int v){
		Node p = new Node(v);
		p.next = this.head;
		this.head = p; 
	}
	
	public void addLast(int v){
		this.tail.v = v;
		Node p = new Node(0);
		this.tail.next = p;
		this.tail = p;
	}
	
	public void list(){
		for (Node p = this.head;p!=this.tail; p=p.next ) System.out.print(p.v + " ");
		System.out.print("\n");
	}
	
	private QList reverseRec(Node p){
		if (p == this.tail) return new QList();
		else {
			QList q = reverseRec(p.next);
			q.addLast(p.v);
			return q;
		}
	}
	public QList reverse(){
		return reverseRec(this.head);
	}
	public int size(){
		int res = 0;
		for(Node p = this.head; p!=this.tail;p = p.next ) res ++;
		return res;
	}
	public void changeAt(int index,int v){
		Node p = this.head;
		for (int i =0; i<index; i++)  p = p.next;
		p.v = v;
	}
	public int[] toArray(){
		int [] res = new int[this.size()];
		int i =0;
		for (Node p = this.head; p != this.tail; p = p.next, i++) res[i] = p.v;
		return res;
	}
}
