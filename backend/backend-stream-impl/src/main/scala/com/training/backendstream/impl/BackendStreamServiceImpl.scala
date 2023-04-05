package com.training.backendstream.impl

import com.lightbend.lagom.scaladsl.api.ServiceCall
import com.training.backendstream.api.BackendStreamService
import com.training.backend.api.BackendService

import scala.concurrent.Future

/**
  * Implementation of the BackendStreamService.
  */
class BackendStreamServiceImpl(backendService: BackendService) extends BackendStreamService {
  def stream = ServiceCall { hellos =>
    Future.successful(hellos.mapAsync(8)(backendService.hello(_).invoke()))
  }
}
