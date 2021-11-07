package com.fourthwall.android.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.fourthwall.android.R
import com.fourthwall.android.databinding.FragmentImagesDetailsBinding
import com.fourthwall.android.ui.viewmodel.ImagesViewModel
import com.squareup.picasso.Picasso


/**
 * A ImageDetails [Fragment] as the second destination in the navigation.
 */
class ImageDetailsFragment : Fragment() {

    private var _binding: FragmentImagesDetailsBinding? = null

    private val binding get() = _binding!!

    private val viewModel: ImagesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentImagesDetailsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateViewInfo()
        binding.share.setOnClickListener { onShareClicked() }
    }

    private fun onShareClicked() {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "image/*"
        val bitmap = binding.image.drawable.toBitmap(300, 300)
        val bitmapPath = MediaStore.Images.Media.insertImage(
            requireContext().contentResolver,
            bitmap,
            "Share Image",
            ""
        )
        val bitmapUri: Uri = Uri.parse(bitmapPath)
        intent.putExtra(Intent.EXTRA_STREAM, bitmapUri)
        startActivity(Intent.createChooser(intent, "Share Image"))
    }

    private fun updateViewInfo() {
        if (viewModel.selectedImageInfo != null) {
            binding.author.text = viewModel.selectedImageInfo?.author
            binding.author.text = viewModel.selectedImageInfo?.author
            Picasso.get()
                .load("https://picsum.photos/500?image=${viewModel.selectedImageInfo?.id}")
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.broken_image)
                .into(binding.image)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}