package com.ivasenko.mobilelivetest.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.ivasenko.mobilelivetest.R
import com.ivasenko.mobilelivetest.databinding.SearchTweetsFragmentBinding
import com.ivasenko.mobilelivetest.ui.shared.SharedViewModel
import kotlinx.android.synthetic.main.search_tweets_fragment.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class SearchTweetsFragment : Fragment(), KodeinAware {

    override val kodein by closestKodein()
    private val viewModelFactory: SearchViewModelFactory by instance()
    private val searchTweetsAdapter = SearchTweetsAdapter()

    private lateinit var viewModel: SearchTweetsViewModel
    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(SearchTweetsViewModel::class.java)
        sharedViewModel = ViewModelProviders.of(requireActivity())
            .get(SharedViewModel::class.java)
        val binding: SearchTweetsFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.search_tweets_fragment, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recycler_view.adapter = searchTweetsAdapter
        searchTweetsAdapter.handler = { tweet ->
            sharedViewModel.currentMarker(tweet)
            findNavController().navigate(R.id.tweetDetailsFragment)
        }

        viewModel.tweetsList.observe(viewLifecycleOwner, Observer {
            searchTweetsAdapter.tweetList = it
            searchTweetsAdapter.notifyDataSetChanged()
        })
    }
}
