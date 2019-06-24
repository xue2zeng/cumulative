package main

import (
	"bufio"
	"fmt"
	"os"
	"training/pipeline"
)

func main() {
	// for {
	// 	if num, ok := <-p; ok {
	// 		fmt.Println(num)
	// 	} else {
	// 		break
	// 	}
	// }

	const filename = "small.in"
	const count = 64
	file, err := os.Create(filename)
	if err != nil {
		panic(err)
	}
	defer file.Close()

	p := pipeline.RandomSource(count)
	writer := bufio.NewWriter(file)
	pipeline.WriterSink(writer, p)
	writer.Flush()

	file, err = os.Open(filename)
	if err != nil {
		panic(err)
	}
	defer file.Close()

	p = pipeline.ReaderSource(bufio.NewReader(file), -1)
	for v := range p {
		fmt.Println(v)
	}
}

func mergeDemo() {
	p := pipeline.Merge(pipeline.InMemSort(pipeline.ArraySource(3, 2, 5, 7, 4, 6)), pipeline.InMemSort(pipeline.ArraySource(7, 9, 1, 10, 78, 12)))
	for v := range p {
		fmt.Println(v)
	}
}
