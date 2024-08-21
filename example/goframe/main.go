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

func test1() (user *User) {
	g.Model(&User{}).Where("id = ?")

	g.Model(&User{}).Where("id = ? and user_name = ? OR email != ?")

	g.DB().Model(&User{}).Where("id = ? and")

	g.Model(&user).Where("id = ?", 1)

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

func test6() {
	model := g.Model(&User{})
	condition := model.Builder()
	// @Table(user)
	condition = condition.Where("id IN ?", g.Slice{1, 2, 3, 4})
	// @Table(user)
	condition = condition.WhereOr(`id IN ?`, g.Slice{1, 2, 3, 4})
	// @Model(User)
	condition = condition.WhereOr("user_name = ?", "zhangsan")
	// @Model(User)
	condition = condition.WhereGT("age", 11)
	model.Where(condition)
// 	where, args := condition.Build()
// 	model.Where(where, args)
	return

}
