package main

import (
	"xorm.io/xorm"
)

type Order struct {
	ID    uint   // 主键
	Name  string `xorm:"varchar(25) notnull unique 'usr_name' comment('姓名')"`
	Price *string
}

func main() {
	engine, _ := xorm.NewEngine("", "")

	var order Order

	qq := engine.Where("n", "")
	qq.And("name").Asc().Get(&order)
}
