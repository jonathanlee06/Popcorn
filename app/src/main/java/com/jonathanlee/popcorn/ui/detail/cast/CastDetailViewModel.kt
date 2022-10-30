package com.jonathanlee.popcorn.ui.detail.cast

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jonathanlee.popcorn.data.model.CastCredit
import com.jonathanlee.popcorn.data.model.CastDetail
import com.jonathanlee.popcorn.data.repository.CastRepository
import com.jonathanlee.popcorn.data.repository.Repository
import com.jonathanlee.popcorn.data.source.ApiResultState
import com.jonathanlee.popcorn.util.TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class CastDetailViewModel(
    private val castRepo: CastRepository = Repository.provideCastRepository()
) : ViewModel() {

    private val _castCredit = MutableLiveData<List<CastCredit>>()
    val castCredit: LiveData<List<CastCredit>> = _castCredit

    private val _castDetail = MutableLiveData<ApiResultState<CastDetail?>>()
    val castDetail: LiveData<ApiResultState<CastDetail?>> = _castDetail

    private val _castPhoto = MutableLiveData<ArrayList<String>>()
    val castPhoto: LiveData<ArrayList<String>> = _castPhoto

    fun getCastInfo(id: Int) {
        viewModelScope.launch {
            val requests = listOf(
                async { getCastPhoto(id) },
                async { getCastDetails(id) },
                async { getCastCredit(id) },
            )
            requests.awaitAll()
        }
    }

    private suspend fun getCastDetails(id: Int) {
        flow {
            emit(ApiResultState.Loading)
            val response = castRepo.fetchCastDetails(id)
            if (response.isSuccessful) {
                emit(ApiResultState.Success(response.body()))
            } else {
                val errorMsg = response.errorBody()?.string()
                response.errorBody()?.close()
                emit(ApiResultState.Failure(errorMsg))
            }
        }.flowOn(Dispatchers.IO).collect {
            _castDetail.value = it
        }
    }

    private suspend fun getCastCredit(id: Int) {
        flow {
            emit(ApiResultState.Loading)
            val response = castRepo.fetchCastCredit(id)
            if (response.isSuccessful) {
                emit(ApiResultState.Success(response.body()))
            } else {
                val errorMsg = response.errorBody()?.string()
                response.errorBody()?.close()
                emit(ApiResultState.Failure(errorMsg))
            }
        }.flowOn(Dispatchers.IO).collect { result ->
            when (result) {
                is ApiResultState.Loading -> {
                    // do loading
                }
                is ApiResultState.Success -> {
                    result.data?.cast?.let { creditList ->
                        _castCredit.value = creditList
                            .filter { !it.posterPath.isNullOrEmpty() }
                            .sortedByDescending { it.popularity }
                            .take(10)
                    }

                }
                is ApiResultState.Failure -> {
                    Log.d(TAG, "response error=${result.exception}")
                }
            }
        }
    }

    private suspend fun getCastPhoto(id: Int) {
        flow {
            emit(ApiResultState.Loading)
            val response = castRepo.fetchCastPhoto(id)
            if (response.isSuccessful) {
                emit(ApiResultState.Success(response.body()))
            } else {
                val errorMsg = response.errorBody()?.string()
                response.errorBody()?.close()
                emit(ApiResultState.Failure(errorMsg))
            }
        }.flowOn(Dispatchers.IO).collect { result ->
            when (result) {
                is ApiResultState.Loading -> {
                    // do loading
                }
                is ApiResultState.Success -> {
                    val pathList = ArrayList<String>()
                    result.data?.profiles?.forEach {
                        it.filePath?.let { it1 -> pathList.add(it1) }
                    }
                    _castPhoto.value = pathList

                }
                is ApiResultState.Failure -> {
                    Log.d(TAG, "response error=${result.exception}")
                }
            }
        }
    }

}