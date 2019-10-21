package main

import (
	"log"
	"net"
	"net/rpc"
	"net/rpc/jsonrpc"
	rpcdemo "training/rpc"
)

// telnet 127.0.0.1 1234
// {"method": "DemoService.Div","params":[{"A":3},{"B":4}],"id":1}
// {"id":1,"result":0.75,"error":null}
func main() {
	// 注册服务
	rpc.Register(rpcdemo.DemoService{})
	// 监听端口
	listener, err := net.Listen("tcp", ":1234")
	if err != nil {
		panic(err)
	}

	for {
		// 创建连接
		conn, err := listener.Accept()
		if err != nil {
			log.Printf("accept error: %v", err)
			continue
		}

		go jsonrpc.ServeConn(conn)
	}
}
