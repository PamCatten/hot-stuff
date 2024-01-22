package com.example.hotstuffkotlin.ui.learn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.hotstuffkotlin.R
import com.example.hotstuffkotlin.databinding.FragmentArticleBinding

class ArticleFragment : Fragment() {

    private var _binding: FragmentArticleBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArticleBinding.inflate(inflater, container, false)
        val view = binding.root
        val bundle = this.requireArguments()

        val articleTitle = view.findViewById<TextView>(R.id.article_name)
        val articleText = view.findViewById<TextView>(R.id.article_text)
        when (bundle.getString("article")) {
            getText(R.string.article_whyCare) -> {
                articleTitle.text = getText(R.string.article_whyCare_title)
                articleText.text = getText(R.string.article_whyCare_text)
            }
            getText(R.string.article_getStarted) -> {
                articleTitle.text = getText(R.string.filler_article_title)
                articleText.text = getText(R.string.filler_article_text)
            }
            getText(R.string.article_export) -> {
                articleTitle.text = getText(R.string.filler_article_title)
                articleText.text = getText(R.string.filler_article_text)
            }
            else -> {}
        }
//        articleTitle.text = getText(R.string.filler_article_title)
//        articleText.text = getText(R.string.filler_article_text)

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}