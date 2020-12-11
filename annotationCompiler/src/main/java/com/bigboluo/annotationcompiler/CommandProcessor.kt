package com.bigboluo.annotationcompiler

import com.bigboluo.annotation.Command
import com.google.auto.service.AutoService
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import java.io.File
import java.lang.reflect.ParameterizedType
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement
import javax.lang.model.util.Elements
import kotlin.reflect.KClass

@AutoService(Processor::class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
class CommandProcessor : AbstractProcessor() {

    private lateinit var clazzName: String
    private lateinit var elementUtils: Elements

    private val MODULE_NAME_KEY = "MODULE_NAME"
    private val PACKAGE_NAME = "com.custom.commands"

    override fun init(processingEnv: ProcessingEnvironment) {
        super.init(processingEnv)
        elementUtils = processingEnv.elementUtils
        // TODO: 2020/12/11 处理下
        clazzName = processingEnv.options[MODULE_NAME_KEY]!! + "_CommandsSet"
    }

    override fun process(
        annotations: MutableSet<out TypeElement>,
        roundEnv: RoundEnvironment
    ): Boolean {
        val annotationSet = roundEnv.getElementsAnnotatedWith(Command::class.java)

        if (annotationSet.isEmpty())
            return true

        val returnType = Map::class.asClassName()
            .parameterizedBy(
                String::class.asClassName(),
                KClass::class.asClassName().parameterizedBy(WildcardTypeName.producerOf(ANY))
            )

        val funSpecBuilder = FunSpec.builder("getAllCommand")
            .returns(returnType)
            .addStatement("val map:MutableMap<String,KClass<*>> = HashMap()")

        annotationSet.forEach {
            val typeElement = it as TypeElement
            funSpecBuilder.addStatement("map[\"${typeElement.simpleName}\"] = ${typeElement.qualifiedName}::class")
        }
        funSpecBuilder.addStatement("return map")

        val clazzTypeSpecBuilder = TypeSpec.objectBuilder(clazzName)
            .addFunction(funSpecBuilder.build())

        FileSpec.builder(PACKAGE_NAME, clazzName)
            .addImport("kotlin.reflect", "KClass")
            .addType(clazzTypeSpecBuilder.build())
            .build()
            .writeFile()

        return true
    }

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        return mutableSetOf(Command::class.java.canonicalName)
    }

    private fun FileSpec.writeFile() {
        val kaptKotlinGeneratedDir = processingEnv.options["kapt.kotlin.generated"]
        val outputFile = File(kaptKotlinGeneratedDir).apply {
            mkdirs()
        }
        writeTo(outputFile)
    }

}