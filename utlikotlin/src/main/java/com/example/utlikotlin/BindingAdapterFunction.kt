package com.example.utlikotlin

import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.View
import android.widget.*
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.button.MaterialButton
import com.google.android.material.button.MaterialButtonToggleGroup

@BindingAdapter("android:text", "isAddCase")
fun setText(editText: EditText, text: String, isAddCase: Boolean) {
    if (isAddCase) editText.setText("") else editText.setText(text)
}

@InverseBindingAdapter(attribute = "android:text")
fun getText(editText: EditText) = editText.text.toString().trim()

@BindingAdapter("imageIndex", "images")
fun setImageResource(imageView: ImageView, imageIndex: Int, images: TypedArray) {
    imageView.setImageResource(images.getResourceId(imageIndex, 0))
}

@BindingAdapter("wordIndex", "words")
fun setText(textView: TextView, wordIndex: Int, words: Array<String>) {
    textView.text = words[wordIndex]
}

@BindingAdapter("label", "wordIndex", "words")
fun setText(textView: TextView, label: String, wordIndex: Int, words: Array<String>) {
    val text = "$label: ${words[wordIndex]}"

    textView.text = text
}

@BindingAdapter("checkedIndex")
fun setChecked(toggleGroup: MaterialButtonToggleGroup, checkedIndex: Int) {
    (toggleGroup.getChildAt(checkedIndex) as MaterialButton).isChecked = true
}

@BindingAdapter("checkedIndexes")
fun setChecked(toggleGroup: MaterialButtonToggleGroup, checkedIndexes: List<Int>) {
    checkedIndexes.forEach {
        (toggleGroup.getChildAt(it) as MaterialButton).isChecked = true
    }
}

@BindingAdapter("imageUrl")
fun setImageResource(imageView: ImageView, imageUrl: String?) {
    imageUrl?.let {
        val uri = it.toUri().buildUpon().scheme("https").build()

        Glide.with(imageView.context).load(uri).into(imageView)
    }
}

@BindingAdapter("imageUrl", "placeHolderImage", "errorImage")
fun setImageResource(imageView: ImageView, imageUrl: String?, placeHolderImage: Drawable, errorImage: Drawable) {
    imageUrl?.let {
        val uri = it.toUri().buildUpon().scheme("https").build()

        Glide.with(imageView.context)
                .load(uri)
                .apply(RequestOptions()
                        .placeholder(placeHolderImage)
                        .error(errorImage))
                .into(imageView)
    }
}

@BindingAdapter("data")
fun <T> setData(recyclerView: RecyclerView, data: List<T>?) {
    (recyclerView.adapter as ListAdapter<T, RecyclerView.ViewHolder>).submitList(data)
}

@BindingAdapter("isShowDivider")
fun setDivider(recyclerView: RecyclerView, isShowDivider: Boolean) {
    if (isShowDivider) {
        recyclerView.addItemDecoration(DividerItemDecoration(recyclerView.context, DividerItemDecoration.VERTICAL))
    }
}

@BindingAdapter("loadingStatus", "loadingImage", "errorImage")
fun setImageResource(imageView: ImageView, loadingStatus: LoadingStatus?, loadingImage: Drawable, errorImage: Drawable) {
    when (loadingStatus) {
        LoadingStatus.LOADING -> {
            imageView.visibility = View.VISIBLE
            imageView.setImageDrawable(loadingImage)
        }

        LoadingStatus.ERROR -> {
            imageView.visibility = View.VISIBLE
            imageView.setImageDrawable(errorImage)
        }

        LoadingStatus.DONE -> {
            imageView.visibility = View.GONE
        }
    }
}

@BindingAdapter("video")
fun setVideoResource(videoView: VideoView, resourceId: Int) {
    val videoUri = Uri.parse("android.resource://${videoView.context.packageName}/$resourceId")

    videoView.setVideoURI(videoUri)
    videoView.setMediaController(MediaController(videoView.context))
    videoView.seekTo(1)
}

@BindingAdapter("videoUrl")
fun setVideoResource(videoView: VideoView, videoUrl: String?) {
    videoUrl?.let {
        val videoUri = Uri.parse(videoUrl)

        videoView.setVideoURI(videoUri)
        videoView.setMediaController(MediaController(videoView.context))
        videoView.seekTo(1)
    }
}