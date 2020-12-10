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
    }

    override fun process(
        annotations: MutableSet<out TypeElement>,
        roundEnv: RoundEnvironment
    ): Boolean {
        val elements = roundEnv.getElementsAnnotatedWith(Factory::class.java)

        //创建create方法
        val funSpecBuilder = FunSpec.builder("create")
            .addParameter("id", String::class.java)
            .beginControlFlow("if (id == null)")
            .addStatement("throw IllegalArgumentException(\"id is null!\")")
            .endControlFlow()
            .returns(Meal::class.java)

        var packageName:String? = null

        elements.forEach {
            packageName = elementUtil.getPackageOf(it).qualifiedName.toString()
            val annotation = it.getAnnotation(Factory::class.java)
            logger.info("factor id:" + annotation.id + " value:" + annotation.value)
            funSpecBuilder.addStatement(
                "if (\"${annotation.id}\".equals(id)) {\n" +
                        "      return ${annotation.value.simpleName}()\n" +
                        "    }"
            )
        }

        funSpecBuilder.addStatement("throw IllegalArgumentException(\"Unknown id = \" + id)")

        val typeSpecBuilder = TypeSpec.classBuilder("MyFactory")
            .addFunction(funSpecBuilder.build())

        FileSpec.builder(packageName!!,"MyFactory")
            .addType(typeSpecBuilder.build())
            .build().writeFile()
        return true
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
        return mutableSetOf(Factory::class.java.canonicalName)
    }
}