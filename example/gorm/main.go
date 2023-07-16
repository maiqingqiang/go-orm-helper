package main

import (
	"gorm.io/gorm"
)

type User struct {
	ID    uint    // 主键
	Name  string  `gorm:"column:user_name;type:varchar(50);not null" json:"name"` // 姓名
	Email *string // 邮箱
	Age   int32   // 年龄
}

func (u *User) TableName() string {
	return "users"
}

func test1(db *gorm.DB) (user *User) {
	db.Model(&user).Where("id = ? = ?", 1).Find(&user)

	db.Model(&user).Where(map[string]interface{}{"user_name": "jinzhu", "age": 20}).Find(&user)

	db.Where("id = ?", 1).Find(&user)

	db.Model(&User{}).Where("id = ?", 1).Find(&user)

	db.Model(new(User)).Where("id = ?", 1).Find(&user)

	db.Table("users").Where("id", 1).Find(&user)

	// @Model(User)
	db.Where("user_name != ?", "")

	// @Table(users)
	db.Where("id = ?", "")

	return
}

func test2(db *gorm.DB) (users []*User) {
	db.Where("user_name = ?", "").Find(&users)
	return
}

func test3(db *gorm.DB, user *User) (users []*User) {
	db.Model(&user).Where("user_name = ?", "").Find(&users)
	return
}

func test4(db *gorm.DB) (user *User) {
	query := db.Model(&User{})
	query.Where("id = ?", 1)
	return
}

func test5(db *gorm.DB) (user *User) {
	query := db.Model(&User{})
	query = query.Where("id = ?", "")
	query.Find(&user)
	return
}

func test6(db *gorm.DB) (user *User) {
	query := db.Where("id = ?", "")
	query.Find(&user)
	return
}
