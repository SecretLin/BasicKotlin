package com.example.basickotlin

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.basickotlin.data.Data
import com.example.basickotlin.databinding.FragmentDetailNoteBinding

/**
 * 详情界面
 */
class DetailNoteFragment : Fragment() {

    private var _binding: FragmentDetailNoteBinding? = null
    private var data: Data? = null

    private val viewModel: CustomWordViewModel by viewModels {
        WordViewModelFactory(
            ((activity?.application) as MyApplication).database.dataDao()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            data = it.getParcelable("data")
            viewModel.word.value = data
            // 更新数据库保存的备注
            if (data != null) {
                viewModel.getData(data!!).observe(this) {
                    Log.d("DetailFragment", "getdata is ${it}")
                    if (it != null) {
                        viewModel.word.value?.remark = it.remark
                        updateView()
                    }

                }


            }
        }
    }

    private fun updateView() {
        _binding?.btnAdd?.visibility =
            if (viewModel.word.value?.remark == null) View.VISIBLE else View.GONE
        _binding?.etRemark?.visibility =
            if (viewModel.word.value?.remark == null) View.GONE else View.VISIBLE
        _binding?.etRemark?.setText(viewModel.word.value?.remark ?: "")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailNoteBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding?.viewModel = viewModel
        // 添加备注
        _binding?.btnAdd?.setOnClickListener {
            _binding?.etRemark?.visibility = View.VISIBLE
            it.visibility = View.GONE
        }

        // 保存到数据库
        _binding?.btnSave?.setOnClickListener {
            saveWord()
        }
    }

    private fun saveWord() {
        val remark = _binding?.etRemark?.text?.toString()?.trim()
        if (TextUtils.isEmpty(remark)) {
            return
        }
        data?.remark = remark
        if (data != null) {
            if (viewModel.word.value?.remark != null) {
                viewModel.update(data!!)
                Log.d("DetailFragment", "updating room...")
            } else {
                viewModel.insert(data!!)
                Log.d("DetailFragment", "insert room...")
            }
        } else {
            Log.d("DetailFragment", "data is null...")
        }
        Toast.makeText(context, "保存成功", Toast.LENGTH_SHORT).show()
    }


}