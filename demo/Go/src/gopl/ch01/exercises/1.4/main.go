// Copyright © 2018 xue.zeng@outlook.com
// Copyright © 2015 Alan A. A. Donovan · Brian W. Kernighan.
// Published Oct 26, 2015 in paperback and Nov 20 in e-book

// 1.4 修改dup2，出现重复的行时打印文件名称
package main

import (
	"bufio"
	"fmt"
	"os"
)

// Run command:
// go run main.go data1.txt data2.txt
func main() {
	counts := make(map[string]int)
	countsFiles := make(map[string][]string)
	files := os.Args[1:]
	if len(files) == 0 {
		countLines(os.Stdin, counts, countsFiles)
	} else {
		for _, arg := range files {
			f, err := os.Open(arg)
			if err != nil {
				fmt.Fprintf(os.Stderr, "dup2: %v\n", err)
				continue
			}
			countLines(f, counts, countsFiles)
			defer f.Close()
		}
	}
	for line, n := range counts {
		if n > 1 {
			fmt.Printf("%d\t%s\t%v\n", n, line, countsFiles[line])
		}
	}
}

func countLines(f *os.File, counts map[string]int, countsFiles map[string][]string) {
	input := bufio.NewScanner(f)
	name := f.Name()
	for input.Scan() {
		text := input.Text()
		counts[text]++
		if !arrayContains(countsFiles[text], name) {
			countsFiles[text] = append(countsFiles[text], name)
		}
	}
	// NOTE: ignoring potential errors from input.Err()
}

func arrayContains(array []string, value string) bool {
	for _, item := range array {
		if item == value {
			return true
		}
	}
	return false
}
