package com.lkb.assignment.view

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.lkb.assignment.R
import com.lkb.assignment.databinding.ActivityMainBinding
import com.lkb.assignment.view.userDetail.UserDetailActivity
import org.koin.android.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity(), UserPostsAdapter.OnPostClickListener {
    private val mViewModel: MainViewModel by viewModel()
    private lateinit var activityPostsBinding: ActivityMainBinding
    private var mAdapter: UserPostsAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAdapter = UserPostsAdapter(this)
        activityPostsBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        activityPostsBinding.postsRecyclerView.adapter = mAdapter
        mViewModel.getPost()
        activityPostsBinding.etFilter.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                mViewModel.filterData(s)
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
        with(mViewModel) {

            postsData.observe(this@MainActivity, {
                activityPostsBinding.postsProgressBar.visibility = GONE
                mAdapter?.mPostList = it
            })

            messageData.observe(this@MainActivity, {
                Toast.makeText(this@MainActivity, it, LENGTH_LONG).show()
            })

            showProgressbar.observe(this@MainActivity, { isVisible ->
                activityPostsBinding.postsProgressBar.visibility = if (isVisible) VISIBLE else GONE
            })
        }
    }

    override fun onDestroy() {
        mAdapter = null
        super.onDestroy()
    }

    companion object {
        private val TAG = MainActivity::class.java.name
    }

    override fun onPostClick(position: Int) {
        val intent = Intent(this, UserDetailActivity::class.java)
        intent.putExtra("id", mViewModel.postsData.value?.get(position)?.userId)
        startActivity(intent)

    }
}
