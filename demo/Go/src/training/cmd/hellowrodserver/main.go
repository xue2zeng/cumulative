package main

import (
	"fmt"
	"log"
	"net/http"
)

func msgHandler(w http.ResponseWriter, r *http.Request) {
	fmt.Fprintf(w, "Hello, world %s!", r.FormValue("name"))
}

func main() {
	http.HandleFunc("/", msgHandler)

	log.Println("lodingg...")
	http.ListenAndServe(":8888", nil)
}
