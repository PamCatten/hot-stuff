//package com.example.hotstuffkotlin.ui.learn
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.Fragment
//import com.example.hotstuffkotlin.R
//import com.example.hotstuffkotlin.databinding.FragmentLearnBinding
//
//class ArticleFragment : Fragment() {
//
//    private var _binding: FragmentLearnBinding? = null
//    private val binding get() = _binding!!
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        _binding = FragmentLearnBinding.inflate(inflater, container, false)
//        val view = inflater.inflate(R.layout.fragment_learn, container, false)
//
//
//
//        return view
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//}