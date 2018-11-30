package main

import "fmt"

func lengthOfNonRepeatingSubStr(s string) int {
	// last0ccurred := make(map[byte]int)
	last0ccurred := make(map[rune]int)
	start := 0
	maxLength :=0

	// for i, ch := range []byte(s) {
	for i, ch := range []rune(s) {
		if lastI, ok := last0ccurred[ch]; ok && lastI >= start {
			start =last0ccurred[ch] + 1
		}
		if i - start + 1 > maxLength {
			maxLength = i - start  + 1
		}
		last0ccurred[ch] = i
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
