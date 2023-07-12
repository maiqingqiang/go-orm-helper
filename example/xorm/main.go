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

	engine.Table(&order).Where("id", "")
	engine.Table("orders").Where("id", "")

	db := engine.Table("orders")
	db.Where("id")

	engine.Where("id", "")
	engine.Where("id", "")

	qq := engine.Table("order").Where("id", "")
	qq.And("usr_name").Asc().Get(&order)
}
