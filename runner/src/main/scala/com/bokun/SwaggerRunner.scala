package com.bokun

import io.swagger.v3.core.util.Json
import play.api.{Configuration, Environment, Mode}

import java.io.File
import java.util.{Map => JMap}
//import scala.jdk.CollectionConverters._
import scala.collection.JavaConverters._

object SwaggerRunner {

  // configuration should be an Option, but it does not play nice with reflective loading, so we use Java-style nullable map instead
  def run(rootPath: File, host: String, validate: Boolean, configuration: JMap[String, Any]): String = {
    val classLoader = getClass.getClassLoader
    val env = Environment(rootPath, classLoader, Mode.Dev)
    val conf = if (configuration == null) {
      Configuration.load(env)
    } else {

      Configuration.reference ++ Configuration.from(configuration.asScala.toMap)
    }
    val plugin = new SwaggerPluginImpl(env, conf)
    val swaggerModel = plugin.apiListingCache.listing(host)
    val json = Json.pretty(swaggerModel)
    json
  }
}
