package com.bokun

import java.util.logging.Logger


trait Loggable {
  val logger: Logger = java.util.logging.Logger.getLogger("swagger");
}
