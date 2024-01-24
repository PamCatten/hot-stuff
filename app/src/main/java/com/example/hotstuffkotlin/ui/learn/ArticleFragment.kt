package com.example.hotstuffkotlin.ui.learn

import android.content.Intent
import android.net.Uri
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
                articleTitle.text = getText(R.string.filler_article_title)
                articleText.text = getText(R.string.filler_article_text)
            }
            getText(R.string.article_export) -> {
                articleTitle.text = getText(R.string.filler_article_title)
                articleText.text = getText(R.string.filler_article_text)
            }
            getText(R.string.article_jargonGlossary) -> {
                articleTitle.text = getText(R.string.article_jargonGlossary_title)
                articleText.text = getText(R.string.article_jargonGlossary_text)
            }
            getText(R.string.article_acknowledgements) -> {
                articleTitle.text = getText(R.string.article_acknowledgements_title)
                articleText.text = getText(R.string.article_acknowledgements_text)
                articleDisclosure.text = ""
            }
            else -> {}
        }

        // TODO: Obliterates DRY, cannot customize menu items visibility w/ activity based menu providers, find another workaround
        requireActivity().addMenuProvider(object: MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                if (!menu.hasVisibleItems()) menuInflater.inflate(R.menu.menu_toolbar_main, menu)
            }
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.toolbar_main_search -> { return true }
                    R.id.toolbar_main_download -> { return true }
                    R.id.toolbar_main_report -> {
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.toolbar_issue_link))))
                        return true
                    }
                    R.id.toolbar_main_rate -> { return true }
                    R.id.toolbar_main_feedback -> {
                        val intent = Intent(Intent.ACTION_VIEW)
                        // TODO: Find an alternative way to extract these
                        intent.data = Uri.parse(
                            "mailto:campatten.dev@outlook.com" +
                                    "?subject=FEEDBACK: (Your Suggestion)" +
                                    "&body=Hey! Thanks for helping me improve Hot Stuff. Just a quick heads up, please make sure 'feedback' is somewhere in the subject of your suggestion so it ends up where I can see it! \n\n Much love, \nCam"
                        )
                        startActivity(intent)
                        return true
                    }
                    else -> return true
                }
            }
            override fun onPrepareMenu(menu: Menu) {
                menu.findItem(R.id.toolbar_main_search).setVisible(false)
                menu.findItem(R.id.toolbar_main_download).setVisible(false)
                menu.findItem(R.id.toolbar_main_report).setVisible(true)
                menu.findItem(R.id.toolbar_main_rate).setVisible(true)
                menu.findItem(R.id.toolbar_main_feedback).setVisible(true)
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}