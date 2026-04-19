package com.example.luontopeli.ml

import android.content.Context
import android.net.Uri
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.suspendCancellableCoroutine
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume

@Singleton
class PlantClassifier @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val labeler = ImageLabeling.getClient(ImageLabelerOptions.DEFAULT_OPTIONS)

    private val natureKeywords = setOf(
        "plant", "flower", "tree", "leaf", "grass", "nature", "forest",
        "mushroom", "berry", "moss", "fern", "bark", "bush", "weed",
        "daisy", "rose", "tulip", "pine", "oak", "birch", "spruce",
        "herb", "vegetation", "flora", "blossom", "petal", "branch",
        "trunk", "root", "seed", "fruit", "lichen", "algae"
    )

    suspend fun classify(imageFile: File): Pair<String?, Float?> =
        suspendCancellableCoroutine { continuation ->
            try {
                val inputImage = InputImage.fromFilePath(context, Uri.fromFile(imageFile))
                labeler.process(inputImage)
                    .addOnSuccessListener { labels ->
                        val topNatureLabel = labels
                            .filter { label ->
                                natureKeywords.any { keyword ->
                                    label.text.lowercase().contains(keyword)
                                } || label.confidence >= 0.7f
                            }
                            .maxByOrNull { it.confidence }

                        if (topNatureLabel != null) {
                            continuation.resume(Pair(topNatureLabel.text, topNatureLabel.confidence))
                        } else {
                            val topLabel = labels.maxByOrNull { it.confidence }
                            continuation.resume(Pair(topLabel?.text, topLabel?.confidence))
                        }
                    }
                    .addOnFailureListener {
                        continuation.resume(Pair(null, null))
                    }
            } catch (e: Exception) {
                continuation.resume(Pair(null, null))
            }
        }
}
