package main

import (
	"fmt"
	"math"
	"reflect"
	"runtime"
)

func eval(a, b int, opt string) (int, error) {
	switch opt {
	case "+":
		return a + b, nil
	case "-":
		return a - b, nil
	case "*":
		return a * b, nil
	case "/":
		q, _ := div(a, b)
		return q, nil
	default:
		return 0, fmt.Errorf("unsupported operation: %s", opt)
	}
}

func div(a, b int) (q, r int) {
	return a / b, a % b
}

func apply(opt func(int, int) int, a, b int) int {
	p := reflect.ValueOf(opt).Pointer()
	optName := runtime.FuncForPC(p).Name()
	fmt.Printf("Calling funtion %s with args "+"(%d, %d)\n", optName, a, b)
	return opt(a, b)
}

func sum(numbers ...int) int {
	s := 0
	for i := range numbers {
		s += numbers[i]
	}
	return s
}

func main() {
	fmt.Println(eval(3, 5, "/"))

	fmt.Println(eval(3, 5, "×"))

	q, r := div(13, 3)
	fmt.Println(q, r)

	fmt.Println(apply(func(a int, b int) int {
		return int(math.Pow(float64(a), float64(b)))
	}, 3, 4))

	fmt.Println(sum(1, 2, 3))
}
