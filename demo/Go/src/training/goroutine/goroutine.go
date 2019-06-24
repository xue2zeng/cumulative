package main

import (
	"fmt"
	"time"
	"runtime"
)

func main() {
	for i := 0; i < 10 ; i++ {
		go func(i int) {
			fmt.Printf("hello go routine %d\n", i)
		}(i)
	}
	time.Sleep(time.Microsecond)

	fmt.Println("###########################")

	var a [10]int
	for j := 0; j < 10 ; j++ {
		go func(j int) {
			for{
				a[j]++
				runtime.Gosched()
			}
		}(j)
	}
	time.Sleep(time.Microsecond)
	fmt.Println(a)
}
