package main

import (
	"xorm.io/xorm"
)

type Order struct {
	ID    uint
	Name  string
	Price *string
}

func main() {
	engine, _ := xorm.NewEngine("", "")

	var order Order

	qq := engine.Where("id IN ?", "")
	qq.And("name").Asc().Get(&order)
}
