package main

import (
	"flag"
	"fmt"
	"go-crawler-distributed/config"
	"go-crawler-distributed/persist"
	"go-crawler-distributed/rpcsupport"
	"log"

	"gopkg.in/olivere/elastic.v5"
)

var port = flag.Int("port", 0, "the port for me to listen on")

func main() {
	flag.Parse()
	if *port == 0 {
		fmt.Println("must specify a port")
		return
	}
	//Fatal，若有异常，则挂了,没有机会recover。panic还有recover的机会
	//log.Fatal(serveRpc(fmt.Sprintf(":%d",config.ItemSaverPort), config.ElasticIndex))
	log.Fatal(serveRpc(fmt.Sprintf(":%d", *port), config.ElasticIndex))

}

func serveRpc(host, index string) error {
	//docker , sniff false
	client, err := elastic.NewClient(elastic.SetSniff(false))
	if err != nil {
		return err
	}

	return rpcsupport.ServeRpc(host, &persist.ItemSaverService{
		Client: client,
		Index:  index,
	})
}
