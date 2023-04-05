package com.training.backendstream.impl

import com.lightbend.lagom.scaladsl.api.ServiceLocator.NoServiceLocator
import com.lightbend.lagom.scaladsl.server._
import com.lightbend.lagom.scaladsl.devmode.LagomDevModeComponents
import play.api.libs.ws.ahc.AhcWSComponents
import com.training.backendstream.api.BackendStreamService
import com.training.backend.api.BackendService
import com.softwaremill.macwire._

class BackendStreamLoader extends LagomApplicationLoader {

  override def load(context: LagomApplicationContext): LagomApplication =
    new BackendStreamApplication(context) {
      override def serviceLocator: NoServiceLocator.type = NoServiceLocator
    }

  override def loadDevMode(context: LagomApplicationContext): LagomApplication =
    new BackendStreamApplication(context) with LagomDevModeComponents

  override def describeService = Some(readDescriptor[BackendStreamService])
}

abstract class BackendStreamApplication(context: LagomApplicationContext)
  extends LagomApplication(context)
    with AhcWSComponents {

  // Bind the service that this server provides
  override lazy val lagomServer: LagomServer = serverFor[BackendStreamService](wire[BackendStreamServiceImpl])

  // Bind the BackendService client
  lazy val backendService: BackendService = serviceClient.implement[BackendService]
}
