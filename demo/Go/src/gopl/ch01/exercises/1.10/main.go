// Copyright © 2018 xue.zeng@outlook.com
// Copyright © 2015 Alan A. A. Donovan · Brian W. Kernighan.
// Published Oct 26, 2015 in paperback and Nov 20 in e-book

// 1.10 找一个数据量比较大的网站，用本小节中的程序调研网站的缓存策略，对每个 URL执行两遍请求，查看两次时间是否有较大的差别，并且每次获取到的响应内容是否一 致，修改本节中的程序，将响应结果输出，以便于进行对比。
package main

import (
	"fmt"
	"io"
	"net/http"
	"os"
	"strings"
	"time"
)

// reported elapse time changed with successive run and content is not the same
func main() {
	start := time.Now()
	ch := make(chan string)
	for _, url := range os.Args[1:] {
		go fetch(url, ch) // start a goroutine
	}
	for range os.Args[1:] {
		fmt.Println(<-ch) // receive from channel ch
	}
	fmt.Printf("%.2fs elapsed\n", time.Since(start).Seconds())
}

func fetch(url string, ch chan<- string) {
	i, j := strings.Index(url, "."), strings.LastIndex(url, ".")
	// in case of only one "." in url
	if i == j {
		i = 0
	}
	output, err := os.Create(url[i+1:j] + "-dump.html")

	start := time.Now()
	if err != nil {
		ch <- fmt.Sprintf("while create output file: %v", err)
		return
	}

	resp, err := http.Get(url)
	if err != nil {
		ch <- fmt.Sprint(err) // send to channel ch
		return
	}

	nbytes, err := io.Copy(output, resp.Body)
	resp.Body.Close() // don't leak resources
	output.Close()
	if err != nil {
		ch <- fmt.Sprintf("while reading %s: %v", url, err)
		return
	}
	secs := time.Since(start).Seconds()
	ch <- fmt.Sprintf("%.2fs  %7d  %s", secs, nbytes, url)
}

//!-
