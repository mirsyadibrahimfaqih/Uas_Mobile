package com.irsyad.filmku.data.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class PopulerResponse(
	@field:SerializedName("page")
	val page: Int? = null,

	@field:SerializedName("total_pages")
	val totalPages: Int? = null,

	@field:SerializedName("results")
	val results: List<ResultsItem?>? = null,

	@field:SerializedName("total_results")
	val totalResults: Int? = null
) : Parcelable {

	override fun toString(): String {
		return "PopulerResponse(page=$page, totalPages=$totalPages, totalResults=$totalResults, results=$results)"
	}
}

@Parcelize
data class ResultsItem(
	@field:SerializedName("overview")
	val overview: String? = null,

	@field:SerializedName("original_language")
	val originalLanguage: String? = null,

	@field:SerializedName("original_title")
	val originalTitle: String? = null,

	@field:SerializedName("video")
	val video: Boolean? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("genre_ids")
	val genreIds: List<String>? = null,

	@field:SerializedName("poster_path")
	val posterPath: String? = null,

	@field:SerializedName("backdrop_path")
	val backdropPath: String? = null,

	@field:SerializedName("release_date")
	val releaseDate: String? = null,

	@field:SerializedName("popularity")
	val popularity: Double? = null,

	@field:SerializedName("vote_average")
	val voteAverage: Double? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("adult")
	val adult: Boolean? = null,

	@field:SerializedName("vote_count")
	val voteCount: Int? = null
) : Parcelable {

	override fun toString(): String {
		return "ResultsItem(overview=$overview, originalLanguage=$originalLanguage, originalTitle=$originalTitle, video=$video, title=$title, genreIds=$genreIds, posterPath=$posterPath, backdropPath=$backdropPath, releaseDate=$releaseDate, popularity=$popularity, voteAverage=$voteAverage, id=$id, adult=$adult, voteCount=$voteCount)"
	}
}
