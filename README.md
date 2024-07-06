#### 介绍
基于Springboot的校园二手交易平台的设计与实现

#### 软件架构
- Springboot+vue+MySQL

#### 部署系统必备
- JDK（1.8版本,并配置系统环境变量）
- MySQL（5.7版本）
- Navicat for MySQL（可视化）
- Maven（换阿里源，管理Jar包）
- Lombok插件（简化实体类）
- 本项目采用IDEA开发
  
#### 其它
- 数据库里的账号密码默认都是 123456，进行过加盐加密
- 超级管理员账号密码：admin  123456

### 已有功能

#### 用户：
  1. 登录注册：登录、注册、忘记密码模块
  2. 商品模块：发布商品，修改商品，商品详情界面，商品搜索界面
  3. 求购模块：发布求购信息，修改求购信息，求购信息列表，搜索信息界面
  4. 收藏模块：收藏商品或取消收藏，分页查看所有收藏内容，查看商品收藏状态
  5. 举报模块：举报商品或取消举报，修改举报信息，查看所有举报信息
  6. 订单模块：用户设置商品状态为未收货或已收货，用户查看售出记录，用户删除售出记录
  7. 支付模块：支付订单
  8. 个人中心：修改个人信息，修改密码，更换手机号
  9. 通知模块：商品审核结果通知、商品购买通知
  10. 聊天模块：可以进行在线聊天
  11. 网站公告：展示网站公告

#### 管理员：
  1. 管理员管理：管理管理员账号，分配管理员权限，重置密码 
  2. 用户管理：管理用户账号，更改用户账号状态，重置密码
  3. 审核管理：审核商品和求购信息，分页展示各类商品信息
  4. 商品分类：新增商品分类 ，修改商品分类，删除商品分类
  5. 商品管理：查询商品，修改商品信息，更改商品状态，商品详情文件导出
  6. 订单管理：查询商品订单，修改订单，删除订单
  7. 推荐管理：商品首页推荐，更改首页推荐
  8. 举报管理：展示举报信息，商品举报状态修改
  9. 公告模块：发布公告，公告详情的展示，修改公告及删除公告
  10. 销售分析：销售列表，销售数据分析
  11. 日志管理：展示所有管理员操作，删除记录



  #### 部分图片
  1.商品首页
![image](https://user-images.githubusercontent.com/46672076/110750258-2c425680-827d-11eb-866f-395e779aa527.png)
  
  2.商品详情
![image](https://user-images.githubusercontent.com/46672076/110750317-42e8ad80-827d-11eb-8999-32f8b9f4be70.png)
  
  3.个人中心
![image](https://user-images.githubusercontent.com/46672076/110750402-614ea900-827d-11eb-8ec0-230600cd73b3.png)
  
  4.在线聊天
![image](https://user-images.githubusercontent.com/46672076/110750750-e8038600-827d-11eb-91ed-2491d22251c0.png)


