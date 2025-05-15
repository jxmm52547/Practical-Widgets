# Practical-Widgets

## 一.介绍
### 1. 此项目为[靖暄](https://wpa.qq.com/msgrd?uin=1250838250)个人制作用于添加任何想到的小功能[Mirai Console](https://github.com/mamoe/mirai-console) 插件

### 2. 灵感来源于

| 序号 | 功能            | 来源                                                                                                        |
|----|---------------|-----------------------------------------------------------------------------------------------------------|
| 1  | 今日人品          | [龙腾猫越](https://afdian.net/a/LTCat?tab=home)的PCL启动器中包含的 `今日人品` 功能                                          |   
| 2  | 舔狗日记          | [ALAPI](http://www.alapi.cn/)中的舔狗日记API                                                                    |  
| 3  | 点歌            | 各位大佬制作的点歌插件想着自己也做一个                                                                                       |
| 4  | new对象         | 呜呜呜, 情人节没人一起过new一个对象陪我过                                                                                   |
| 5  | hyp相关信息       | 每次想看数据都得上服务器，麻烦死了                                                                                         |
| 6  | 退群提醒(Alpha)   | 来自[MiraiForum](https://mirai.mamoe.net/)中[@MC__luoluo](https://mirai.mamoe.net/user/mc__luoluo)提出         |
| 7  | 签到(Alpha - 2) | 来自[MiraiForum](https://mirai.mamoe.net/)中[@De6ris](https://mirai.mamoe.net/user/de6ris)提出自定义消息格式, 此功能用于测试 |
| 8  | 权限系统          | P-W使用者强烈建议                                                                                                |
| 9  | 对话功能          | 只是想做一个自己的Ai对话bot, 一不小心就做完善了                                                                               |


### 3.代码参考于
[MiraiForum](https://mirai.mamoe.net/)  
官方社区的插件开源代码

### 4. 当前拥有功能
| 序号 | 功能            | 介绍                                             |
|----|---------------|------------------------------------------------|
| 1  | 今日人品          | **昨天** 真是糟糕透了，一定是人品太低的原因！也不知道 **今天** 的人品会好起来吗？ |   
| 2  | 今日人品排行榜       | 让我看看今天是谁人品最高，帮我抽个心海老婆吧 (原神乱入)                  |
| 3  | 舔狗日记          | 每天一次的文案，让你当上一名合格的舔狗                            |  
| 4  | 点歌            | 找到好听的歌想分享, 又不想发链接这么麻烦? 要试试点歌功能吗                |
| 5  | new对象         | 情人节没对象怎么办, 那就 new一个对象吧                         |
| 6  | hyp相关信息       | 要来跟我比比数据吗                                      |
| 7  | 退群提醒(Alpha)   | 当群成员退群时, 发送提醒至群内                               |
| 8  | 签到(Alpha - 2) | 没什么作用, 用来测试自定义消息格式用                            |
| 9  | 权限系统          | 终于有权限系统啦！                                      |
| 10 | 对话功能          | 分用户保存对话记录, 自定义人设                               |


## 二.使用方法

从[Releases](https://github.com/jxmm52547/kcb/releases)下载插件放置于`./plugins`

### 1.指令

**无需下载[chat-command](https://github.com/project-mirai/chat-command)皆可在聊天内使用指令**
**仅群内可用**
**指令前缀: 默认 `/` 可在配置文件自行定义**

| 序号  | 指令                       | 功能                                                                                                                                                      |
|-----|--------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------|
| 0   | `/注册`                    | 向数据库中添加您的数据,注册后可使用以下指令                                                                                                                                  |
| 1   | `/jrrp`                  | 每天一次可查看 今日人品 (可查看`src/main/xyz.jxmm/tools/JrrpMap`中所有匹配项)                                                                                               |
| 2   | `/jrrptop`               | 查看今日人品排行榜 (可查看`src/main/xyz.jxmm/tools/JrrpMap`中所有匹配项)                                                                                                  |
| 3   | `/reset jrrptop`         | 手动重置今日人品排行榜 ***如果今日已经有人获取今日人品将一并重置且今日无法再参与排行***                                                                                                         |
| 4   | `/舔狗日记`                  | **每天一次**可调用API获取 舔狗日记  请前往配置文件填写Token后使用                                                                                                                |
| 5   | `/点歌 <歌名>`               | 基于ALAPI获取音乐卡片, 获取到歌曲列表后请回复 `1-10` 到自己发送的点歌指令即可                                                                                                          |
| 6   | `/new对象`                 | **每天一次** 随机得到群成员作为您今日的new对象                                                                                                                             |
| 7   | `/hyp <type> <playerID>` | 查询hyp相关信息                                                                                                                                               |
| 8   | 退群提醒                     | <li>格式: $memberNick + $quit + , QQ号: + $memberID + , $quitExpress</li><br/><li>例子: 終末牽挂 退出了群聊, QQ号: 123456</li><br/>请前往配置文件填写 `quit` 和 `quitExpress` 字段 |
| 9   | `/签到`                    | 每天一次 存储签到次数 (详细查看数据库)                                                                                                                                   |


### 2.数据库
* 采用`.json`文件格式存储数据,文件位于`./PracticalWidgets`

  其中会包含主要数据`data.json`, 排行榜数据`jrrpTop`, new对象数据`object.json`
* 首次启动会自动创建，如果损坏可删除文件后重新启动 ~~请勿手动更改数据库,否则将会导致 `舔狗日记`数据乱码~~

***

### 3.配置文件
* 采用`.properties`文件格式储存数据，文件位于`./PracticalWidgets/config.properties` 为未来做准备
* 配置文件有自动更新功能，您只需要在更新新版本后检查更新日志是否有新的配置信息，如果有可进行填写(无需重启即可生效)

***

### 4.权限系统
* 采用`.json`文件格式存储数据,文件位于`./PracticalWidgets/perm`
* 管理员数据`admin.json`
* 黑名单数据`blackList.json`
* 群权限`EnableGroup.json`决定群是否启用某功能
* 用户权限`GroupMemberPerm.json`决定用户在某群是否拥有某功能权限
* 首次启动会创建, 如某一段数据损坏可删除这一段后重启, 会进行自行修复
* 支持实时修改, 手动更改后无需重启

#### a.指令
* `/perm [groupID] [memberID] <type> <true | false>`  控制群或成员权限
* `/bl <add | rm> <groupID | memberID>`  添加或删除黑名单

***PS: `groupID` 为填写群号, 且必须在群号前添加 `g` 否则识别为成员QQ号  例如`groupID=g1003931532`***

#### b.用法
* `[...]` 内为可填内容, `<...>` 内为必填内容
* `groupID` 为群号, `memberID` 为成员QQ号
* 若 `[groupID]` `[memberID]` 均不存在 则控制当前群聊的权限  即`EnableGroup.json`
* 若只存在 `[groupID]` 则控制群号为 `groupID` 的群聊权限  即`EnableGroup.json`
* 若只存在 `[memberID]` 则控制当前群聊下, QQ号为 `memberID` 的群成员权限  即`GroupMemberPerm.json`
* 若 `[groupID]` `[memberID]` 均存在 则控制群号为 `groupID` 下QQ号为 `memberID` 的群成员权限 即`GroupMemberPerm.json`

#### c.例子
* `/perm jrrp false` 关闭当前群聊的 `jrrp` 功能
* `/perm g1003931532 jrrp true` 开启群号 `1003931532` 的 `jrrp` 功能
* `/perm 1250838250 sign false` 关闭当前群聊下 QQ号 `1250838250` 的 `sign` 功能
* `/perm g1003931532 1250838250 hyp true` 开启群号 `1003931532` 下 QQ号 `1250838250` 的 `hyp` 功能
* `/bl add g1003931532` 将群 `1003931532` 列入黑名单
* `/bl rm 1250838250` 将QQ号 `1250838250` 的用户 移出黑名单

***

### 5.对话
* 采用`.json`文件格式存储聊天记录,文件位于`./PracticalWidgets/TidewhisperScrolls.json`
* 根据不同的用户保留不同的对话记录, 以QQ号记录, 也就是可以跨群、跨环境(群聊, 私聊)

#### a.指令
* `@Bot` + `<content>` 触发聊天
***PS: `@Bot`后会自动添加一个空格, 请勿删除***
* `/保存聊天记录` 将您自己的保存至文件
* `/清理聊天记录` 删除您自己的聊天记录
* `/清理全部聊天记录` 删除全部聊天记录, 仅管理员可执行 `./PracticalWidgets/perm/admin.json`
* `/system` + `<content>` 定义Bot对您的人设

#### b.例子
* `@Bot 你好` 触发聊天
* `/system 请你扮演游戏<原神>中一个叫做心海的角色，并以心海的语气和习惯来和我对话。` 定义Bot对您的人设, 同时这也是默认人设

#### c.提示
* `/清理聊天记录`会重置您的人设
* `/system`定义人设前建议先清理聊天记录, 避免人设不准确问题



## 三.更新日志

| 版本号                | 发布时间       | 更新内容                                                                                                                                                                                                                                                                                                                                                                                  |
|--------------------|------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| V0.1.0             | 2023.02.02 | 首个打包好地插件，拥有其主要功能 **[今日人品;舔狗日记]**                                                                                                                                                                                                                                                                                                                                                      |
| V0.1.1             | 2023.02.02 | 完善代码,修改写法,增加 **今日人品排行榜**功能                                                                                                                                                                                                                                                                                                                                                            |
| V0.1.2             | 2023.02.05 | <li>修复 **今日人品排行榜** 非固定时间重置BUG 现在为 **每次开机** 和 **每天0点** 自动重置</li><br/><li>新增music目录,为新功能做准备</li><br/><li>关于 **API** 锁死问题以及 **排行榜建议合并转发** 已经纳入规划，未来版本解决</li>                                                                                                                                                                                                                             |
| V0.1.3             | 2023.02.07 | <li>修复 **今日人品排行榜** 显示来自何群信息bug</li><br/><li>改为**合并转发**方式发送排行榜 (来自[MiraiForum](https://mirai.mamoe.net/)中[@FIREFAIRY](https://mirai.mamoe.net/user/firefairy)提出的建议)</li><br/><li>***此版本建议尽早更新！此版本建议尽早更新！此版本建议尽早更新！***</li>                                                                                                                                                             |
| V0.1.4             | 2023.02.05 | <li>修复 **舔狗日记** @用户未转义问题</li><br/><li>新增配置文件，文件目录 `./PracticalWidgets/data.properties` 为将来做准备</li><br/><li>现有BUG 如果 **今日人品排行榜** 重复则排行榜将会报错问题 预计下版本(V0.1.5)修复</li><br/><li>**今日人品排行榜**将会更换写法，做到区分群聊发送(获取当前群号然后遍历此群的今日人品做排行，这取决于用户在哪个群查看的**今日人品**)</li><br/><li>将就着先用着吧，靖暄这边高三应届毕业生，我尽力快点换写法</li>                                                                                       |
| V0.1.5             | 2023.02.09 | <li>配置文件增加用户自定义 `今日人品形容词`</li><br/><li>修复 `V0.1.4`版本 排行榜bug</li><br/><li>修复 `舔狗日记` 无法获取到数据导致数据库报错问题</li><br/><li>更换 `今日人品排行榜` 写法, 现在可以做到分群发送 排行榜 每个群有不同的排行榜数据</li><br/><li>更换 `舔狗日记` 获取写法</li><br/><li>解决数据库乱码问题</li><br/><li>代码更为完善</li>                                                                                                                                             |
| V0.2.0             | 2023.02.09 | <li>新功能 `点歌` (欢呼!)</li><br/><li>完善上版本代码</li>                                                                                                                                                                                                                                                                                                                                          |
| V0.2.5             | 2023.02.15 | <li>增加新功能 `new对象`</li><br/><li>完善上版本代码</li>                                                                                                                                                                                                                                                                                                                                           |
| V0.2.6             | 2023.02.17 | <li>修复 `今日人品排行榜` 转发信息时锁死末酱, 现改为自动获取到群bot的昵称</li>                                                                                                                                                                                                                                                                                                                                      |
| V0.3.0             | 2023.02.19 | <li>优化上版本代码</li><br/><li>新增 `询hypixel服务器相关信息` 功能</li><br/><li>配置文件更新</li>                                                                                                                                                                                                                                                                                                             |
| V0.3.1             | 2023.02.21 | <li>修复数据库异常过大问题</li><br/><li>完善 `查询hypixel服务器相关信息` 功能</li><br/><li>主数据库更新, 新增版本条目, 方便查看</li>                                                                                                                                                                                                                                                                                          |
| V0.3.2             | 2023.02.22 | <li>经用户反馈, 修复 `今日人品排行榜` 经常缺人问题</li><br/><li>新增即便是在不同群也能查看到自己的人品值排行榜(在群A获取人品值后在群B再获取一次人品值就会写入数据库保留群B的排行数据)</li>                                                                                                                                                                                                                                                                        |
| V0.3.3             | 2023.02.24 | <li>修复 `hyp相关信息` 中 `玩家不存在` 或 `数据不存在` 的报错 或 直接不给回复 问题 (来自[MiraiForum](https://mirai.mamoe.net/)中[@MC__luoluo](https://mirai.mamoe.net/user/mc__luoluo)提出的反馈)</li><br/><li>修复 `自动更新数据库` 问题 (如果跨多个版本更新可能导致需要重启多次才会完整更新数据库功能)</li><br/><li>预计下版本更新 `hyp相关信息` 中添加更多游戏模式</li>                                                                                                               |
| V0.3.4             | 2023.02.27 | <li>修复 `hyp相关信息` 中 `玩家存在` 但 `玩家数据不存在` 得报错, 现在拥有回复, 且不会在控制台报错 (来自[MiraiForum](https://mirai.mamoe.net/)中[@MC__luoluo](https://mirai.mamoe.net/user/mc__luoluo)提出的反馈)</li><br/><li>修复 `今日人品排行榜` 中 如果群成员试图 逆天改命 进行重复查询 `今日人品` 导致排行榜数据过大问题 (来自[GitHub](https://www.github.com/)中[@光影](https://github.com/DUXING130))</li>                                                                 |
| V0.4.0             | 2023.02.28 | <li>修复 `hyp相关信息` 中 大部分BUG  现在很少遇到报错, 都带有提示</li><br/><li>**以下内容全部来自[MiraiForum](https://mirai.mamoe.net/)中[@MC__luoluo](https://mirai.mamoe.net/user/mc__luoluo)提出**</li><br/><li>新增 `自定义指令前缀` 可在配置文件自定义您的指令前缀 默认 `/`</li><br/><li>`hyp相关信息` 中 `player` 字段 新增显示 `大厅等级` 保留三位小数</li><br/><li>`hyp相关信息` 中 `player` 字段 新增显示 `RANK`</li><br/><li>`hyp相关信息` 中 `player` 字段 新增显示 `玩家皮肤预览`</li> |
| V0.4.1             | 2023.03.06 | <li>`hyp信息查询` 中 新增 `街机游戏` 模块 `/hyp acd <ID>`  包含了绝大多数的街机游戏模式</li><br/><li>`hyp信息查询` 中 `player` 字段 新增 `rank赠送数`</li><br/><li>修复 `hyp信息查询` 中 `player` 字段缺少 `【MVP++】` 问题</li>                                                                                                                                                                                                            |
| V0.4.2 - Alpha     | 2023.03.25 | ***该版本为 `V0.4.2测试版` 即上版本的修复版本, 缺少内容待下版本更新***<br/><li>修正 `hyp信息查询` 中 `MVP++` 判断错误问题</li><br/><li>为 `hyp信息查询` 中 新增 `<type>` 字段错误提醒</li><br/><li>为 `hyp信息查询` 功能预留文件夹, 用于新模式添加</li><br/><li>按照 [@MC__luoluo](https://mirai.mamoe.net/user/mc__luoluo) 要求 新增退群提醒 **(配置文件有更新)**</li>                                                                                                        |
| V0.4.2 - Alpha - 2 | 2023.03.31 | ***该版本为 `V0.4.2测试版2` 即上版本的 新功能预发布 版本, 可能存在严重BUG***(这次细心了, 不出意外的话是不会有bug的, 但是不出意外的话肯定会出意外)<li>新功能 `签到` 但是没有什么实际作用, 属于是按照 [MiraiForum](https://mirai.mamoe.net/) 中 [De6ris](https://mirai.mamoe.net/user/de6ris) 的要求测试 `自定义消息结构` 详细可查看配置文件</li>                                                                                                                                         |
| V0.4.3             | 2023.09.24 | <li>新增可控的退群提醒, 详情见配置文件更新</li><br><li>完善了几个测试版</li><br><li>修复已知bug</li><br><li>`hyp信息查询` 中 新增 `密室杀手` 模块</li>                                                                                                                                                                                                                                                                           |
| V0.5.0             | 2023.10.08 | <li>新增权限系统</li></br><li>新增可选的自动注册功能, 存在于`EnableGroup.json`中</li></br><li>修复已知bug</li></br> ***这个版本的bug请及时反馈给靖暄!***                                                                                                                                                                                                                                                                    |
| V0.5.1             | 2023.10.10 | <li>修复权限文件无法自动创建问题</li></br><li>修复配置文件跨版本更新插件时无法更新配置文件问题</li></br><li>新增分群控制退群提醒</li></br>                                                                                                                                                                                                                                                                                            |
| V0.5.2             | 2024.02.06 | <li>优化 `点歌功能` 详见[issues1](https://github.com/jxmm52547/Practical-Widgets/issues/1)</li><br><li>`hyp信息查询` 中 新增 `公会数据`</li><br><li>`hyp信息查询` 中 新增 `决斗游戏` 模块</li>                                                                                                                                                                                                                        |
| V0.6.0             | 2024.05.11 | <li>完成所有[issues](https://github.com/jxmm52547/Practical-Widgets/issues)中的问题</li>                                                                                                                                                                                                                                                                                                      |
| V0.6.1             | 2025.05.15 | <li>新增对话功能</li><br><li>迭代配置文件更新功能</li>                                                                                                                                                                                                                                                                                                                                                |

## 四.关于

如果您发现BUG或对本插件有任何建议欢迎在<a href="../../issues">issues</a>提出

**如果你也觉得 *Practical-Widgets* 做的好的话可以给开发者赞助(备注上您的任意 平台主页地址 及 ID 将会列入赞助列表)**

<details>
<summary>给靖暄的赞助方式如下</summary>
<picture>
  <img src="https://github.com/jxmm52547/Practical-Widgets/assets/84485811/86377f31-c0cf-4994-bdb0-00ed88de4861" style="visibility:visible;max-width:100%;">
  <img src="https://github.com/jxmm52547/Practical-Widgets/assets/84485811/0e015f2d-3a3c-4d33-82b0-0d4ac04a94de" style="visibility:visible;max-width:100%;">
  <img src="https://github.com/jxmm52547/Practical-Widgets/assets/84485811/e1adb785-db5d-45bf-a08f-781674e745fc" style="visibility:visible;max-width:100%;">
  <img src="https://github.com/jxmm52547/Practical-Widgets/assets/84485811/43812bb4-02a9-4505-97ef-75832683b2f3" style="visibility:visible;max-width:100%;">
</picture>
</details>
<details>
<summary>给洛洛的赞助方式</summary>
<a href="https://afdian.net/a/MC__luoluo">爱发电</a>
</details>

