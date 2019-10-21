package main

import (
	"fmt"
	"os"
	"strings"
	"time"
)

func main() {
	echo1()
	echo2()
	echo3()
}

func echo1() {
	var startTime = time.Now()
	var s, sep string
	fmt.Println(os.Args[0])
	for i := 1; i < len(os.Args); i++ {
		s += sep + os.Args[i]
		sep = " "
	}
	fmt.Println(s)
	fmt.Println("echo1 time: ", time.Now().Sub(startTime))
}

func echo2() {
	var startTime = time.Now()
	//s, sep := "", ""
	//for _, arg := range os.Args[1:] {
	for index, arg := range os.Args[1:] {
		fmt.Printf("%d: %s \n", index, arg)
	}
	fmt.Println("echo2 time: ", time.Now().Sub(startTime))
}

func echo3() {
	var startTime = time.Now()
	fmt.Println(strings.Join(os.Args[1:], " "))
	fmt.Println("echo3 time: ", time.Now().Sub(startTime))
}
