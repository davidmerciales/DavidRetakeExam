package com.example.davidretakeexam.presenter.screen.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.davidretakeexam.R
import com.example.davidretakeexam.data.model.PersonEntity
import com.example.davidretakeexam.databinding.FragmentDetailsBinding

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: DetailsViewModel

    companion object {
        private const val ARG_PERSON = "person"
        fun newInstance(id: String): DetailFragment {
            val fragment = DetailFragment()
            fragment.arguments = Bundle().apply {
                putString(ARG_PERSON, id)
            }
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupObservers()
        setupToolbar()
        fetchPersonDetails()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(requireActivity())[DetailsViewModel::class.java]
    }

    private fun setupObservers() {
        viewModel.resultLiveData.observe(viewLifecycleOwner) { data ->
            updateUI(data)
        }
    }

    private fun setupToolbar() {
        val toolbar = binding.toolbar
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
            Log.d("DetailFragment", "Navigation back clicked")
        }
    }

    private fun fetchPersonDetails() {
        val receivedId = arguments?.getString(ARG_PERSON)
        if (!receivedId.isNullOrEmpty()) {
            viewModel.fetchDataById(receivedId)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateUI(data: PersonEntity) {
        binding.apply {
            fullname.text = "${data.title} ${data.firstName} ${data.lastName}"
            birthday.text = data.birthday
            age.text = data.age.toString()
            email.text = data.emailAddress
            phone.text = data.mobileNumber
            address.text = data.address
            gender.setImageDrawable(
                AppCompatResources.getDrawable(
                    requireContext(),
                    if (data.gender == "female") R.drawable.baseline_female_24
                    else R.drawable.baseline_male_24
                )
            )
            Glide.with(this@DetailFragment)
                .load(data.profileImg)
                .placeholder(R.drawable.default_profile)
                .transform(CircleCrop())
                .into(profileImg)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
