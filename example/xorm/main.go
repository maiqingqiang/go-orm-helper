package main

import (
	"xorm.io/xorm"
)

type User struct {
	ID    uint    // 主键
	Name  string  `xorm:"'user_name'"` // 姓名
	Email *string // 邮箱
	Age   int32   // 年龄
}

func test1(db *xorm.Engine) (user *User) {
	db.Table(&user).Where("id = ? = ?", 1).Find(&user)

	db.Table(&user).Where(map[string]interface{}{"user_name": "jinzhu", "age": 20}).Find(&user)

	db.Where("id = ?", 1).Find(&user)

	db.Table(&User{}).Where("id = ?", 1).Find(&user)

	db.Table(new(User)).Where("id = ?", 1).Find(&user)

	db.Table("users").Where("id = ?", 1).Find(&user)

	// @Model(User)
	db.Where("user_name != ?", "")

	// @Table(users)
	db.Where("id IN ?", "")

	return
}

func test2(db *xorm.Engine) (users []*User) {
	db.Where("user_name = ?", "").Find(&users)
	return
}

func test3(db *xorm.Engine, user *User) (users []*User) {
	db.Table(&user).Where("user_name = ?", "").Find(&users)
	return
}

func test4(db *xorm.Engine) (user *User) {
	query := db.Table(&User{})
	query.Where("id = ?", 1)
	return
}

func test5(db *xorm.Engine) (user *User) {
	query := db.Table(&User{})
	query = query.Where("id = ?", "")
	query.Find(&user)
	return
}

func test6(db *xorm.Engine) (user *User) {
	query := db.Where("id = ?", "")
	query.Find(&user)
	return
}
