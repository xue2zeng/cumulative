package tree

import "fmt"

type Node struct {
	Value       int
	Left, Right *Node
}

func (node Node) Print() {
	fmt.Print(node.Value, " ")
}

func (node *Node) SetValue(value int) {
	if node == nil {
		fmt.Println("Setting Value to nil " +
			"node. Ignored.")
		return
	}
	node.Value = value
}

func CreateNode(value int) *Node {
	return &Node{Value: value}
}

func (node *Node) traverse() {
	if node == nil {
		return
	}

	node.Left.traverse()
	node.Print()
	node.Right.traverse()
}

func main() {
	var root Node

	root.SetValue(3)
	root.Left = &Node{}
	root.Right = &Node{5, nil, nil}
	root.Right.Left = new(Node)
	root.Left.Right = CreateNode( 2)

	root.Print()
	fmt.Println("##############")

	root.Right.Left.SetValue(4)
	root.Right.Left.Print()
	fmt.Println("##############")

	root.traverse()
	fmt.Println("##############")

	nodes := []Node {
		{3, nil, nil},
		{},
		{6, nil,&root},
	}

	fmt.Println(nodes)
}
