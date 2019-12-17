package com.ivasenko.mobilelivetest.ui.details

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.ivasenko.mobilelivetest.R
import com.ivasenko.mobilelivetest.databinding.TweetDetailsFragmentBinding
import com.ivasenko.mobilelivetest.ui.shared.SharedViewModel
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class TweetDetailsFragment : Fragment(), KodeinAware {

    override val kodein by closestKodein()
    private val viewModelFactory: TweetDetailsViewModelFactory by instance()

    private lateinit var viewModel: TweetDetailsViewModel
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var binding: TweetDetailsFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(TweetDetailsViewModel::class.java)
        sharedViewModel = ViewModelProviders.of(requireActivity())
            .get(SharedViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.tweet_details_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.actionResult.observe(viewLifecycleOwner, Observer {
            Toast.makeText(context, getString(it), Toast.LENGTH_SHORT).show()
        })
        sharedViewModel.tweetMapMarker.observe(viewLifecycleOwner, Observer {
            binding.tweet = it
            Glide.with(this)
                .load(it.tweets.user.profileImageUrlHttps)
                .centerCrop()
                .placeholder(R.drawable.ic_account_box)
                .error(R.drawable.ic_image_broken_variant)
                .fallback(R.drawable.ic_image_off_outline)
                .into(binding.profileImage)
            viewModel.tweet = it
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_details, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favorite -> viewModel.addToFavorites()
            R.id.retweet -> viewModel.retweet()
        }
        return true
    }
}
