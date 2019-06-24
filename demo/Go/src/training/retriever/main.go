package main

import "fmt"
import "./mock"
import (
	"./real"
	"time"
)

type Retriever interface {
	Get(url string) string
}

func download(r Retriever) string {
	return r.Get("http://www.imooc.com")
}

func main() {
	var r Retriever
	r = mock.Retriever{"this is a fake imooc.com"}
	inspect(r)
	r = &real.Retriever{
		UserAgent:"Mozilla/5.0",
		TimeOut:time.Minute,
	}
	inspect(r)

	// Type assertion
	realRetriever := r.(*real.Retriever)
	fmt.Println(realRetriever.TimeOut)

	//fmt.Println(download(r))
}

func inspect(r Retriever) {
	fmt.Printf("%T, %v\n", r, r)
	fmt.Println("Type switch: ")
	switch v := r.(type) {
	case mock.Retriever:
		fmt.Println("Contents: ", v.Contents)
	case *real.Retriever:
		fmt.Println("UserAgent: ", v.UserAgent)
	}
}
