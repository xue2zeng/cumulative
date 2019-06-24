package main

import (
	"flag"
	"fmt"
	"os"
)

func main() {
	fmt.Println(os.Args)

	// 参数格式定义
	methodPtr := flag.String("method", "default", "method of sample")
	valuePtr := flag.Int("value", -1, "value of sample")

	// 解析
	flag.Parse()

	fmt.Println(*methodPtr, *valuePtr)

	var method string
	var value int

	flag.StringVar(&method, "method", "defult", "method of sample")
	flag.IntVar(&value, "value", -1, "value of sample")

	flag.Parse()

	fmt.Println(method, value)
}
