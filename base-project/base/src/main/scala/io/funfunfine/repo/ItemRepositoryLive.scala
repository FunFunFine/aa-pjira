package io.funfunfine.repo

import zio.{IO, ZLayer, ZIO, Task}
import io.funfunfine.domain.*
import doobie.*
import doobie.implicits.given
import javax.sql.DataSource
import zio.interop.catz.given

final class ItemRepositoryLive(ctx: Transactor[Task])
    extends ItemRepository:


  def add(description: String): IO[RepositoryError, ItemId] =
    sql"insert into items (description) $description".update
      .withUniqueGeneratedKeys[ItemId]("id")
      .transact(ctx)
      .mapError(e => RepositoryError(e))

  def delete(id: ItemId): IO[RepositoryError, Unit] =
    sql"delete from items where id = $id"
    .update
    .run
      .transact(ctx)
      .mapError(e => new RepositoryError(e))
      .unit

  def getAll(): IO[RepositoryError, List[Item]] =
    sql"select * from items"
      .query[Item]
      .to[List]
      .transact(ctx)
      .mapError(e => new RepositoryError(e))

  def getById(id: ItemId): IO[RepositoryError, Option[Item]] =
    sql"select * from items where id = $id"
      .query[Item]
      .option
      .transact(ctx)
      .map(_.headOption)
      .mapError(e => new RepositoryError(e))

  def update(item: Item): IO[RepositoryError, Unit] =
    sql"update table items where id = ${item.id} set description = ${item.description}"
    .update
    .run
      .transact(ctx)
      .mapError(e => new RepositoryError(e))
      .unit

object ItemRepositoryLive:

  val layer: ZLayer[Transactor[Task], Nothing, ItemRepository] =
    ZLayer(
      for xa <- ZIO.service[Transactor[Task]]
      yield ItemRepositoryLive(xa)
    )
