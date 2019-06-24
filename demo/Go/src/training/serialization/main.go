package main

import (
	"encoding/xml"
	"fmt"
)

type person struct {
	Name string `xml:"name,attr"`
	Age  int
}

func main() {
	p := person{Name: "xue", Age: 18}

	var data []byte
	var err error

	//if data, err := xml.Marshal(p); err != nil {
	// 序列化
	if data, err = xml.MarshalIndent(p, "~", " "); err != nil {
		fmt.Println(err)
		return
	}
	fmt.Println(string(data))

	// 反序列化
	p2 := new(person)
	if err = xml.Unmarshal(data, p2); err != nil {
		fmt.Println(err)
		return
	}
	fmt.Println(p2)
}
