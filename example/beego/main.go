package main

import "github.com/beego/beego/v2/client/orm"

type User struct {
	ID    uint    // 主键
	Name  string  `gorm:"column:user_name;type:varchar(50);not null" json:"name"` // 姓名
	Email *string // 邮箱
	Age   int32   // 年龄
}

func test01() {
	orm.NewOrm().QueryTable(&User{}).Filter()

	orm.NewOrm().QueryTableWithCtx()
}
