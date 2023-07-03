<!-- Plugin description -->
English | [简体中文](./README-zh_CN.md)

<div align="center">
    <img src="https://blog.johnmai.top/go-orm-helper/src/main/resources/icons/icon64x64.svg" alt="Go ORM Helper"/>
    <h1 align="center">Go ORM Helper</h1>
</div>

<p align="center">A JetBrains plugin for GoLand that automatically provides database field autocompletion, tags, and generates Structs for writing ORM code. Supports: gorm, xorm, beego, database/sql, etc.</p>

> Inspired by [Laravel Idea](https://plugins.jetbrains.com/plugin/13441-laravel-idea) & 
> [PhpStorm metadata](https://www.jetbrains.com/help/phpstorm/ide-advanced-metadata.html). When using ORM packages in Golang,
> I noticed that some ORM function parameters are strings and the IDE does not support code completion. It becomes inconvenient
> to write code when dealing with numerous fields. Previously, when I was writing PHP, I used similar plugins and found them
> extremely useful. Hence, this plugin was developed.

## Features
- [x] ORM code autocompletion
  - [x] Assisted completion with @Model annotation
  - [ ] Assisted completion with @Table annotation 🚧[WIP]
  - [ ] Assisted completion for custom SQL 🚧[WIP]
- [x] SQL to Struct conversion
  - [x] Gorm
  - [x] Xorm
  - [x] Generic Struct
  - [ ] GoFrame
  - [ ] Beego
  - [ ] sqlx
- [ ] Tag code autocompletion 🚧[WIP]
- More features waiting to be discovered and improved...

## Supported ORM Packages
- [x] [Gorm](https://github.com/go-gorm/gorm)
- [x] [Xorm](https://gitea.com/xorm/xorm)
- [ ] [GoFrame](https://github.com/gogf/gf) 🚧[WIP]
- [ ] [Beego](https://github.com/beego/beego) 🚧[WIP]
- [ ] [sqlx](https://github.com/jmoiron/sqlx) 🚧[WIP]
- More packages waiting to be discovered and improved...

## Usage
![guide.gif](assets%2Fguide.gif)

<!-- Plugin description end -->

## Installation

<a href="https://plugins.jetbrains.com/plugin/22173-go-orm-helper" target="_blank">
    <img src="https://blog.johnmai.top/go-orm-helper/assets/installation_button.svg" height="52" alt="Get from Marketplace" title="Get from Marketplace">
</a>

### Install Plugin from Disk

- Download Go ORM Helper Plugin [Releases](https://github.com/maiqingqiang/go-orm-helper/releases)
- Install plugins guide: https://www.jetbrains.com/help/idea/managing-plugins.html
