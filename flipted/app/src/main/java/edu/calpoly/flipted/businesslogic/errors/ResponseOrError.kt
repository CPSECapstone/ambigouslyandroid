package edu.calpoly.flipted.businesslogic.errors

class ResponseOrError<T> private constructor(
        val response: T?,
        val error: BackendError?,
        val isError: Boolean
) {
   companion object {
       fun <T> withResponse(response: T) = ResponseOrError(response, null,false)
       fun <T> withError(error: BackendError) = ResponseOrError<T>(null, error, true)
   }

}