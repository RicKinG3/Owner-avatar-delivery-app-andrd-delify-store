package com.swmpire.delifyit.presentation.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.swmpire.delifyit.databinding.FragmentSignUpBinding
import com.swmpire.delifyit.domain.model.NetworkResult
import com.swmpire.delifyit.domain.model.StoreModel
import com.swmpire.delifyit.presentation.viewmodel.SignUpViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SignUpFragment : Fragment() {


    private val signUpViewModel: SignUpViewModel by viewModels()
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignUpBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            buttonNext.setOnClickListener {
                val email = textInputEmail.text.toString().trim()
                val password = textInputPassword.text.toString().trim()

                if (email.isNotBlank() && password.isNotBlank()) {
                    signUpViewModel.signUpStore(StoreModel(
                        email = email,
                        password = password
                    ))
                } else {
                    // need to replace toast with "error view"
                    Toast.makeText(requireContext(), "Введите Email и пароль", Toast.LENGTH_SHORT).show()
                }
            }
            buttonSignIn.setOnClickListener{
                findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToSignInFragment())
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                signUpViewModel.signUpFlow.collect { result ->
                    when(result) {
                        is NetworkResult.Loading -> binding.buttonNext.isEnabled = false // add progress indicator also
                        is NetworkResult.Error -> {
                            Toast.makeText(requireContext(), result.message, Toast.LENGTH_SHORT).show() // need to replace toast with "error view"
                            binding.buttonNext.isEnabled = true
                        }
                        is NetworkResult.Success -> {
                            binding.buttonNext.isEnabled = true
                            if (result.data == true) {
                                findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToItemsFragment())
                            } else {
                                Toast.makeText(requireContext(), "Произошла ошибка", Toast.LENGTH_SHORT).show()
                            }
                        }
                        is NetworkResult.Idle -> {}
                    }
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}