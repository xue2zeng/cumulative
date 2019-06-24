package main

import (
	"fmt"
	"strconv"
	"strings"
)

func main() {
	s := "hello world"
	// 是否包含
	fmt.Println(strings.Contains(s, "hello"), strings.Contains(s, "?"))

	// 索引，base 0
	fmt.Println(strings.Index(s, "o"))

	ss := "1#3#32#99"

	splictStr := strings.Split(ss, "#")
	// 分割
	fmt.Println(splictStr)

	// 连接合并
	fmt.Println(strings.Join(splictStr, "$"))

	// 前后缀检测
	fmt.Println(strings.HasPrefix(s, "he"), strings.HasSuffix(s, "ld"))

	// 数字转换
	fmt.Println(strconv.Itoa(10))
	fmt.Println(strconv.Atoi("911"))
	// 解析
	fmt.Println(strconv.ParseBool("false"))
	fmt.Println(strconv.ParseFloat("3.14", 32))

	// 格式化
	fmt.Println(strconv.FormatBool(true))
	fmt.Println(strconv.FormatInt(123, 4))
}
