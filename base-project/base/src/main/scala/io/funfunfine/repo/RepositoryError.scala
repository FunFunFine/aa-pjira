package io.funfunfine.repo

case class RepositoryError(cause: Throwable) extends RuntimeException(cause)
