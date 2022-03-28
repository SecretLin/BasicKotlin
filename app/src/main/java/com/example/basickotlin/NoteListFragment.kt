package com.example.basickotlin

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.basickotlin.databinding.FragmentNoteListBinding
import com.example.basickotlin.databinding.FragmentWordListBinding
import kotlinx.coroutines.launch

/**
 * 笔记列表
 */
class NoteListFragment : Fragment() {
    private var _binding: FragmentNoteListBinding? = null
    private var adapter: WordListAdapter? = null
    private var layoutManager: LinearLayoutManager? = null

    private val viewModel: CustomWordViewModel by activityViewModels {
        WordViewModelFactory(
            ((activity?.application) as MyApplication).database.dataDao()
        )
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNoteListBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        adapter = WordListAdapter(WordListAdapter.WordListClickListener(
            handleClick = {
                findNavController().navigate(
                    NoteListFragmentDirections.actionNoteListFragmentToDetailNoteFragment(
                        it
                    )
                )
            }, handleLongClick = {
                viewModel.delete(it)
                val pos = viewModel.getPosition(it)
                if (pos >= 0) {
                    adapter?.notifyItemRemoved(pos)
                }
                true
            }
        ))


        layoutManager = LinearLayoutManager(context)

        _binding?.rvNoteList?.adapter = adapter
        _binding?.rvNoteList?.layoutManager = layoutManager
        viewModel.dataList.observe(this.viewLifecycleOwner) { items ->
            items.let {
                adapter?.submitList(it)
            }
        }

    }


}