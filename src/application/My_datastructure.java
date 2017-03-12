package application;

import java.util.NoSuchElementException;

public class My_datastructure<T extends Vertex> {

	private class Node {
		public Node(T value) {
			this.value = value;
			this.left = null;
			this.right = null;
		}

		public T value;
		private Node left;
		private Node right;
	}

	public Node root;

	public My_datastructure() {
		this.root = null;

	}

	public T peek() {
		return root.value;
	}

	public boolean isEmpty() {
		return root == null;

	}

	public boolean check(T tmp) {
		return check(root, tmp);

	}

	private boolean check(Node curr, T tmp) {

		if (curr != null) {
			if ((curr.value.x == tmp.x) && (curr.value.y == tmp.y)){
				tmp.f=curr.value.f;
				tmp.g=curr.value.g;
				return true;}
			return check(curr.left, tmp)||check(curr.right, tmp);
			
		}

		return false;

	}

	public void insert(T data) {

		Node tmp = new Node(data);
		if (root == null) {
			root = tmp;
		} else {
			Node curr = root;
			while (true) {

				if (data.f < curr.value.f) {
					if (curr.left == null) {
						curr.left = tmp;
						break;
					} else {
						curr = curr.left;
					}
				} else {
					if (curr.right == null) {
						curr.right = tmp;
						break;
					} else {
						curr = curr.right;
					}
				}
			}
		}
	}

	private Node findIOP(Node node) {
		Node curr;
		for (curr = node; curr.right != null; curr = curr.right)
			;
		return curr;
	}

	public T pop() {
		if (root == null)
			return null;
		T tmp;
		Node my_find = root;
		while (my_find.left != null)
			my_find = my_find.left;
		tmp = my_find.value;
		remove(tmp);
		return tmp;

	}

	public void remove(T key) {
		root = remove(root, key);
	}

	private Node remove(Node curr, T key) {
		if (curr == null) {
			return curr;
		}

		if (curr.value.f > key.f) {
			curr.left = remove(curr.left, key);
		} else if (curr.value.f < key.f) {
			curr.right = remove(curr.right, key);
		} else if (curr.left != null && curr.right != null) {
			curr.value = findIOP(curr.left).value;

			curr.left = remove(curr.left, curr.value);
		} else if (curr.left == null) {
			curr = curr.right;
		} else {
			curr = curr.left;
		}
		return curr;
	}

	public void display(Node curr) {

		if (curr != null) {
			display(curr.left);
			System.out.println("" + curr.value.x + " " + curr.value.y + " " + curr.value.f);
			display(curr.right);
		}
	}

}
