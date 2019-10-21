package rpcdemo

import (
	"errors"
	"log"
)

type DemoService struct{}

type Args struct {
	A, B int64
}

func (DemoService) Div(args Args, result *float64) error {
	log.Println(args.A, args.B)
	if args.B == 0 {
		return errors.New("division by zero")
	}
	*result = float64(args.A) / float64(args.B)
	return nil
}
