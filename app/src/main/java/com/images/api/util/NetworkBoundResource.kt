package com.images.api.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/**
 * Created by ZEESHAN on 07,July,2021
 */

inline fun <ResultType, RequestType> getLocalElseFromNetwork(
    crossinline query: suspend () -> Resource<ResultType>,
    crossinline fetch: suspend () -> Resource<RequestType>,
    crossinline saveFetchResult: suspend (RequestType) -> Unit, // This will take data from repo,  and save it into local storage.
    crossinline shouldFetch: (Resource<ResultType>) -> Boolean = { true }
) = flow {
    val queryData =
        query() // get data from local storage, based on this result we make decision for update

    if (shouldFetch(queryData)) {
        val apiFetchedData =
            fetch() // get from remote
        val result = if (apiFetchedData.isSuccessful) {
            saveFetchResult(apiFetchedData.data!!)  // successfully fetched data from api, save it into local storage
            query() // get saved data from db, single source of truth
        } else {
            Resource.Failure(
                apiFetchedData.apiError ?: emptyException()
            ) // unable to fetch from remote
        }
        emit(result)
    } else {
        if (queryData.isSuccessful) {
            emit(queryData) // send local data back
            /*val apiFetchedData = fetch()
            if (apiFetchedData.isSuccessful) {
                saveFetchResult(apiFetchedData.data!!) // successfully fetched data from api, save it into local storage
            }*/
        } else {
            val apiFetchedData =
                fetch() // get from remote
            val result = if (apiFetchedData.isSuccessful) {
                saveFetchResult(apiFetchedData.data!!)  // successfully fetched data from api, save it into local storage
                query() // get saved data from db, single source of truth
            } else {
                Resource.Failure(
                    apiFetchedData.apiError ?: emptyException()
                ) // unable to fetch from remote
            }
            emit(result)
        }
    }

}.flowOn(Dispatchers.IO)
