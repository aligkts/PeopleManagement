package com.aligkts.peoplemanagement.scene.main

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.aligkts.peoplemanagement.base.BaseBindingActivity
import com.aligkts.peoplemanagement.databinding.ActivityMainBinding
import com.aligkts.peoplemanagement.internal.injection.DependencyComponent
import com.aligkts.peoplemanagement.internal.util.CommonValue.DEFAULT_PAGE_SIZE
import com.aligkts.peoplemanagement.internal.util.extension.gone
import com.aligkts.peoplemanagement.internal.util.extension.show
import com.aligkts.peoplemanagement.internal.util.pagination.PaginationScrollListener
import com.aligkts.peoplemanagement.scene.main.peoplelist.*
import com.aligkts.peoplemanagement.view.model.TextMessageUiModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainActivity :
    BaseBindingActivity<MainViewModel, ActivityMainBinding>() {

    private var isLoading = false
    private var isLastPage = false
    private val peopleListAdapter by lazy { PeopleListAdapter() }

    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeData()
        DependencyComponent.inject(this)
        setupPeopleRecyclerview()
        setupSwipeToRefresh()
        savedInstanceState?.let {
            viewModel.restorePeopleList()
        } ?: viewModel.updatePeopleList()
    }

    private fun setupPeopleRecyclerview() {
        val linearLayoutManager = LinearLayoutManager(this)
        binding.rvPeople.apply {
            layoutManager = linearLayoutManager
            adapter = peopleListAdapter
            addOnScrollListener(PeopleListScrollListener(linearLayoutManager))
        }
    }

    private fun setupSwipeToRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            resetPeopleList()
        }
    }

    private fun loadNextPage() {
        peopleListAdapter.addLoadingViewFooter()
        viewModel.updatePeopleList()
    }

    private fun resetPeopleList() {
        peopleListAdapter.clearData()
        viewModel.resetPeopleList()
    }

    private fun showErrorState(message: String) {
        binding.llErrorRetry.show()
        binding.txtErrorMessage.text = message
        binding.btnRetry.setOnClickListener {
            hideErrorState()
            resetPeopleList()
        }
    }

    private fun hideErrorState() {
        binding.llErrorRetry.gone()
    }

    private fun observeData() = lifecycleScope.launchWhenStarted {
        viewModel.peopleListState
            .onEach { state ->
                handlePeopleListState(state)
            }.launchIn(this)
    }

    private fun handlePeopleListState(state: PeopleListState) {
        isLastPage = state.loadedAllItems
        if (isLastPage) peopleListAdapter.removeLoadingViewFooter()
        when (state) {
            is LoadingState -> renderLoadingUiState()
            is PaginationState -> renderPaginationUiState()
            is DefaultState -> renderDefaultUiState(state)
            is EmptyState -> renderRetryUiState(state.errorMessageUiModel)
            is ErrorState -> renderRetryUiState(state.errorMessageUiModel)
        }
    }

    private fun renderLoadingUiState() {
        isLoading = true
        binding.swipeRefreshLayout.isRefreshing = true
    }

    private fun renderPaginationUiState() {
        isLoading = true
    }

    private fun renderDefaultUiState(state: DefaultState) {
        isLoading = false
        binding.swipeRefreshLayout.isRefreshing = false
        peopleListAdapter.updateData(state.data)
    }

    private fun renderRetryUiState(messageUiModel: TextMessageUiModel) {
        isLoading = false
        binding.swipeRefreshLayout.isRefreshing = false
        peopleListAdapter.removeLoadingViewFooter()
        peopleListAdapter.clearData()
        showErrorState(messageUiModel.asString(this@MainActivity))
    }

    inner class PeopleListScrollListener(layoutManager: LinearLayoutManager) :
        PaginationScrollListener(layoutManager) {
        override fun isLoading() = isLoading
        override fun loadMoreItems() = loadNextPage()
        override fun getTotalPageCount() = DEFAULT_PAGE_SIZE
        override fun isLastPage() = isLastPage
    }
}
