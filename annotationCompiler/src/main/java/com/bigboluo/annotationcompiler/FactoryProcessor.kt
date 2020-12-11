package com.bigboluo.annotationcompiler

import com.bigboluo.annotation.Factory
import com.bigboluo.annotation.Meal
import com.google.auto.service.AutoService
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.TypeSpec
import java.io.File
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement
import javax.lang.model.type.DeclaredType
import javax.lang.model.type.MirroredTypeException
import javax.lang.model.util.Elements

@AutoService(Processor::class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
class FactoryProcessor : AbstractProcessor() {

    lateinit var elementUtil: Elements
    lateinit var logger: Logger

    override fun init(processingEnv: ProcessingEnvironment) {
        super.init(processingEnv)
        elementUtil = processingEnv.elementUtils
        logger = Logger(processingEnv.messager)
        logger.info("FactoryProcessor init")
    }

    override fun process(
        annotations: MutableSet<out TypeElement>,
        roundEnv: RoundEnvironment
    ): Boolean {
        logger.info("FactoryProcessor process start")
        val elements = roundEnv.getElementsAnnotatedWith(Factory::class.java)

        if (elements.isEmpty()) {
            logger.info("elements is empty!")
            return true
        }

        //创建create方法
        val funSpecBuilder = FunSpec.builder("create")
            .addParameter("id", String::class)
            .beginControlFlow("if (id == null)")
            .addStatement("throw IllegalArgumentException(\"id is null!\")")
            .endControlFlow()
            .addStatement(" ")
            .returns(Meal::class.java)

        var packageName: String? = null

        elements.forEach {
            packageName = elementUtil.getPackageOf(it).qualifiedName.toString()
            val annotation = it.getAnnotation(Factory::class.java)
            funSpecBuilder.apply {
                beginControlFlow("if (\"${annotation.id}\".equals(id))")
                    .addStatement("return ${getTypeElementFromFactory(annotation)}()")
                    .endControlFlow()
                    .addStatement(" ")
            }
        }

        funSpecBuilder.addStatement("throw IllegalArgumentException(\"Unknown id = \" + id)")

        val typeSpecBuilder = TypeSpec.classBuilder("MyFactory")
            .addFunction(funSpecBuilder.build())

        FileSpec.builder(packageName!!, "MyFactory")
            .addType(typeSpecBuilder.build())
            .build().writeFile()
        return true
    }

    private fun logAnnotation(annotation: Factory) {
        //直接从annotation.value中拿值会报错.不知道为什么
        //可以中annotation.id中拿.

//        javax.lang.model.type.MirroredTypeException: Attempt to access Class object for TypeMirror com.bigboluo.kaptapplication.MyMeal
//        at com.sun.tools.javac.model.AnnotationProxyMaker$MirroredTypeExceptionProxy.generateException(AnnotationProxyMaker.java:308)
//        at com.sun.proxy.$Proxy120.value(Unknown Source)
//        at com.bigboluo.annotationcompiler.FactoryProcessor.process(FactoryProcessor.kt:48)
        try {
            logger.info("factor id:" + annotation.id + " value:" + annotation.value)
        } catch (e: MirroredTypeException) {
            e.let {
                (it.typeMirror as DeclaredType).asElement() as TypeElement
            }.run {
                logger.info("qualifiedName=$qualifiedName")
            }
        }
    }

    private fun getTypeElementFromFactory(anno: Factory): TypeElement? {
        try {
            //抛出异常吧!!!
            logger.log("anno.value=" + anno.value)
            return null
        } catch (e: MirroredTypeException) {
            val typeElement = (e.typeMirror as DeclaredType).asElement() as TypeElement;
            logger.log("factory 果然抛出了异常,返回TypeElement=$typeElement")
            return typeElement
        }
    }

    private fun getPackageName(type: TypeElement): String {
        return elementUtil!!.getPackageOf(type).qualifiedName.toString()
    }

    private fun FileSpec.writeFile() {
        val kaptKotlinGeneratedDir = processingEnv.options["kapt.kotlin.generated"]
        val outputFile = File(kaptKotlinGeneratedDir).apply {
            mkdirs()
        }
        writeTo(outputFile)
    }

//    override fun getSupportedSourceVersion(): SourceVersion {
//        return SourceVersion.RELEASE_8
//    }

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        logger?.info("canonicalName " + Factory::class.java.canonicalName)
        return mutableSetOf(Factory::class.java.canonicalName)
    }
}