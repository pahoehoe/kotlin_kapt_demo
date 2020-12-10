# kotlin_kapt_demo
kotlin kapt demo

### 调试方法 ###
##### 1.在as中的命令行输入命令:

gradlew --no-daemon -Dorg.gradle.debug=true -Dkotlin.daemon.jvm.options="-Xdebug,-Xrunjdwp:transport=dt_socket,address=5005,server=y,suspend=n" :app:clean :app:assemble

##### 2.在Edit Configureations-Templates-Remote中创建一个remote.  

注意端口号对应,并点击as中的调试按钮启动它.  

##### 3.打号断点并运行Build-Rebuild.等待执行到断点.  





<br>
### 遇到的问题  
##### annotationProcessors.json not found
删掉.gradle文件夹
  
  
<br/>  
### 资料

[java注解](https://juejin.cn/post/6844903477907324935#heading-6)<br>
[kotlin kapt调试](og.csdn.net/xx326664162/article/details/91456018)  
