package main

import (
	"github.com/gogf/gf/v2/frame/g"
)

type User struct {
	ID    uint    `orm:"idd,dd"` // 主键sss
	Name  string  // 姓名
	Email *string // 邮箱
}

func main() {
	g.Model(&User{}).Where(g.Map{"idd": 1, "name": "john"}).Fields("idd", "email").OnDuplicate()
}
