# onlineEdu
历时两周尚硅谷谷粒学院项目

1、lib文件夹中有数据库文件，自行导入。

2、在项目中application.properties文件都已经注掉，将每个service的模块配置文件同步到nacos配置中心，改成你相应的配置，主要改数据库和redis连接，oss和vod模块还需要改为你自己的阿里云AK。nacos压缩包传不上去，自行下载，解压后进入bin目录cmd命令
startup -m standalone 启动
![](https://cdn.jsdelivr.net/gh/xiaobubuya/image@second/eduOnlineQQ%E6%88%AA%E5%9B%BE20210630163251.png)

3、系统运行需要redis环境

4、启动service下所有的模块，在启动api_gateway模块


项目后台前端地址
https://github.com/xiaobubuya/onlineEdu-vue

项目前台前端地址
https://github.com/xiaobubuya/userEdu-vue