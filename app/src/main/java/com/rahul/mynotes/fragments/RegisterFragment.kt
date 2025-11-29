package com.rahul.mynotes.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.rahul.mynotes.R
import com.rahul.mynotes.databinding.FragmentRegisterBinding
import com.rahul.mynotes.model.UserRequest
import com.rahul.mynotes.utils.NetworkResult
import com.rahul.mynotes.utils.TokenManager
import com.rahul.mynotes.viewModel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding?=null
    private val binding get() = _binding!!

    private val authViewModel by activityViewModels<AuthViewModel>()

    @Inject
    lateinit var tokenManager: TokenManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)

        if (!tokenManager.getToken().isNullOrEmpty()){
            findNavController().navigate(R.id.action_registerFragment_to_mainFragment)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.txtAlreadyAcc.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

        binding.btnSignup.setOnClickListener {
            var validatationResult = validateUserInput()
            if (validatationResult.first){
                authViewModel.registerUser(getUserRequest())
            }
            else{
                showValidationError(validatationResult.second)
            }
        }

        bindObserver()
    }

    private fun showValidationError(errorMsg: String) {
        binding.txtErrorMsg.text = String.format(resources.getString(R.string.txt_error_message), errorMsg)
    }

    private fun getUserRequest(): UserRequest{
        return binding.run {
            UserRequest(
                email.text.toString().trim(),
                password.text.toString().trim(),
                username.text.toString().trim()
            )
        }
    }

    private fun validateUserInput(): Pair<Boolean, String> {
        var email = binding.email.text.toString().trim()
        var password = binding.password.text.toString().trim()
        var userName = binding.username.text.toString().trim()

        return authViewModel.validateCredential(email, password, userName, false)
    }

    private fun bindObserver() {
        authViewModel.userResponseLiveData.observe(viewLifecycleOwner, Observer{
            binding.progressBar.isVisible = false
            when(it){
                is NetworkResult.Success -> {
                    tokenManager.setToken(it.data!!.token)
                    findNavController().navigate(R.id.action_registerFragment_to_mainFragment)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}