English | [简体中文](./README-zh_CN.md) | [日本語](./README-ja_JP.md) | [한국어](./README-ko_KR.md)

<div align="center">
    <img src="https://blog.johnmai.top/go-orm-helper/src/main/resources/icons/icon64x64.svg" alt="Go ORM Helper"/>
    <h1 align="center">Go ORM Helper</h1>
</div>

<p align="center">A GoLand plugin that automatically provides database field autocompletion, tags, and generates Structs
for writing ORM code. Supports: Gorm、Xorm、Beego、GoFrame, etc.<br/>⭐️ Star to support our work!</p>

> Inspired by [Laravel Idea](https://plugins.jetbrains.com/plugin/13441-laravel-idea) & 
> [PhpStorm metadata](https://www.jetbrains.com/help/phpstorm/ide-advanced-metadata.html). When using ORM packages in Golang,
> I noticed that some ORM function parameters are strings and the IDE does not support code completion. It becomes inconvenient
> to write code when dealing with numerous fields. Previously, when I was writing PHP, I used similar plugins and found them
> extremely useful. Hence, this plugin was developed.

## Features
- [x] ORM Code Completion.
  - [x] Assisted code completion with @Model annotation.
  - [x] Assisted code completion with @Table annotation.
  - [ ] Assisted code completion for custom SQL. 🚧[WIP]
- [x] SQL to Struct conversion.
  - [x] Gorm
  - [x] Xorm
  - [x] Generic Struct
  - [x] GoFrame
  - [ ] Beego  🚧[WIP]
  - [ ] sqlx  🚧[WIP]
- [ ] Tag Code Completion. 🚧[WIP]
- More features waiting to be discovered and improved...

## Supported ORM Packages
- [x] [Gorm](https://github.com/go-gorm/gorm)
- [x] [Xorm](https://gitea.com/xorm/xorm)
- [x] [GoFrame](https://github.com/gogf/gf)
- [ ] [Beego](https://github.com/beego/beego) 🚧[WIP]
- [ ] [sqlx](https://github.com/jmoiron/sqlx) 🚧[WIP]
- More packages waiting to be discovered and improved...

## Usage
![guide.gif](assets%2Fguide.gif)

<!-- Plugin description end -->

## Installation

> Compatibility Range: GoLand — 2022.2 — 2023.2 (eap)

<a href="https://plugins.jetbrains.com/plugin/22173-go-orm-helper" target="_blank">
    <img src="https://blog.johnmai.top/go-orm-helper/assets/installation_button.svg" height="52" alt="Get from Marketplace" title="Get from Marketplace">
</a>

### Install Plugin from Disk

- Download Go ORM Helper Plugin [Releases](https://github.com/maiqingqiang/go-orm-helper/releases)
- Install plugins guide: https://www.jetbrains.com/help/idea/managing-plugins.html
