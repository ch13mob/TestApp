package com.ch13mob.testapp.feature.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.ch13mob.testapp.R
import com.ch13mob.testapp.common.extension.configure
import com.ch13mob.testapp.common.extension.observe
import com.ch13mob.testapp.common.extension.showError
import com.ch13mob.testapp.databinding.FragmentLoginBinding
import com.ch13mob.testapp.feature.NavigationListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val navigationListener get() = activity as NavigationListener

    private val viewModel by activityViewModels<LoginViewModel>()

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe(viewModel.buttonState) { isEnabled ->
            binding.btnLogin.isEnabled = isEnabled
        }

        observe(viewModel.state) {
            when (it) {
                is LoginViewModel.State.Loading -> {
                    setLoading(true)
                }
                is LoginViewModel.State.Fail -> {
                    setLoading(false)
                    Toast.makeText(requireContext(), it.error.toString(), Toast.LENGTH_LONG).show()
                }
                LoginViewModel.State.LaunchMain -> {
                    setLoading(false)

                    navigationListener.onUserLoggedIn()
                }
                LoginViewModel.State.BadEmail -> {
                    binding.tilEmail.showError(R.string.error_email)
                }
                LoginViewModel.State.BadPassword -> {
                    binding.tilPassword.showError(R.string.error_password)
                }
            }
        }

        binding.tilEmail.configure(binding.etEmail) { email ->
            viewModel.onEmailChanged(email)
        }

        binding.tilPassword.configure(binding.etPassword) { password ->
            viewModel.onPasswordChanged(password)
        }

        binding.btnLogin.setOnClickListener {
            viewModel.login(
                email = binding.etEmail.text.toString(),
                password = binding.etPassword.text.toString()
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.pbLogin.isVisible = true
        } else {
            binding.pbLogin.isGone = true
        }
    }
}