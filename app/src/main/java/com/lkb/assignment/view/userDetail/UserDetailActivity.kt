package com.lkb.assignment.view.userDetail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.lkb.assignment.R
import com.lkb.assignment.databinding.UserDetailLayoutBinding
import com.lkb.assignment.view.PostsAdapter
import org.koin.android.viewmodel.ext.android.viewModel

class UserDetailActivity : AppCompatActivity() {
    private var mAdapter: PostsAdapter? = null
    private lateinit var userId: String
    private val mViewModel: UserDetailsViewModel by viewModel()
    private lateinit var binding: UserDetailLayoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAdapter = PostsAdapter()
        binding = DataBindingUtil.setContentView(this, R.layout.user_detail_layout)
        userId = intent.getStringExtra("id").toString()
        mViewModel.getUserData(userId)
        binding.postsRecyclerView.adapter = mAdapter
        binding.viewModel = mViewModel
        with(mViewModel) {
            mUserName.observe(this@UserDetailActivity, {
                binding.userName.text = it
            })

            mEmail.observe(this@UserDetailActivity, {
                binding.email.text = it
            })
            messageData.observe(this@UserDetailActivity, {
                Toast.makeText(this@UserDetailActivity, it, Toast.LENGTH_LONG).show()
            })

            showProgressbar.observe(this@UserDetailActivity, { isVisible ->
                binding.postsProgressBar.visibility = if (isVisible) View.VISIBLE else View.GONE
            })

            postList.observe(this@UserDetailActivity, {
                mAdapter?.mPostList = it
            })
        }
    }
}