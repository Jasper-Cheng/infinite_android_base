# infinite_android_base

一个android脚手架项目,基于jetpack基础应用开发架构。

## Getting Started

DONE List:
1. 类似于MVVM架构(ViewModel、StateFlow、Compose)(没有用MVI架构是因为觉得Intent和State过于繁琐，采用StateFlow而不是LiveData是觉得StateFlow搭配协程更方便)
2. 数据持久化(room、datastore)
3. 权限(accompanist-permission)
4. 主题(light、dark)、字体、颜色适配
5. 屏幕密度、字体因子适配(CompositionLocalProvider)
6. 国际化(中文、英文)

TODO List:
1. fragment管理(navigation)
2. 通知(notifications)
3. 桌面小组件(Glance)
4. 应用报错、崩溃上报处理
5. android启动动画和自适应图标(splashscreen)l
6. 网络封装
7. android签名
8. 屏幕适配
9. websocket
10. 一键登录