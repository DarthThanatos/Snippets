package my.kalkulator;
import java.util.*;

public class Oper {
	
  char oper;
  boolean changed = false;
  static node root = null;
  static node actual = null;
  public class node{
	  String value;
	  node left;
	  node right;
	  node parent;
	  public node (String key){
		  value = key;
		  left = null;
		  right = null;
	  }
  }

  public void addDiv(String part1, String oper, String part2){
	  if (root == null ){
		  root = new node (oper);
		  root.parent = null;
		  root.left = new node (part1);
		  root.left.parent = root;
		  root.right = new node (part2);
		  root.right.parent = root;
		  actual = root.right;
		  return;
	  }
	  if(!actual.parent.value.equals(oper)){
		  actual.parent.right.value = oper;
		  actual.left = new node(part1);
		  actual.left.parent = actual;
		  actual.right = new node(part2);
		  actual.right.parent = actual;
		  actual = actual.right;
		  
	  }
	  else{
		  node tmp = new node(oper);
		  tmp.parent = actual.parent.parent;
		  tmp.parent.right = tmp;
		  actual.parent.parent = tmp;
		  tmp.left = actual.parent;
		  tmp.right = new node(part2);
		  tmp.left.parent = tmp;
		  tmp.right.parent = tmp;
		  actual = tmp.right;
	  }
  }
  
  
  public void addMulti(String part1, String oper, String part2){
	  if (root == null){
		  root = new node (oper);
		  root.parent = null;
		  root.left = new node (part1);
		  root.left.parent = root;
		  root.right = new node (part2);
		  root.right.parent = root;
		  actual = root.right;
	  }
	  else{
		  actual.value = oper;
		  actual.left = new node(part1);
		  actual.left.parent = actual;
		  actual.right = new node(part2);
		  actual.right.parent = actual;
		  actual = actual.right;
	  }
  }
  
  public void addPlusMinus(String part1, String oper, String part2){
	  if (root == null){
		  root = new node (oper);
		  root.parent = null;
		  root.left = new node (part1);
		  root.left.parent = root;
		  root.right = new node (part2);
		  root.right.parent = root;
		  actual = root.right;
	  }
	  else{
		  actual = new node (oper);
		  actual.left = root;
		  root.parent = actual;
		  actual.right = new node (part2);
		  actual.right.parent = actual;
		  root = actual;
		  actual = actual.right;
	  }
  }
  public static double math (node p){
	  if (root == null )return 0;
	  switch(p.value.charAt(0)){
	  case '+' : return  math(p.left) + math(p.right);  
	  case '-' : return math(p.left) - math(p.right); 
	  case '*' : return math(p.left) * math(p.right);
	  case '/' : return math(p.left) / math(p.right);
	  default : return Double.parseDouble(p.value);
	  }
  }
  
  public static int spacesAmount(String seq){
	  int res = 0;
	  for (int i = 0; i< seq.length(); i++) if (seq.charAt(i) == ' ') res++;
	  return res;
  }
  
  public static String cut(String seq){
	  if (spacesAmount(seq) == 0) return seq;
	  Oper o = new Oper();
	  boolean act = true;
	  StringTokenizer st = new StringTokenizer (seq);
	  try{
		  String part1 = st.nextToken();
		  String oper = st.nextToken();
		  String part2 = st.nextToken();
		  while (act){
			  switch (oper.charAt(0)){
		  		case '*': o.addMulti(part1,oper,part2); break;
		  		case '/': o.addDiv(part1,oper,part2);break;
		  		case '-': o.addPlusMinus(part1,oper,part2);break;
		  		case '+': o.addPlusMinus(part1,oper,part2);break;
		  		default: throw new Exception();
			  }
			  
			  if(st.hasMoreTokens()){
				  part1 = part2;
				  oper = st.nextToken();
				  part2 = st.nextToken();
			  }
			  else act = false;
		  
		  }
	  }catch(Exception e){
		  root = null;
		  return "Invalid format data";
		  }
	  
	  String res = Double.toString(math(root));
	  root = null;
	  System.gc();
	  return res;
  }
  
  public static void preorder(node p){
	  if (p==null)return;
	  if (p.left != null) preorder(p.left);
	  if (p.right != null) preorder(p.right);
	  System.out.print(p.value + " ");
  }
 
  
}