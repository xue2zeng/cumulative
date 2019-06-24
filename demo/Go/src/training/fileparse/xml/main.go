package main

import (
	"bytes"
	"encoding/xml"
	"fmt"
	"io/ioutil"
)

func getAttributeValue(attr []xml.Attr, name string) string {
	for _, a := range attr {
		if a.Name.Local == name {
			return a.Value
		}
	}
	return ""
}

func main() {
	content, err := ioutil.ReadFile("vsproj.csproj")

	decoder := xml.NewDecoder(bytes.NewBuffer(content))

	var t xml.Token
	var intItemGroup bool
	for t, err = decoder.Token(); err == nil; t, err = decoder.Token() {
		switch token := t.(type) {
		case xml.StartElement:
			name := token.Name.Local
			if intItemGroup {
				if name == "Compile" {
					fmt.Println(getAttributeValue(token.Attr, "include"))
				}
			} else {
				if name == "ItemGroup" {
					intItemGroup = true
				}	
			}
		case xml.EndElement:
			if intItemGroup {
				if token.Name.Local == "ItemGroup" {
					intItemGroup = false
				}
			}
		}
	}
}
