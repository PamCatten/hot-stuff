package com.hotstuff.ui.learn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.hotstuff.R
import com.hotstuff.databinding.FragmentArticleBinding

class ArticleFragment : Fragment() {
    private var _binding: FragmentArticleBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        _binding = FragmentArticleBinding.inflate(inflater, container, false)
        val view = binding.root
        val bundle = this.requireArguments()

        val articleTitle = view.findViewById<TextView>(R.id.article_name)
        val articleText = view.findViewById<TextView>(R.id.article_text)
        val articleDisclosure = view.findViewById<TextView>(R.id.article_disclosure)
        articleDisclosure.text = getText(R.string.article_legal_disclosure)

        when (bundle.getString(getString(R.string.key_article))) {
            getText(R.string.article_whyCare) -> {
                articleTitle.text = getText(R.string.article_whyCare_title)
                articleText.text = getText(R.string.article_whyCare_text)
            }
            getText(R.string.article_getStarted) -> {
                articleTitle.text = getText(R.string.article_getStarted_title)
                articleText.text = getText(R.string.article_getStarted_text)
                articleDisclosure.text = ""
            }
            getText(R.string.article_export) -> {
                articleTitle.text = getText(R.string.article_export_title)
                articleText.text = getText(R.string.article_export_text)
                articleDisclosure.text = ""
            }
            getText(R.string.article_detail) -> {
                articleTitle.text = getText(R.string.article_detail_title)
                articleText.text = getText(R.string.article_detail_text)
            }
            getText(R.string.article_jargonGlossary) -> {
                articleTitle.text = getText(R.string.article_jargonGlossary_title)
                articleText.text = getText(R.string.article_jargonGlossary_text)
            }
            getText(R.string.article_adjusters) -> {
                articleTitle.text = getText(R.string.article_adjusters_title)
                articleText.text = getText(R.string.article_adjusters_text)
            }
            getText(R.string.article_likeKind) -> {
                articleTitle.text = getText(R.string.article_likeKind_title)
                articleText.text = getText(R.string.article_likeKind_text)
            }
            else -> {} // do nothing
        }

        return view
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}