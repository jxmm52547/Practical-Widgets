# Practical-Widgets

## 一.介绍
### 1. 此项目为[靖暄](https://wpa.qq.com/msgrd?uin=1250838250)个人制作用于添加任何想到的小功能[Mirai Console](https://github.com/mamoe/mirai-console) 插件，同样兼容[Mirai core](https://github.com/sonder-joker/mirai-compose)

### 2. 灵感来源于

#### a. 今日人品
[龙腾猫越](https://afdian.net/a/LTCat?tab=home)的PCL启动器中包含的 `今日人品` 功能
#### b. 舔狗日记
[ALAPI](http://www.alapi.cn/)中的舔狗日记API
#### c. 点歌
各位大佬制作的点歌插件想着自己也做一个
#### d. new对象
呜呜呜, 情人节没人一起过new一个对象陪我过
#### e. 查询 hypixel 服务器信息
每次想看数据都得上服务器, 麻烦死了
#### g.退群提醒
来自[MiraiForum](https://mirai.mamoe.net/)中[@MC__luoluo](https://mirai.mamoe.net/user/mc__luoluo)提出

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
#### d.点歌
找到好听的歌想分享, 又不想发链接这么麻烦? 要试试点歌功能吗
#### e.new对象
情人节没对象怎么办, 那就 new一个对象吧
#### f.查询 hypixel 服务器信息
要来跟我比比数据吗
#### g.退群提醒
当群成员退群时, 发送提醒至群内


## 二.使用方法

从[Releases](https://github.com/jxmm52547/kcb/releases)下载插件放置于`./plugins`

### 1.指令

**无需下载[chat-command](https://github.com/project-mirai/chat-command)皆可在聊天内使用指令**
**仅群内可用**
**指令前缀: 默认 `/` 可在配置文件自行定义**

#### a. 注册

* `/注册` 向数据库中添加您的数据,注册后可使用以下指令

#### b. 今日人品

* `/jrrp` **每天一次**可查看 今日人品  (可查看 `src/main/xyz.jxmm/tools/JrrpMap`中所有匹配项 )
* `/jrrptop` 查看今日人品排行榜 (可查看 `src/main/xyz.jxmm/tools/JrrpMap`中所有匹配项 )
* `/reset jrrptop` 手动重置今日人品排行榜 ***如果今日已经有人获取今日人品将一并重置且今日无法再参与排行***

#### c. 舔狗日记

* `/舔狗日记` **每天一次**可调用API获取 舔狗日记
* 请前往配置文件填写Token后使用

#### d. 点歌

* `/点歌 <歌名> ?<歌手>` 基于ALAPI获取音乐卡片 如果`<歌手>`存在, 建议在 `<歌名>` 与 `<歌手>`之间添加 连接符`-`(减号)

#### e. new对象

* `/new对象` **每天一次** 随机得到群成员作为您今日的new对象

#### f. 查询hyp相关信息

* `/hyp bw <PlayerID>` 查询 `起床战争` 信息
* `/hyp sw <PlayerID>` 查询 `空岛战争` 信息
* `/hyp player <PlayerID>` 查询玩家 `hypixel服务器` 一系列信息
* `/hyp acd <PlayerID>` 查询 `街机游戏` 信息

#### g. 退群提醒
* 格式: $memberNick + $quit + , QQ号: + $memberID + , $quitExpress
* 例子: 終末牽挂 退出了群聊, QQ号: 123456
请前往配置文件填写 `quit` 和 `quitExpress` 字段


### 2.数据库
* 采用`.json`文件格式存储数据,文件位于`./PracticalWidgets`

  其中会包含主要数据`data.json`, 排行榜数据`jrrpTop`, new对象数据`object.json`
* 首次启动会自动创建，如果损坏可删除文件后重新启动 ~~请勿手动更改数据库,否则将会导致 `舔狗日记`数据乱码~~

### 3.配置文件
* 采用`.properties`文件格式储存数据，文件位于`./PracticalWidgets/config.properties` 为未来做准备
* 配置文件有自动更新功能，您只需要在更新新版本后检查更新日志是否有新的配置信息，如果有可进行填写(无需重启即可生效)

## 三.更新日志

###  V0.1.0 *(2023.02.02)*
首个打包好地插件，拥有其主要功能 **[今日人品;舔狗日记]**

###  V0.1.1 *(2023.02.02)*
完善代码,修改写法,增加 **今日人品排行榜**功能

### V0.1.2 *(2023.02.05)*
* 修复 **今日人品排行榜** 非固定时间重置BUG 现在为 **每次开机** 和 **每天0点** 自动重置
* 新增music目录,为新功能做准备
* 关于 **API** 锁死问题以及 **排行榜建议合并转发** 已经纳入规划，未来版本解决

### V0.1.3 *(2023.02.07)*
* 修复 **今日人品排行榜** 显示来自何群信息bug
* 改为**合并转发**方式发送排行榜 (来自[MiraiForum](https://mirai.mamoe.net/)中[@FIREFAIRY](https://mirai.mamoe.net/user/firefairy)提出的建议)
* ***此版本建议尽早更新！此版本建议尽早更新！此版本建议尽早更新！***

### V0.1.4 *(2023.02.08)*
* 修复 **舔狗日记** @用户未转义问题
* 新增配置文件，文件目录 `./PracticalWidgets/data.properties` 为将来做准备
* 现有BUG 如果 **今日人品排行榜** 重复则排行榜将会报错问题 预计下版本(V0.1.5)修复
* **今日人品排行榜**将会更换写法，做到区分群聊发送(获取当前群号然后遍历此群的今日人品做排行，这取决于用户在哪个群查看的**今日人品**)
* 将就着先用着吧，靖暄这边高三应届毕业生，我尽力快点换写法

### V0.1.5 (重大修复) *(2023.02.09)*
* 配置文件增加用户自定义 `今日人品形容词`

* 修复 `V0.1.4`版本 排行榜bug
* 修复 `舔狗日记` 无法获取到数据导致数据库报错问题

* 更换 `今日人品排行榜` 写法, 现在可以做到分群发送 排行榜 每个群有不同的排行榜数据
* 更换 `舔狗日记` 获取写法

* 解决数据库乱码问题
* 代码更为完善

### V0.2.0 (重大更新) *(2023.02.09)*
* 新功能 `点歌` (欢呼!)
* 完善上版本代码

### V0.2.5 (新功能) *(2023.02.15)*
* 增加新功能 `new对象`
* 完善上版本代码

### V0.2.6 *(2023.02.17)*
* 修复 `今日人品排行榜` 转发信息时锁死末酱, 现改为自动获取到群bot的昵称

### V0.3.0 *(2023.02.19)*
* 优化上版本代码
* 新增 `询hypixel服务器相关信息` 功能
* 配置文件更新

### V0.3.1 *(2023.02.21)*
* 修复数据库异常过大问题
* 完善 `查询hypixel服务器相关信息` 功能
* 主数据库更新, 新增版本条目, 方便查看

### V0.3.2 *(2023.02.22)*
* 经用户反馈, 修复 `今日人品排行榜` 经常缺人问题
* 新增即便是在不同群也能查看到自己的人品值排行榜(在群A获取人品值后在群B再获取一次人品值就会写入数据库保留群B的排行数据)

### V0.3.3 *(2023.02.24)*
* 修复 `hyp相关信息查询` 中 `玩家不存在` 或 `数据不存在` 的报错 或 直接不给回复 问题 (来自[MiraiForum](https://mirai.mamoe.net/)中[@MC__luoluo](https://mirai.mamoe.net/user/mc__luoluo)提出的反馈)
* 修复 `自动更新数据库` 问题 (如果跨多个版本更新可能导致需要重启多次才会完整更新数据库功能)
* 预计下版本更新 `hyp相关信息查询` 中添加更多游戏模式

### V0.3.4 *(2023.02.27)*
* 修复 `hyp相关信息查询` 中 `玩家存在` 但 `玩家数据不存在` 得报错, 现在拥有回复, 且不会在控制台报错 (来自[MiraiForum](https://mirai.mamoe.net/)中[@MC__luoluo](https://mirai.mamoe.net/user/mc__luoluo)提出的反馈)
* 修复 `今日人品排行榜` 中 如果群成员试图 逆天改命 进行重复查询 `今日人品` 导致排行榜数据过大问题 (来自[GitHub](https://www.github.com/)中[@光影](https://github.com/DUXING130))

### V0.4.0 (重大更新) *(2023.02.28)*
* 修复 `hyp相关信息查询` 中 大部分BUG  现在很少遇到报错, 都带有提示

* **以下内容全部来自[MiraiForum](https://mirai.mamoe.net/)中[@MC__luoluo](https://mirai.mamoe.net/user/mc__luoluo)提出**
* 新增 `自定义指令前缀` 可在配置文件自定义您的指令前缀 默认 `/`
* `hyp相关信息查询` 中 `player` 字段 新增显示 `大厅等级` 保留三位小数
* `hyp相关信息查询` 中 `player` 字段 新增显示 `RANK` 
* `hyp相关信息查询` 中 `player` 字段 新增显示 `玩家皮肤预览`

### V0.4.1 *(2023.03.06)*
* `hyp信息查询` 中 新增 `街机游戏` 模块 `/hyp acd <ID>`  包含了绝大多数的街机游戏模式
* `hyp信息查询` 中 `player` 字段 新增 `rank赠送数`
* 修复 `hyp信息查询` 中 `player` 字段缺少 `【MVP++】` 问题

### V0.4.2 - Alpha *(2023.03.25)*
***该版本为 `V0.4.2测试版` 即上版本的修复版本, 缺少内容待下版本更新***
* 修正 `hyp信息查询` 中 `MVP++` 判断错误问题
* 为 `hyp信息查询` 中 新增 `<type>` 字段错误提醒
* 为 `hyp信息查询` 功能预留文件夹, 用于新模式添加
* 按照 [@MC__luoluo](https://mirai.mamoe.net/user/mc__luoluo) 要求 新增退群提醒 **(配置文件有更新)**

## 四.关于

如果您发现 BUG 可以联系[靖暄](https://wpa.qq.com/msgrd?uin=1250838250)

如果您有 **任何想要的功能** 也可联系靖暄或者加群提出建议，如果加群请回答您看到此内容的平台(GITHUB; MiraiForum; GITEE) **三选一**

[***点击催更***](https://qm.qq.com/cgi-bin/qm/qr?k=_rYUOn7VOO4-34qPy5kTVrrT08s3sC1v&jump_from=webapi&authKey=xH5JaRthfo8upiNAQgV8ZEumcMRJYqmvE5w1Lgz/U2yskulZz7xWMrwm32+Mhs4f)

**如果你也觉得 *Practical-Widgets* 做的好的话可以给靖暄赞助(备注上您的任意 平台主页地址 及 ID 将会列入赞助列表)**

<details>
<summary>赞助方式如下</summary>
<picture>
  <source media="微信" srcset="https://i.postimg.cc/k4zYrbz0/mm-facetoface-collect-qrcode-1676217553397.png">
  <source media="支付宝" srcset="https://i.postimg.cc/RVMKSFmy/1678152006444.jpg">
  <source media="数字人民币)" srcset="https://i.postimg.cc/N0WRBkGk/Screenshot-2023-03-07-09-15-02-148-cn-gov-pbc-dce.jpg">
  <source media="QQ" srcset="https://i.postimg.cc/Kj5T7XP0/qrcode-20230307091527.png">
  
  <img alt="微信." src="https://i.postimg.cc/k4zYrbz0/mm-facetoface-collect-qrcode-1676217553397.png">
  <img alt="支付宝." src="https://i.postimg.cc/RVMKSFmy/1678152006444.jpg">
  <img alt="数字人名币." src="https://i.postimg.cc/N0WRBkGk/Screenshot-2023-03-07-09-15-02-148-cn-gov-pbc-dce.jpg">
  <img alt="QQ." src="https://i.postimg.cc/Kj5T7XP0/qrcode-20230307091527.png">
</picture>
</details>
