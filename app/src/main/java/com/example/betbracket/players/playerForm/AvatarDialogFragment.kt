package com.example.betbracket.players.playerForm


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.betbracket.databinding.FragmentAvatarDialogBinding
import com.example.betbracket.players.PlayerViewModel


class AvatarDialogFragment : DialogFragment() {
    private var _binding: FragmentAvatarDialogBinding? = null
    private val binding get() = _binding!!
    private val playerViewModel: PlayerViewModel by viewModels({ requireParentFragment() })
    private lateinit var adapter: AvatarDialogAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAvatarDialogBinding.inflate(inflater, container, false)

        initRecyclerView()
        binding.closeButton.setOnClickListener {
            dismiss()
        }

        return binding.root
    }

    private fun initRecyclerView() {
        adapter = AvatarDialogAdapter(AvatarProvider.imageList) { pos -> onAvatarSelect(pos) }
        binding.avatarList.layoutManager =
            GridLayoutManager(requireContext(), 3, GridLayoutManager.VERTICAL, false)
        binding.avatarList.adapter = adapter

    }

    private fun onAvatarSelect(pos: Int) {
        Log.i("AVATAR", "${pos}th item selected")
        playerViewModel.setCurrentPlayerImage(AvatarProvider.imageList[pos])

    }


    companion object {
        const val TAG = "AvatarDialogFragment"
    }


}


