package main

import (
	"strconv"
	"fmt"
	"os"
	"bufio"
)

func convertToBin(n int) string {
	result := ""
	for ; n > 0; n /= 2 {
		result = strconv.Itoa(n%2) + result
	}
	return result
}

func printFile(filename string)  {
	file, err := os.Open(filename)
	if err != nil {
		panic(err)
	}

	scanner := bufio.NewScanner(file)
	for scanner.Scan() {
		fmt.Println(scanner.Text())
	}
}

func forever()  {
	for {
		fmt.Println("abc")
	}
}

func main() {
	fmt.Println(
		convertToBin(4),
		convertToBin(23423),
		convertToBin(5234412),
	)

	printFile("C:\\xue.zeng\\GitHub\\cumulative\\demo\\Go\\training\\abc.txt")

	forever()
}
