package main

import "fmt"

func lengthOfNonRepeatingSubStr(s string) int {
	lastOccurred := make(map[rune]int)
	start := 0
	maxLength := 0

	for i, ch := range []rune(s) {
		if lastI, ok := lastOccurred[ch]; ok && lastI >= start {
			start = lastI + 1
		}
		if i-start+1 > maxLength {
			maxLength = i - start + 1
		}
		lastOccurred[ch] = i
	}

	return maxLength
}

func main() {
	fmt.Println(lengthOfNonRepeatingSubStr("featddd"))
	fmt.Println(lengthOfNonRepeatingSubStr("eeee"))
	fmt.Println(lengthOfNonRepeatingSubStr("dfdfdfdf"))
	fmt.Println(lengthOfNonRepeatingSubStr(""))
	fmt.Println(lengthOfNonRepeatingSubStr("爱我中华"))
	fmt.Println(lengthOfNonRepeatingSubStr("一二三四五五四三二一"))
}
