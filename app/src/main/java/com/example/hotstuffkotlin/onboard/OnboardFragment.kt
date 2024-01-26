//package com.example.hotstuffkotlin.onboard
//
//import android.content.Context
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.Fragment
//import com.example.hotstuffkotlin.R
//import com.example.hotstuffkotlin.databinding.FragmentViewPagerBinding
//
//class OnboardFragment : Fragment() {
//
//    private var _binding: FragmentViewPagerBinding? = null
//    private val binding get() = _binding!!
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
//        _binding = FragmentViewPagerBinding.inflate(inflater, container, false)
//        val view = binding.root
//
//            if(onBoardingFinished()){
//                findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
//            }else{
//                findNavController().navigate(R.id.action_splashFragment_to_viewPagerFragment)
//            }
//
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_onboard, container, false)
//
//    private fun onBoardingFinished(): Boolean{
//        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
//        return sharedPref.getBoolean("Finished", false)
//    }
//
//        return view
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//}