package com.example.basickotlin.wordlist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.basickotlin.WordListAdapter
import com.example.basickotlin.databinding.FragmentWordListBinding
import com.example.basickotlin.network.APPKEY
import com.example.basickotlin.network.EnglishWordApi
import com.example.basickotlin.network.UID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class WordListFragment : Fragment() {

    private var _binding: FragmentWordListBinding? = null
    private var adapter: WordListAdapter? = null
    private var layoutManager: LinearLayoutManager? = null

    private val viewModel by activityViewModels<WordListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentWordListBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = WordListAdapter(WordListAdapter.WordListClickListener(
            handleClick = {
                findNavController().navigate(
                    WordListFragmentDirections.actionWordListFragmentToDetailNoteFragment(
                        it
                    )
                )
            }, handleLongClick = {
                false
            }
        ))
        adapter?.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY

        layoutManager = LinearLayoutManager(context)

        _binding?.rvWordList?.adapter = adapter
        _binding?.rvWordList?.layoutManager = layoutManager


        if (viewModel.word.value == null) {
            viewModel.viewModelScope.launch(Dispatchers.Main) {
                var word = EnglishWordApi.retrofitService.getData(UID, APPKEY, "45090", "1")
                Log.d("WordListFragment", word.msg + "," + word.datas.size)
                adapter?.submitList(word.datas)
                viewModel.saveDataList(word.datas)
            }
        } else {
            adapter?.submitList(viewModel.word.value)
            Log.d("WordListFragment", "get from viewModel")
        }

    }

    override fun onResume() {
        super.onResume()
        if (viewModel.stateInitialized()) {
            _binding?.rvWordList?.layoutManager?.onRestoreInstanceState(
                viewModel.restoreRecyclerViewState()
            )
        }
    }

    override fun onPause() {
        super.onPause()
        // 保存滑动位置
        _binding?.rvWordList?.layoutManager?.onSaveInstanceState()?.let {
            viewModel.saveRecyclerViewState(it)
        }
    }


}