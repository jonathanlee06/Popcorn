package com.jonathanlee.popcorn.ui.detail

import android.util.Log
import com.google.gson.Gson
import com.jonathanlee.popcorn.data.model.*
import com.jonathanlee.popcorn.data.model.network.CastListResponse
import com.jonathanlee.popcorn.data.model.network.GenreListResponse
import com.jonathanlee.popcorn.data.model.network.VideoListResponse
import com.jonathanlee.popcorn.data.repository.DetailRepository
import com.jonathanlee.popcorn.data.repository.Repository
import com.jonathanlee.popcorn.data.source.Api
import com.jonathanlee.popcorn.util.SharedPreferencesUtils
import kotlinx.coroutines.*

class DetailPresenter(
    private val view: DetailContract.View,
    private val detailRepository: DetailRepository = Repository.provideMovieDetailRepository(),
    private val scope: CoroutineScope
) : DetailContract.Presenter {

    init {
        view.presenter = this
    }

    companion object {
        private const val MOVIE_GENRE_LIST = "movieGenre"
    }

    override fun getDetails(contentDetails: ContentDetails, entry: Int) {
        scope.launch(Dispatchers.IO) {
            async { getBackdropImage(contentDetails.backdropPath) }
            async { getBackdropPoster(contentDetails.posterPath) }
            async { getCasts(contentDetails.contentId, entry) }
            async { getGenres(contentDetails.genreId) }
            async { getVideos(contentDetails.contentId, entry) }
        }
    }

    override fun getBackdropImage(path: String?) {
        scope.launch(Dispatchers.Main) {
            val fullPath = Api.getBackdropPath(path)
            view.setBackdropImage(fullPath)
        }
    }

    override fun getBackdropPoster(path: String?) {
        scope.launch(Dispatchers.Main) {
            val fullPath = Api.getPosterPath(path)
            view.setBackdropPoster(fullPath)
        }
    }

    override fun getCasts(id: Int, entry: Int) {
        scope.launch(Dispatchers.IO) {
            try {
                val request = if (entry == DetailActivity.ENTRY_FROM_MOVIE) {
                    detailRepository.fetchMovieCast(id)
                } else {
                    detailRepository.fetchTvCast(id)
                }
                if (request.isSuccessful) {
                    val result = request.body() as CastListResponse
                    Log.d("getCasts", "response success=${result.cast[0].profilePath}")
                    val castResult = result.cast as ArrayList<Cast>
                    withContext(Dispatchers.Main) {
                        view.setCasts(toCastItemList(castResult))
                    }
                } else {
                    Log.d("getCasts", "response error=${request.errorBody()}")
                }
            } catch (e: Exception) {
                Log.e("getCasts", "getGenres: error=${e.message}")
            }
        }
    }

    override fun getGenres(id: List<Int>) {
        scope.launch(Dispatchers.IO) {
            if (checkIfCacheEmpty()) {
                try {
                    val movieRequestDeferred = async { detailRepository.fetchMovieGenres() }
                    val tvRequestDeferred = async { detailRepository.fetchTvGenres() }
                    val movieRequest = movieRequestDeferred.await()
                    val tvRequest = tvRequestDeferred.await()
                    if (movieRequest.isSuccessful && tvRequest.isSuccessful) {
                        val movieResult = movieRequest.body()?.genres
                        val tvResult = tvRequest.body()?.genres
                        val joinResult = ArrayList<Genre>()
                        if (movieResult != null && tvResult != null) {
                            joinResult.apply {
                                addAll(movieResult)
                                addAll(tvResult)
                            }
                        }
                        val genreResult = GenreListResponse(genres = joinResult.distinct())
                        storeInCache(genreResult)
                        val fromCache = getFromCache(id)
                        withContext(Dispatchers.Main) {
                            view.setGenres(fromCache)
                        }
                    } else {
                        Log.d("getGenres", "response error=${movieRequest.errorBody()}")
                    }
                } catch (e: Exception) {
                    Log.e("getGenres", "getGenres: error=${e.message}")
                }
            } else {
                Log.d("getGenres", "getGenres from cache")
                withContext(Dispatchers.Main) {
                    view.setGenres(getFromCache(id))
                }
            }
        }
    }

    override fun getVideos(id: Int, entry: Int) {
        scope.launch(Dispatchers.IO) {
            try {
                val request = if (entry == DetailActivity.ENTRY_FROM_MOVIE) {
                    detailRepository.fetchVideos(id)
                } else {
                    detailRepository.fetchTvVideos(id)
                }
                if (request.isSuccessful) {
                    val result = request.body() as VideoListResponse
                    val videoResult = result.results as ArrayList<Video>
                    if (result.results.isNullOrEmpty()) {
                        withContext(Dispatchers.Main) {
                            view.setNoVideos()
                        }
                    } else {
                        videoResult.forEach {
                            it.thumbnailPath = Api.getYoutubeThumbnailPath(it.key)
                            it.videoPath = Api.getYoutubeVideoPath(it.key)
                        }
                        withContext(Dispatchers.Main) {
                            view.setVideos(videoResult)
                        }
                    }
                } else {
                    Log.d("getVideos", "response error=${request.errorBody()}")
                }
            } catch (e: Exception) {
                Log.e("getVideos", "getGenres: error=${e.message}")
            }
        }
    }

    private fun checkIfCacheEmpty(): Boolean {
        val cache = SharedPreferencesUtils.getString(MOVIE_GENRE_LIST)
        return cache.isEmpty()
    }

    private fun storeInCache(result: GenreListResponse) {
        val toJson = Gson().toJson(result)
        SharedPreferencesUtils.putString(MOVIE_GENRE_LIST, toJson)
    }

    private fun getFromCache(genreId: List<Int>): ArrayList<String> {
        val genreList = SharedPreferencesUtils.getString(MOVIE_GENRE_LIST)
        val serialized = Gson().fromJson(genreList, GenreListResponse::class.java)
        Log.d("getFromCache", "getFromCache response=${serialized}")
        val mappedGenre: ArrayList<String> = ArrayList()
        genreId.forEach { fromList ->
            serialized.genres.forEach { fromCache ->
                if (fromList == fromCache.id) {
                    mappedGenre.add(fromCache.name)
                }
            }
        }
        return mappedGenre
    }

    private fun toCastItemList(castList: ArrayList<Cast>): List<CastItem.Item>? {
        val list: MutableList<CastItem.Item>?
        if (castList.isNullOrEmpty()) {
            list = null
        } else {
            list = ArrayList()
            castList.forEach { cast ->
                list.add(CastItem.Item(cast))
            }
        }
        return list
    }
}