package main

import (
	"fmt"
	"unicode/utf8"
)

func main() {
	s := "Yes, 我爱中华！"
	fmt.Println(s)
	for _, b := range []byte(s) {
		fmt.Printf("%X ", b)
	}
	fmt.Println("\n#########################################")

	for i, ch := range s {
		fmt.Printf("(%d %X)", i, ch)
	}
	fmt.Println("\n#########################################")

	fmt.Println("Rune count: ", utf8.RuneCountInString(s))

	bytes := []byte(s)
	for len(bytes) > 0 {
		ch , size := utf8.DecodeRune(bytes)
		bytes = bytes[size:]
		fmt.Printf("%c ", ch)
	}
	fmt.Println("\n#########################################")

	for i, ch := range []rune(s) {
		fmt.Printf("(%d %c)", i, ch)
	}
	fmt.Println("\n#########################################")
}
