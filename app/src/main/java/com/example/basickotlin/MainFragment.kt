package com.example.basickotlin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.basickotlin.databinding.FragmentMainBinding

/**
 *  主界面
 */
class MainFragment : Fragment() {

    private var _databinding: FragmentMainBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _databinding = FragmentMainBinding.inflate(inflater, container, false)
        return _databinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        // 跳转到获取单词列表界面
        _databinding?.btnRecite?.setOnClickListener {
            findNavController().navigate(
                MainFragmentDirections.actionMainFragmentToWordListFragment()
            )
        }
        // 跳转到存到数据库的数据界面
        _databinding?.btnNote?.setOnClickListener {
            findNavController().navigate(
                MainFragmentDirections.actionMainFragmentToNoteListFragment()
            )
        }

    }

}