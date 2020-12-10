package com.bigboluo.annotationcompiler

import com.google.auto.service.AutoService
import com.squareup.kotlinpoet.*
import java.io.File
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement
import javax.lang.model.util.Elements
import com.bigboluo.annotation.MyClass
import javax.lang.model.AnnotatedConstruct
import javax.lang.model.element.ElementVisitor
import javax.lang.model.type.TypeMirror
import javax.lang.model.type.TypeVisitor


@AutoService(Processor::class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
class MyProcessor : AbstractProcessor() {

    private lateinit var mLogger: Logger
    private var elementUtils: Elements? = null


    override fun getSupportedAnnotationTypes(): Set<String> {
        return setOf(MyClass.MyClazz::class.java.canonicalName,MyClass.findView::class.java.canonicalName)
    }

    override fun init(processingEnv: ProcessingEnvironment) {
        super.init(processingEnv)
        mLogger = Logger(processingEnv.messager)
        elementUtils = processingEnv.elementUtils

        mLogger.info(" ${this::class.java.simpleName} init")
    }

    override fun process(annotations: MutableSet<out TypeElement>, roundEnv: RoundEnvironment): Boolean {
        mLogger.info("processor start")

        val elements = roundEnv.getElementsAnnotatedWith(MyClass.MyClazz::class.java)
        elements.forEach {
            val typeElement = it as TypeElement
            val members = elementUtils!!.getAllMembers(typeElement)

//            val bindFunBuilder = FunSpec.builder("bindView").addParameter("activity", typeElement.asClassName()).addAnnotation(JvmStatic::class.java)
            val bindFunBuilder = FunSpec.builder("bindView").addParameter("activity", typeElement.asClassName()).addAnnotation(JvmStatic::class.java)

            members.forEach {
                val find: MyClass.findView? = it.getAnnotation(MyClass.findView::class.java)
                if (find != null) {
                    mLogger.info("find annotation " + it.simpleName)
                    bindFunBuilder.addStatement("activity.${it.simpleName} = activity.findViewById(${find.value})")
                }
            }
            val bindFun = bindFunBuilder.build()


            val file = FileSpec.builder(getPackageName(typeElement), it.simpleName.toString()+"_bindView")
                    .addType(TypeSpec.classBuilder(it.simpleName.toString()+"_bindView")
                            .addType(TypeSpec.companionObjectBuilder()
                                    .addFunction(bindFun)
                                    .build())
                            .build())
                    .build()
            file.writeFile()
        }

        mLogger.info("end")

        return true
    }


    private fun getPackageName(type: TypeElement): String {
        return elementUtils!!.getPackageOf(type).qualifiedName.toString()
    }

    private fun FileSpec.writeFile() {
        val kaptKotlinGeneratedDir = processingEnv.options["kapt.kotlin.generated"]
        val outputFile = File(kaptKotlinGeneratedDir).apply {
            mkdirs()
        }
        writeTo(outputFile)
    }
}