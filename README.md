# Practical-Widgets

## 一.介绍
### 1. 此项目为[靖暄](https://wpa.qq.com/msgrd?uin=1250838250)个人制作用于添加任何想到的小功能[Mirai Console](https://github.com/mamoe/mirai-console) 插件，同样兼容[Mirai core](https://github.com/sonder-joker/mirai-compose)

### 2. 灵感来源于

#### a. 今日人品
[龙腾猫越](https://afdian.net/a/LTCat?tab=home)的PCL启动器中包含的 `今日人品` 功能
#### b. 舔狗日记
[ALAPI](http://www.alapi.cn/)中的舔狗日记API

### 3.代码参考于
[MiraiForum](https://mirai.mamoe.net/)  
官方社区的插件开源代码

### 4. 当前拥有功能

#### a. 舔狗日记
每天一次的文案，让你当上一名合格的舔狗
#### b. 今日人品
**昨天** 真是糟糕透了，一定是人品太低的原因！也不知道 **今天** 的人品会好起来吗？
#### c.今日人品排行榜
让我看看今天是谁人品最高，帮我抽个心海老婆吧 (原神乱入)


## 二.使用方法

从[Releases](https://github.com/jxmm52547/kcb/releases)下载插件放置于`./plugins`

### 1.指令

**无需下载[chat-command](https://github.com/project-mirai/chat-command)皆可在聊天内使用指令**
**仅群内可用**

#### a. 注册

* `/注册` 向数据库中添加您的数据,注册后可使用以下指令

#### b. 今日人品

* `/jrrp` **每天一次**可查看 今日人品  (可查看 `src/main/xyz.jxmm/tools/JrrpMap`中所有匹配项 )
* `/jrrptop` 查看今日人品排行榜 (可查看 `src/main/xyz.jxmm/tools/JrrpMap`中所有匹配项 )
* `/reset jrrptop` 手动重置今日人品排行榜 ***如果今日已经有人获取今日人品将一并重置且今日无法再参与排行***

#### c. 舔狗日记

* `/舔狗日记` **每天一次**可调用API获取 舔狗日记

### 2.数据库
* 采用`.json`文件格式存储数据,文件位于`./PracticalWidgets`
其中会包含主要数据和排行榜数据
* 首次启动会自动创建，如果损坏可删除文件后重新启动 ***请勿手动更改数据库,否则将会导致 `舔狗日记`数据乱码***

## 三.更新日志

###  V0.1.0
首个打包好的插件，拥有其主要功能 **[今日人品;舔狗日记]**

###  V0.1.1
完善代码,修改写法,增加 **今日人品排行榜**功能

### V0.1.2
* 修复 **今日人品排行榜** 非固定时间重置BUG 现在为 **每次开机** 和 **每天0点** 自动重置
* 新增music目录,为新功能做准备
* 关于 **API** 锁死问题以及 **排行榜建议合并转发** 已经纳入规划，未来版本解决

### V0.1.3
* 修复 **今日人品排行榜** 显示来自何群信息bug
* 改为**合并转发**方式发送排行榜 (来自[MiraiForum](https://mirai.mamoe.net/)中[firefairy](https://mirai.mamoe.net/user/firefairy)提出的建议)
* ***此版本建议尽早更新！此版本建议尽早更新！此版本建议尽早更新！***

### V0.1.4
* 修复 **舔狗日记** @用户未转义问题
* 新增配置文件，文件目录 `./PracticalWidgets/data.properties` 为将来做准备
* 现有BUG 如果 **今日人品排行榜** 重复则排行榜将会报错问题 预计下版本(V0.1.5)修复
* **今日人品排行榜**将会更换写法，做到区分群聊发送(获取当前群号然后遍历此群的今日人品做排行，这取决于用户在哪个群查看的**今日人品**)
* 将就着先用着吧，靖暄这边高三应届毕业生，我尽力快点换写法

## 四.关于

如果您发现 BUG 可以联系[靖暄](https://wpa.qq.com/msgrd?uin=1250838250)

如果您有任何想要的功能也可联系靖暄或者加群提出建议，如果加群请回答您看到此内容的平台(GITHUB; MiraiForum; GITEE) **三选一**

[点击加群](https://qm.qq.com/cgi-bin/qm/qr?k=_rYUOn7VOO4-34qPy5kTVrrT08s3sC1v&jump_from=webapi&authKey=xH5JaRthfo8upiNAQgV8ZEumcMRJYqmvE5w1Lgz/U2yskulZz7xWMrwm32+Mhs4f)

