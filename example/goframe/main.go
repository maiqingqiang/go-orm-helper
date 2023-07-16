package main

import (
	"github.com/gogf/gf/v2/frame/g"
)

type User struct {
	g.Meta `orm:"table:goframe_users, do:true"`
	ID     uint    // 主键
	Name   string  `orm:"user_name"` // 姓名
	Email  *string // 邮箱
	Age    int32   // 年龄
}

func main() {
	g.Model(&User{}).Where("id = ?")
}

func test1() (user *User) {
	g.Model(&user).Where("id = ? = ?", 1)

	g.Model(&User{}).Where(g.Map{"user_name": 1, "name": "john"})

	g.Model(new(User)).Where("id = ?", 1)

	g.Model(User{}).Where("id = ?", 1)

	g.Model("goframe_users").Where("id = ?", 1)

	// @Model(User)
	g.Model("").Where("user_name != ?", "")

	// @Table(users)
	g.Model("").Where("user_name", "")

	return
}

func test4() {
	query := g.Model(&User{})
	query.Where("id = ?", 1)
	return
}

func test5() {
	query := g.Model(&User{})
	query = query.Where("id = ?", "")
	return
}
