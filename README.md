# kotlin_kapt_demo
kotlin kapt demo

### kapt断点调试方法 
##### 1.在as中的命令行输入命令:

gradlew --no-daemon -Dorg.gradle.debug=true -Dkotlin.daemon.jvm.options="-Xdebug,-Xrunjdwp:transport=dt_socket,address=5005,server=y,suspend=n" :app:clean :app:assemble

##### 2.在Edit Configureations-Templates-Remote中创建一个remote.  

注意端口号对应,并点击as中的调试按钮启动它.  

##### 3.打号断点并运行Build-Rebuild.等待执行到断点.  
<br>
<br>
<br>
<br>

### AbstractProcessor日志打印位置
通过processingEnv.messager输出的日志可以在as中的build输出中看到
<br>
<br>
<br>
<br>

### 给处理器传递参数
[java版本参数传递](https://blog.csdn.net/qq_19431333/article/details/89431065)
kotlin配置不同:
```
kapt {
    arguments {
        arg("module_name", "App")
    }
}
```
### 在kotlinpoet中写val myMap:Map<String, KClass<out Any>>

```
Map::class.asClassName()
    .parameterizedBy(
        String::class.asClassName(),
        KClass::class.asClassName().parameterizedBy(WildcardTypeName.producerOf(ANY))
    )
```
[How to set Map<String, KClass<out Any>> as property type?](https://stackoverflow.com/questions/55846678/how-to-set-mapstring-kclassout-any-as-property-type)
    

### 遇到的问题 
annotationProcessors.json not found.删掉.gradle文件夹就可以解决
<br>
<br>
<br>
<br>

### 资料

[java注解](https://juejin.cn/post/6844903477907324935#heading-6)<br>
[kotlin kapt调试](og.csdn.net/xx326664162/article/details/91456018)  
[Android 注解系列之APT工具（三）](https://juejin.cn/post/6844903701283340301#heading-7)  
[通过指定包名，扫描包下面包含的所有的ClassName](https://www.javatips.net/api/ARouter-master/arouter-api/src/main/java/com/alibaba/android/arouter/utils/ClassUtils.java)
[反射调用kotlin类的构造器](https://www.cnblogs.com/webor2006/p/11654178.html)
[反射调用Kotlin类里的Companion类函数or object类函数](https://juejin.cn/post/6844903729783635975)

