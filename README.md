# ViewJSON
#### 介绍
JSON格式化工具类

#### 软件架构
swing + flatlaf + rsyntaxtextarea

#### 框架层需要解决的问题
1. 解决事件绑定的分离，页面结构分离，形成框架分离
2. 解决配置文件获取,配置保存✌️
3. 解决打包后的日志打印问题,方便打包后的调试
4. 封装自己的组件扫描机制,IOC容器,依赖注入，组件之间解耦合
   
#### 产品层面需要完善的清单(第一版)
1. 记录格式化生成次数，显示当前时间
2. 对json数据进行json类型检验
3. 点击格式化按钮新增选项卡〔用MD5对json数据进行签名，如果第一次按钮和第二次按钮生成的md5值相同，则不生成新的选项卡，否则，生成新的选项卡，展示格式化后的数据〕
4. 对右侧json对象的关键字颜色进行调整优化，冒号后面加空格✌️
5. 左侧功能区，增加压缩按钮✌️
6. 右侧增加行号✌️
7. 左右两侧实行宽窄可变的布局样式✌️
8. 主题可更换✌️
9. 是否可换行，可配置✌️
10. 结合市面上所有的json工具，优化布局，优化功能✌️
11. 增加搜索功能

#### 产品层面需要完善的清单(第二版)
1. 主题可更换✌️
2. 增加json树结构
3. 增加选项卡功能(内容MD5值检验)
4. 底部增加呼吸灯效果
5. 主要按钮操作绑定快捷键
6. 格式化后的json保存图片功能✌️
7. 增加XML格式化
8. 增加输入框回退功能（ctrl+z 和 ctrl + shift + z）
9. 增加配置 json是否可换行支持✌️
10. 搜索功能完善
11. 优化代码结构，MVC分层
12. 打包exe

#### 产品层面需要完善的清单(第三版)
1. 工具条新增新建按钮，点击新建时，增加新的选项卡(选项卡竖排，可关闭，类似于plsql界面)
2. 删除左侧富文本框，增加JSON树显示
3. 系统托盘驻留功能
4. 最下方显示，工具格式化次数。菜单栏新增菜单显示IO操作统计(软件打开次数，格式化次数，保存文件次数，保存图片次数，软件打开时长〔打开到关闭的时间汇总〕)
5. 搜索功能
6. 窗口大小本地文件记录
7. 增加复制图片功能
8. 报文分享为二维码
9. 增加按钮栏工具是否显示，某个按钮是否显示功能
10. 新发现Bug，1200行以上json报错图片报错



>作者：莫斐鱼
> 
>2023年11月23日
