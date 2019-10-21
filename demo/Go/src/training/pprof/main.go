package main

import (
	"log"
	"net/http"
	_ "net/http/pprof"
)

func main() {
	go func() {
		for {
			log.Println(Add("https://github.com"))
		}
	}()

	http.ListenAndServe("0.0.0.0:8080", nil)
}
