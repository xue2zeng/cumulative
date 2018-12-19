package main

import "fmt"
// 闭包
func adder() func(v int) int {
	sum := 0
	return func(v int) int {
		sum += v
		return sum
	}
}

// 正统函数式编程
type iAdder func(int) (int, iAdder)

func adder2(base int) iAdder {
	return func(v int) (int, iAdder) {
		return base + v, adder2(base + v)
	}
}

func main() {
	a := adder()
	for i := 0; i < 10; i++ {
		fmt.Printf("0 + 1 + .... + %d = %d\n", i, a(i))
	}

	fmt.Println("####################################")

	b := adder2(0)
	for j := 0; j < 10; j++ {
		var s int
		s, b = b(j)
		fmt.Printf("0 + 1 + .... + %d = %d\n", j, s)
	}
}
