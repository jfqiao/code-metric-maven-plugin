### code-metric maven插件使用说明

1. 依赖： 插件依赖于code-metric的jar包，务必保证code-metric的jar包能在maven的仓库中可以找到（并且按照maven要求的方式在相应的目录下）。由于当前code-metric一般在本地，请将code-metric安装在本地仓库中。

2. 安装：克隆插件的项目后，在项目的跟目录下运行安装命令即可。

   ```shell
   $ mvn clean install
   ```

3. 运行：运行插件分析项目时，可以分析整个项目，也可以只分析项目的子模块，进入要分析的项目的根目录（即pom.xml文件所在目录），运行以下命令即可。

   ```shell
   $ mvn clean install # 分析项目前务必编译打包原始项目，可以跳过测试。
   # -Dmaven.test.skip=true
   # 如果打包失败，则不可运行插件分析项目
   $ mvn com.alibaba:codemetric-maven-plugin:calculate  #开始分析项目
   ```

   分析的结果在每个子模块的根目录下。