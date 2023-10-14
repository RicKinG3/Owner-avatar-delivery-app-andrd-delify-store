package com.swmpire.delifyit.presentation.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.swmpire.delifyit.R
import com.swmpire.delifyit.databinding.FragmentSignInBinding
import com.swmpire.delifyit.databinding.FragmentSignUpBinding
import com.swmpire.delifyit.presentation.viewmodel.SignInViewModel
import com.swmpire.delifyit.presentation.viewmodel.SignUpViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : Fragment() {
    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!
    private  val viewModelMy : SignInViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignInBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModelMy.liveData.observe(requireActivity(), Observer {
            binding.text123.text = viewModelMy.liveData.value
        })

        binding.buttonNext.setOnClickListener{
            viewModelMy.setText("sdsd")

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}