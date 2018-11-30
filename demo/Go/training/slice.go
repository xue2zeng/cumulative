package main

import "fmt"

func updateSlice(s []int) {
	s[0] = 100
}

func main() {
	arry := [...]int{0, 1, 2, 3, 4, 5, 6, 7, 8, 9}

	fmt.Println("arry[2:8] = ", arry[2:8])
	fmt.Println("arry[:6] = ", arry[:6])
	s1 := arry[4:]
	fmt.Println("s1 = ", s1)
	s2 := arry[:]
	fmt.Println("s2 = ", s2)

	fmt.Println("After updateSlice(s1)")
	updateSlice(s1)
	fmt.Println(s1)
	fmt.Println(arry)

	fmt.Println("After udateSlice(s2)")
	updateSlice(s2)
	fmt.Println(s2)
	fmt.Println(arry)

	fmt.Println("Extending slice")
	arry[0], arry[4] = 0, 4
	fmt.Println("arry = ", arry)
	s1 = arry[2:6]
	s2 = s1[3:5]
	fmt.Printf("s1=%v, len(s1)=%d, cap(s1)=%d\n", s1, len(s1), cap(s1))
	fmt.Printf("s2=%v, len(s1)=%d, cap(s1)=%d\n", s2, len(s2), cap(s2))
}
