[English](./README.md) | [简体中文](./README-zh_CN.md) | 日本語 | [한국어](./README-ko_KR.md)

<div align="center">
    <img src="https://blog.johnmai.top/go-orm-helper/src/main/resources/icons/icon64x64.svg" alt="Go ORM アシスタント"/>
    <h1 align="center">Go ORM アシスタント</h1>
</div>

<p align="center">GoLand にORMを書くためにデータベースフィールド、Tag、Struct を自動的に補完する Jetbrains プラグイン。サポート：Gorm、Xorm、Beego、GoFrame...）<br>私たちの仕事をサポートするために ⭐️スター をください！</p>

> インスピレーション源の  [Laravel Idea](https://plugins.jetbrains.com/plugin/13441-laravel-idea) &
> [PhpStorm metadata](https://www.jetbrains.com/help/phpstorm/ide-advanced-metadata.html).
> 本人がGoのORMパケットを使用している場合、いくつかのORM関数のパラメータは文字列であり、ideはコード補完をサポートしていないため、フィールド泥
> 棒が多い場合、はっきり覚えられず、書くのが不便になる。以前本人がphpを書いていたときに、前のプラグインを使っていて、とても爽快だったので、この
> プラグインができました~~

## プロパティ

- [x] ORM コード補完
    - [x] @Model 注記補助補完
    - [x] @Table 注記補助補完
    - [ ] カスタムSQLアクセシビリティ不全 🚧[WIP]
- [x] SQL 変換 Struct [サポート詳細](./SUPPORTED.md#supported-sql-to-struct-conversion)
- [x] Go ORM Tag リアルタイムテンプレート [サポート詳細](./SUPPORTED.md#supported-orm-tags-live-template)
- 発見と改善を待っています...

## サポートされているORM

- [x] [Gorm](https://github.com/go-gorm/gorm)
- [x] [Xorm](https://gitea.com/xorm/xorm)
- [x] [GoFrame](https://github.com/gogf/gf)
- [ ] [Beego](https://github.com/beego/beego) 🚧[WIP]
- [ ] [sqlx](https://github.com/jmoiron/sqlx) 🚧[WIP]
- [サポート詳細](./SUPPORTED.md)

## 使用

![guide.gif](assets%2Fguide.gif)

## インストール

> 互換性範囲:
> - GoLand — 2022.2+
> - IntelliJ IDEA Ultimate — 2022.2+

### Jetbrains Marketplace からプラグインをインストールする

<a href="https://plugins.jetbrains.com/plugin/22173-go-orm-helper" target="_blank">
    <img src="https://blog.johnmai.top/go-orm-helper/assets/installation_button.svg" height="52" alt="Get from Marketplace" title="Get from Marketplace">
</a>

### ローカルインストール

- 【Go ORMアシスタント】プラグインパッケージのダウンロード [Releases](https://github.com/maiqingqiang/go-orm-helper/releases)
- プラグイン・チュートリアルのインストール https://www.jetbrains.com/help/idea/managing-plugins.html

## Star History

[![Star History Chart](https://api.star-history.com/svg?repos=maiqingqiang/go-orm-helper&type=Date)](https://star-history.com/#maiqingqiang/go-orm-helper&Date)