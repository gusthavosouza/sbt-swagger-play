package com.bokun

import io.swagger.v3.oas.integration.SwaggerConfiguration
import io.swagger.v3.oas.models.OpenAPI

import scala.collection.mutable

class ApiListingCache(
  scanner: PlayApiScanner,
  config: PlaySwaggerConfig
) extends Loggable {
  private val cache: mutable.Map[String, OpenAPI] = mutable.Map.empty

  def listing(host: String): OpenAPI = {
    cache.getOrElseUpdate(
      host, {

        val swagger = config.applyTo(new OpenAPI())
        val swaggerConfig = new SwaggerConfiguration()

        swaggerConfig.setReadAllResources(false)

        logger.info("")

        val reader = PlayReader.init(swaggerConfig.openAPI(swagger), scanner.routes);

        reader.read(scanner.classes())
      }
    )
  }
}
