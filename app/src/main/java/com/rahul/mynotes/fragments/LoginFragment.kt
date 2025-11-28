package com.rahul.mynotes.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.rahul.mynotes.R
import com.rahul.mynotes.databinding.FragmentLoginBinding
import com.rahul.mynotes.model.UserRequest
import com.rahul.mynotes.utils.NetworkResult
import com.rahul.mynotes.viewModel.AuthViewModel

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null

    private val authViewModel by activityViewModels<AuthViewModel>()
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        binding.txtRegisterNow.setOnClickListener {
            findNavController().popBackStack()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSignin.setOnClickListener {
            var validationResult = validateUserInput()
            if (validationResult.first){
                authViewModel.loginUser(getUserRequest())
            }
            else{
                showValidationError(validationResult.second)
            }
        }
        bindObserver()
    }

    private fun bindObserver() {
        authViewModel.userResponseLiveData.observe(viewLifecycleOwner, Observer{
            binding.progressBar.isVisible = false
            when(it){
                is NetworkResult.Success -> {
                    findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
                }
                is NetworkResult.Error -> {
                    binding.txtErrorMsg.text = it.message.toString()
                }
                is NetworkResult.Loading -> {
                    binding.progressBar.isVisible = true
                }
            }
        })
    }

    private fun showValidationError(errorMsg: String) {
        binding.txtErrorMsg.text = String.format(resources.getString(R.string.txt_error_message), errorMsg)
    }

    private fun getUserRequest(): UserRequest{
        return binding.run {
            UserRequest(
                email.text.toString().trim(),
                password.text.toString().trim(),
                username = ""
            )
        }
    }

    private fun validateUserInput(): Pair<Boolean, String> {
        var email = binding.email.text.toString().trim()
        var password = binding.password.text.toString().trim()


        return authViewModel.validateCredential(email, password, "", true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}