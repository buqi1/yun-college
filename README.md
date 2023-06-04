# yun-college（云学院）
在线教育系统，分为前台网站系统和后台运营平台，B2C模式。
前台用户系统包括课程、讲师、问答、文章几大大部分，使用了微服务技术架构，前后端分离开发。

后端的主要技术架构是：``SpringBoot + SpringCloud + MyBatis-Plus + HttpClient + MySQL + Maven+EasyExcel+ nginx``

前端的架构是：``Node.js + Vue.js +element-ui+NUXT+ECharts``

其他涉及到的中间件包括Redis、阿里云OSS、阿里云视频点播,
业务中使用了ECharts做图表展示，使用EasyExcel完成分类批量添加、注册分布式单点登录使用了JWT,
也实现了微信登录，微信支付的功能
