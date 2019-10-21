// Copyright © 2018 xue.zeng@outlook.com
// Copyright © 2015 Alan A. A. Donovan · Brian W. Kernighan.
// Published Oct 26, 2015 in paperback and Nov 20 in e-book

// 1.3 做实验测量潜在低效的版本和使用了 strings.Join 的版本的运行时间差异
//（1.6 节讲解了部分 time 包，11.4节展示了如何写标准测试程序，以得到系统性的性能评测。）
package main

import (
	"fmt"
	"os"
	"strings"
	"time"
)

func echo1(args []string) string {
	printTime("echo1")

	var s, sep string
	for i := 1; i < len(args); i++ {
		s += sep + args[i]
		sep = " "
	}
	return s
}

func echo2(args []string) string {
	printTime("echo2")

	s, sep := "", ""
	for _, arg := range args[1:] {
		s += sep + arg
		sep = " "
	}
	return s
}

func echo3(args []string) string {
	printTime("echo3")
	return strings.Join(args[1:], " ")
}

func printTime(methName string) {
	start := time.Now()
	defer func() {
		fmt.Printf("%s: %v ns\n", methName, time.Since(start).Nanoseconds())
	}()
}

func main() {
	echo1(os.Args)
	echo2(os.Args)
	echo3(os.Args)
}
