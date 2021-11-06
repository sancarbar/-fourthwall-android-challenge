package com.fourthwall.android.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.fourthwall.android.R
import com.fourthwall.android.data.entity.ImageInfo
import com.fourthwall.android.databinding.FragmentImagesGalleryBinding
import com.fourthwall.android.ui.adapter.GalleryImagesAdapter
import com.fourthwall.android.ui.adapter.ImageClickListener
import com.fourthwall.android.ui.viewmodel.ImagesViewModel

/**
 * The Images Gallery [Fragment] as the default destination in the navigation.
 */
class ImagesGalleryFragment : Fragment(), ImageClickListener {

    private var _binding: FragmentImagesGalleryBinding? = null

    private val binding get() = _binding!!

    private val viewModel: ImagesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentImagesGalleryBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureRecyclerView()
        addLiveDataObservers()
    }

    private fun addLiveDataObservers() {
        viewModel.imagesListLiveData.observe(viewLifecycleOwner, {
            binding.recyclerView.adapter = GalleryImagesAdapter(it, this)
        })
        viewModel.loadImagesLiveData.observe(viewLifecycleOwner, {
            binding.progressBar.visibility = View.GONE
            binding.recyclerView.visibility = View.VISIBLE

        })
    }

    private fun configureRecyclerView() {
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onImageClicked(imageInfo: ImageInfo) {
        findNavController().navigate(R.id.action_ImagesGalleryFragment_to_ImageDetailsFragment)
    }
}