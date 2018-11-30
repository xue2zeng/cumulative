package main

import "fmt"

func main() {
	var m1 = map[string]string {
		"name": "xue",
		"course": "golang",
		"site": "imooc",
		"quality": "notbad",
	}

	m2 := make(map[string]int)
	var m3 map[string]int

	fmt.Println(m1, m2, m3)

	fmt.Println("Traversing map")
	for k, v := range m1 {
		fmt.Println(k, v)
	}

	fmt.Println("Getting values")
	courseName, ok := m1["course"];
	fmt.Println(courseName, ok)

	if caurseName, ok := m1["caurse"]; ok {
		fmt.Println(caurseName)
	} else {
		fmt.Println("key does not exist")
	}

	fmt.Println("Deleting values")
	name, ok := m1["name"]
	fmt.Println(name, ok)

	delete(m1, "name")
	name, ok = m1["name"]
	fmt.Println(name, ok)
}
