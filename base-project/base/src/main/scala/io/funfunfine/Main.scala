package io.funfunfine

import zhttp.http._
import zhttp.service._
import zhttp.service.server.ServerChannelFactory
import zio._
import zio.config._
import zio.stream._
import io.funfunfine.api._
import io.funfunfine.config.Configuration._
import io.funfunfine.healthcheck._
import io.funfunfine.repo._
import io.funfunfine.service._
import doobie.*
import doobie.implicits.given
import zio.interop.catz.given
import zio.interop.catz.*
import zio.interop.*
import cats.Monad
import cats.effect.kernel.Async
object Main extends ZIOAppDefault:

  private val repoLayer = ItemRepositoryLive.layer

  private val serviceLayer = ItemServiceLive.layer

  private def transactor(implicit m: Async[Task]): Transactor[Task] = 
    Transactor.fromDriverManager[Task](
      "org.postgresql.Driver",     // driver classname
      "jdbc:postgresql:world",     // connect URL (driver-specific)
      "postgres",                  // user
      "pwd"                        // password
    ) 

  val routes =
    HttpRoutes.app ++
      Healthcheck.routes

  val program =
    for
      config <- getConfig[ServerConfig]
      _      <- Server.start(config.port, routes)
    yield ()

  override val run =
    ZIO.runtime.flatMap( (r: Runtime[Any]) =>
        program.provide(ServerConfig.layer, serviceLayer, repoLayer, ZLayer.succeed(transactor(asyncRuntimeInstance(r))))
      )