package com.example.giphycodingchallenge.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class Images( @SerializedName("fixed_height_still") val fixedHeightStill: ImageFormat,
              @SerializedName("original_still") val originalStill: ImageFormat,
              @SerializedName("fixed_width") val fixedWidth: ImageFormat,
              @SerializedName("fixed_height_small_still") val fixedHeightSmallStill: ImageFormat,
              @SerializedName("fixed_height_downsampled") val fixedHeightDownsampled: ImageFormat,
              @SerializedName("preview") val preview: ImageFormat,
              @SerializedName("fixed_height_small") val fixedHeightSmall: ImageFormat,
              @SerializedName("downsized_still") val downsizedStill: ImageFormat,
              @SerializedName("downsized") val downsized: ImageFormat,
              @SerializedName("downsized_large") val downsizedLarge: ImageFormat,
              @SerializedName("fixed_width_small_still") val fixedWidthSmallStill: ImageFormat,
              @SerializedName("preview_webp") val previewWebp: ImageFormat,
              @SerializedName("fixed_width_still") val fixedWidthStill: ImageFormat,
              @SerializedName("fixed_width_small") val fixedWidthSmall: ImageFormat,
              @SerializedName("fixed_width_downsampled") val fixedWidthDownsampled: ImageFormat,
              @SerializedName("downsized_medium") val downsizedMedium: ImageFormat,
              @SerializedName("original") val original: ImageFormat,
              @SerializedName("fixed_height") val fixedHeight: ImageFormat,
              @SerializedName("looping") val looping: ImageFormat,
              @SerializedName("original_mp4") val originalMp4: ImageFormat,
              @SerializedName("preview_gif") val previewGif: ImageFormat,
              @SerializedName("480w_still") val wStill480: ImageFormat) : Parcelable