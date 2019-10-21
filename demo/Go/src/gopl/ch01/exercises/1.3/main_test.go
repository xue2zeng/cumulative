package main

import (
	"testing"
)

/*
go test -bench .
goos: darwin
goarch: amd64
pkg: gopl/ch01/exercises/1.3
BenchmarkEcho1-8        args: [a b c d e f g h i j k l m n o p q r s t u v w x y z]
1000000000               2.86 ns/op
BenchmarkEcho2-8        args: [a b c d e f g h i j k l m n o p q r s t u v w x y z]
500000000                3.43 ns/op
BenchmarkEcho3-8        args: [a b c d e f g h i j k l m n o p q r s t u v w x y z]
500000000                3.14 ns/op
PASS
ok      gopl/ch01/exercises/1.3 7.115s
*/

var (
	args = []string{"a b c d e f g h i j k l m n o p q r s t u v w x y z"}
)

func TestEcho1(t *testing.T) {
	t.Logf("args: %v", args)
	if len(args) > 0 {
		echo1(args)
	} else {
		t.Logf("no args")
	}
}

func BenchmarkEcho1(b *testing.B) {
	b.Logf("args: %v", args)
	if len(args) > 0 {
		for i := 0; i < b.N; i++ {
			echo1(args)
		}
	} else {
		b.Logf("no args")
	}
}

func BenchmarkEcho2(b *testing.B) {
	b.Logf("args: %v", args)
	if len(args) > 0 {
		for i := 0; i < b.N; i++ {
			echo2(args)
		}
	} else {
		b.Logf("no args")
	}
}

func BenchmarkEcho3(b *testing.B) {
	b.Logf("args: %v", args)
	if len(args) > 0 {
		for i := 0; i < b.N; i++ {
			echo3(args)
		}
	} else {
		b.Logf("no args")
	}
}
