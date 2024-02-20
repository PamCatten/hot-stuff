package com.example.hotstuffkotlin.ui.learn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
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
        val articleDisclosure = view.findViewById<TextView>(R.id.article_disclosure)
        articleDisclosure.text = getText(R.string.article_legal_disclosure)

        when (bundle.getString("article")) {
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
            getText(R.string.article_acknowledgements) -> {
                articleTitle.text = getText(R.string.article_acknowledgements_title)
                articleText.text = getText(R.string.article_acknowledgements_text)
                articleDisclosure.text = ""
            }
            else -> {} // do nothing
        }

        // TODO: Obliterates DRY, cannot customize menu items visibility w/ activity based menu providers, find another workaround
        requireActivity().addMenuProvider(object: MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                if (!menu.hasVisibleItems()) menuInflater.inflate(R.menu.menu_toolbar_main, menu)
            }
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean { return true }
            override fun onPrepareMenu(menu: Menu) {
                menu.findItem(R.id.toolbar_main_search).setVisible(false)
                menu.findItem(R.id.toolbar_main_download).setVisible(false)
                menu.findItem(R.id.toolbar_main_report).setVisible(false)
                menu.findItem(R.id.toolbar_main_rate).setVisible(false)
                menu.findItem(R.id.toolbar_main_feedback).setVisible(false)
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}